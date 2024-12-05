package com.stacykarab.bloodpressuretracker.dto

import com.stacykarab.bloodpressuretracker.entity.Gender
import java.time.LocalDate

data class UserDto(
    val id: Long,
    val name: String,
    val gender: Gender,
    val birthDate: LocalDate,
    val height: Int,
    val weight: Int,
    val smoking: Boolean,
    val avgSystolic: Int,
    val avgDiastolic: Int,
    val chronicIllnesses: List<ChronicIllnessDto>,
)