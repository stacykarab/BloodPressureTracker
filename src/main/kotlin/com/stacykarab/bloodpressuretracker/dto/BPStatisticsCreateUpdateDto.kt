package com.stacykarab.bloodpressuretracker.dto

import com.stacykarab.bloodpressuretracker.entity.PartOfDay
import java.time.LocalDate

data class BPStatisticsCreateUpdateDto(
    val userId: Long,
    val date: LocalDate,
    val systolic: Int,
    val diastolic: Int,
    val partOfDay: PartOfDay,
)
