package com.stacykarab.bloodpressuretracker.service

import com.stacykarab.bloodpressuretracker.dto.UserBpStatisticsDto
import com.stacykarab.bloodpressuretracker.dto.UserCreateUpdateDto
import com.stacykarab.bloodpressuretracker.dto.UserDto
import com.stacykarab.bloodpressuretracker.dto.UserRequestFilter
import java.time.LocalDate

interface UserService {
    fun create(user: UserCreateUpdateDto): UserDto
    fun getById(id : Long): UserDto?
    fun getAllByFilter(filter : UserRequestFilter): List<UserDto>
    fun update(user: UserCreateUpdateDto): UserDto
    fun getBpStatistics(id: Long, from: LocalDate, to: LocalDate): UserBpStatisticsDto?
}