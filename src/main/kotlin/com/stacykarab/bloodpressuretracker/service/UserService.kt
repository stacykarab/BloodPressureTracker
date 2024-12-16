package com.stacykarab.bloodpressuretracker.service

import com.stacykarab.bloodpressuretracker.dto.UserBpStatisticsDto
import com.stacykarab.bloodpressuretracker.dto.UserCreateUpdateDto
import com.stacykarab.bloodpressuretracker.dto.UserDto
import com.stacykarab.bloodpressuretracker.dto.UserRequestFilterDto
import java.time.LocalDate

interface UserService {
    fun create(user: UserCreateUpdateDto): UserDto
    fun getById(id : Long): UserDto?
    fun getAllByFilter(filter: UserRequestFilterDto?): List<UserDto>
    fun getBpStatistics(id: Long, from: LocalDate, to: LocalDate): UserBpStatisticsDto?
}