package com.stacykarab.bloodpressuretracker.dto

import com.stacykarab.bloodpressuretracker.entity.Gender
import java.time.LocalDate

data class UserRequestFilter(
    val userIds: List<Long>? = null,
    val name: String? = null,
    val gender: List<Gender>? = null,
    val birthDateFrom: LocalDate? = null,
    val birthDateTo: LocalDate? = null,
    val smoking: Boolean? = null,
    val height: IntRange? = null,
    val weight: IntRange? = null,
    val chronicIllnessIds: List<Long>? = null,
)