package com.stacykarab.bloodpressuretracker.service

import com.stacykarab.bloodpressuretracker.dto.BPStatisticsCreateUpdateDto
import com.stacykarab.bloodpressuretracker.entity.PartOfDay
import com.stacykarab.bloodpressuretracker.events.KafkaBpStatistics
import mu.KotlinLogging
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.concurrent.ConcurrentHashMap

private val logger = KotlinLogging.logger { }

@Service
class CalculationServiceImpl(
    private val userDailyBPStatisticService: UserDailyBPStatisticsService,
) : CalculationService {

    private val currentPartOfDayUserBpStatistics = ConcurrentHashMap<Long, MutableList<Pair<Int, Int>>>()

    private val lastPartOfDayPerUsers = ConcurrentHashMap<Long, PartOfDay>()

    override fun addBpToCalculateAverage(kafkaBpStatistics: KafkaBpStatistics) {
        val currentPartOfDay = getCurrentPartOfDay(kafkaBpStatistics.timestamp)
        val userId = kafkaBpStatistics.userId
        val lastPartOfDayPerUser = lastPartOfDayPerUsers[userId]
        if (lastPartOfDayPerUser != null && lastPartOfDayPerUser != currentPartOfDay) {
            logger.info { "Calculating average BP for user $userId, date ${kafkaBpStatistics.timestamp.toLocalDate()}, partOfDay $lastPartOfDayPerUser" }
            val averagePerPartOfDay = currentPartOfDayUserBpStatistics[userId]?.let {
                calculateAveragePerPartOfDay(it)
            }
            saveNewBpStatisticsAndClear(kafkaBpStatistics, averagePerPartOfDay, lastPartOfDayPerUser)
        }
        lastPartOfDayPerUsers[userId] = currentPartOfDay
        logger.info { "Switched current partOfDay for user $userId to $currentPartOfDay" }

        addNewBpStatisticsToMap(userId, kafkaBpStatistics)
        logger.info { "Added BP to map for user $userId, date ${kafkaBpStatistics.timestamp.toLocalDate()}, partOfDay $currentPartOfDay" }
    }

    private fun addNewBpStatisticsToMap(
        userId: Long,
        kafkaBpStatistics: KafkaBpStatistics
    ) {
        val bpToAdd: MutableList<Pair<Int, Int>> =
            currentPartOfDayUserBpStatistics.getOrDefault(userId, ArrayList())
        bpToAdd
            .add(kafkaBpStatistics.systolicPressure to kafkaBpStatistics.diastolicPressure)
        currentPartOfDayUserBpStatistics[userId] = bpToAdd
    }

    private fun saveNewBpStatisticsAndClear(
        kafkaBpStatistics: KafkaBpStatistics,
        averagePerPartOfDay: Pair<Int, Int>?,
        lastPartOfDay: PartOfDay,
    ) {
        if (averagePerPartOfDay != null) {
            userDailyBPStatisticService.addNewBPStatistic(
                BPStatisticsCreateUpdateDto(
                    userId = kafkaBpStatistics.userId,
                    date = kafkaBpStatistics.timestamp.toLocalDate(),
                    partOfDay = lastPartOfDay,
                    systolic = averagePerPartOfDay.first,
                    diastolic = averagePerPartOfDay.second,
                )
            )
            currentPartOfDayUserBpStatistics.clear()
        }
    }

    private fun calculateAveragePerPartOfDay(bpStatisticsList: List<Pair<Int, Int>>): Pair<Int, Int> {
        val averageSystolic = bpStatisticsList
            .map { it.first }
            .average()
            .toInt()
        val averageDiastolic = bpStatisticsList
            .map { it.second }
            .average()
            .toInt()
        return averageSystolic to averageDiastolic
    }

    private fun getCurrentPartOfDay(timestamp: LocalDateTime?): PartOfDay {
        return when (timestamp?.hour) {
            in 6..11 -> PartOfDay.MORNING
            in 12..15 -> PartOfDay.AFTERNOON
            in 18..23 -> PartOfDay.EVENING
            else -> PartOfDay.NIGHT
        }
    }
}