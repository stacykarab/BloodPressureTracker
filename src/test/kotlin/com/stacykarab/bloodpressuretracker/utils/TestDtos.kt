package com.stacykarab.bloodpressuretracker.utils

import com.stacykarab.bloodpressuretracker.dto.UserCreateUpdateDto
import com.stacykarab.bloodpressuretracker.dto.UserRequestFilterDto
import com.stacykarab.bloodpressuretracker.entity.Gender
import java.time.LocalDate

object TestDtos {
    val userCreateUpdateDto = UserCreateUpdateDto(
        name = "test",
        gender = Gender.F,
        birthDate = LocalDate.parse("2000-01-01"),
        smoking = false,
        height = 165,
        weight = 55,
        avgSystolic = 110,
        avgDiastolic = 70,
        chronicIllnessIds = listOf(1),
    )
}