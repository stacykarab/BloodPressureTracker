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
class CalculationService(
    private val userDailyBPStatisticService: UserDailyBPStatisticsService,
) {

    private val currentPartOfDayUserBpStatistics = ConcurrentHashMap<Long, MutableList<Pair<Int, Int>>>()

    private var lastPartOfDay: PartOfDay? = null

    public fun addBpToCalculateAverage(kafkaBpStatistics: KafkaBpStatistics) {
        val currentPartOfDay = getCurrentPartOfDay(kafkaBpStatistics.timestamp)
        val userId = kafkaBpStatistics.userId
        if (lastPartOfDay != null && lastPartOfDay != currentPartOfDay) {
            logger.info { "Calculating average BP for user $userId, partOfDay $lastPartOfDay" }
            val averagePerPartOfDay = currentPartOfDayUserBpStatistics[userId]?.let {
                calculateAveragePerPartOfDay(it)
            }
            userDailyBPStatisticService.addNewBPStatistic(
                BPStatisticsCreateUpdateDto(
                    userId = userId,
                    date = kafkaBpStatistics.timestamp.toLocalDate(),
                    partOfDay = lastPartOfDay!!,
                    systolic = averagePerPartOfDay!!.first,
                    diastolic = averagePerPartOfDay.second,
                )
            )
            currentPartOfDayUserBpStatistics.clear()
        }
        lastPartOfDay = currentPartOfDay

        val bpToAdd: MutableList<Pair<Int, Int>> = currentPartOfDayUserBpStatistics.getOrDefault(
            userId, ArrayList()
        )
        bpToAdd.add(
            kafkaBpStatistics.systolicPressure to kafkaBpStatistics.diastolicPressure
        )
        currentPartOfDayUserBpStatistics[userId] = bpToAdd
        logger.info { "Added BP to map for user $userId, partOfDay $lastPartOfDay" }
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
        val hour = timestamp?.hour

        if (hour in 6..11) return PartOfDay.MORNING

        if (hour in 12..17) return PartOfDay.AFTERNOON

        if (hour in 18..23) return PartOfDay.EVENING

        return PartOfDay.NIGHT
    }
}