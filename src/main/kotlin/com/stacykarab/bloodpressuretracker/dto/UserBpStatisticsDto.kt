package com.stacykarab.bloodpressuretracker.dto

import java.time.LocalDate

data class UserBpStatisticsDto(
    val userDto: UserDto,
    val bpStatistics: List<UserDailyBPStatisticsDto>
)

data class UserDailyBPStatisticsDto(
    val date: LocalDate?,
    val avgSystolicNight: Int?,
    val avgDiastolicNight: Int?,
    val avgSystolicMorning: Int?,
    val avgDiastolicMorning: Int?,
    val avgSystolicAfternoon: Int?,
    val avgDiastolicAfternoon: Int?,
    val avgSystolicEvening: Int?,
    val avgDiastolicEvening: Int?,
)

