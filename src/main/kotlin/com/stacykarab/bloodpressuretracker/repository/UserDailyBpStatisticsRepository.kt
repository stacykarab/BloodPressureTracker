package com.stacykarab.bloodpressuretracker.repository

import com.stacykarab.bloodpressuretracker.entity.UserDailyBPStatistics
import com.stacykarab.bloodpressuretracker.entity.UserDailyBpStatisticsId
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate

interface UserDailyBpStatisticsRepository : JpaRepository<UserDailyBPStatistics, UserDailyBpStatisticsId> {

    fun findAllByUserIdAndIdDateBetween(userId: Long, from: LocalDate, to: LocalDate): List<UserDailyBPStatistics>
}