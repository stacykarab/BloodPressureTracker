package com.stacykarab.bloodpressuretracker.service

import com.stacykarab.bloodpressuretracker.dto.BPStatisticsCreateUpdateDto
import com.stacykarab.bloodpressuretracker.entity.UserDailyBPStatistics
import java.time.LocalDate

interface UserDailyBPStatisticsService {
    fun addNewBPStatistic(bpStatistics: BPStatisticsCreateUpdateDto)
    fun getBPStatistics(userId: Long, from: LocalDate, to: LocalDate): List<UserDailyBPStatistics>
}