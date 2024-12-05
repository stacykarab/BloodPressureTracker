package com.stacykarab.bloodpressuretracker.service

import com.stacykarab.bloodpressuretracker.dto.BPStatisticsCreateUpdateDto
import com.stacykarab.bloodpressuretracker.entity.BloodPressure
import com.stacykarab.bloodpressuretracker.entity.PartOfDay
import com.stacykarab.bloodpressuretracker.entity.UserDailyBPStatistics
import com.stacykarab.bloodpressuretracker.entity.UserDailyBpStatisticsId
import com.stacykarab.bloodpressuretracker.repository.UserDailyBpStatisticsRepository
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class UserDailyBPStatisticsImpl(
    private val userDailyBpStatisticsRepository: UserDailyBpStatisticsRepository,
) : UserDailyBPStatisticsService {

    override fun addNewBPStatistic(bpStatistics: BPStatisticsCreateUpdateDto) {
        val userDailyBpStatisticsId = UserDailyBpStatisticsId(
            bpStatistics.userId, bpStatistics.date
        )
        val userDailyBPStatistics = UserDailyBPStatistics(userDailyBpStatisticsId)
        setBpValue(userDailyBPStatistics, bpStatistics)
        userDailyBpStatisticsRepository.save(userDailyBPStatistics)
    }

    override fun updateBPStatistic(bpStatistics: BPStatisticsCreateUpdateDto) {
        val userDailyBpStatisticsId = UserDailyBpStatisticsId(
            bpStatistics.userId, bpStatistics.date
        )
        userDailyBpStatisticsRepository.findById(userDailyBpStatisticsId)
            .ifPresent {
                setBpValue(it, bpStatistics)
                userDailyBpStatisticsRepository.save(it)
            }
    }

    override fun getBPStatistics(userId: Long, from: LocalDate, to: LocalDate): List<UserDailyBPStatistics> {
        return userDailyBpStatisticsRepository
            .findAllByUserIdAndIdDateBetween(userId, from, to)
    }

    private fun setBpValue(
        userDailyBPStatistics: UserDailyBPStatistics,
        bpStatistics: BPStatisticsCreateUpdateDto
    ) {
        val bloodPressure = getBloodPressure(bpStatistics)
        with(userDailyBPStatistics) {
            when (bpStatistics.partOfDay) {
                PartOfDay.NIGHT -> avgNight = bloodPressure
                PartOfDay.MORNING -> avgMorning = bloodPressure
                PartOfDay.AFTERNOON -> avgAfternoon = bloodPressure
                PartOfDay.EVENING -> avgEvening = bloodPressure
            }
        }
    }

    private fun getBloodPressure(bpStatistics: BPStatisticsCreateUpdateDto) =
        BloodPressure(bpStatistics.systolic, bpStatistics.diastolic)

}