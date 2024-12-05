package com.stacykarab.bloodpressuretracker.dto

import com.stacykarab.bloodpressuretracker.entity.Gender
import java.time.LocalDate

data class UserCreateUpdateDto(
    val name: String,
    val gender: Gender,
    val birthDate: LocalDate? = null,
    val smoking: Boolean? = null,
    val height: Int? = null,
    val weight: Int? = null,
    val avgSystolic: Int? = null,
    val avgDiastolic: Int? = null,
    val chronicIllnessIds: List<Long>,
)