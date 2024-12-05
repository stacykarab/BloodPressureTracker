package com.stacykarab.bloodpressuretracker.service

import com.stacykarab.bloodpressuretracker.dto.UserBpStatisticsDto
import com.stacykarab.bloodpressuretracker.dto.UserCreateUpdateDto
import com.stacykarab.bloodpressuretracker.dto.UserDto
import com.stacykarab.bloodpressuretracker.dto.UserRequestFilter
import com.stacykarab.bloodpressuretracker.mapper.UserBpStatisticsMapper
import com.stacykarab.bloodpressuretracker.mapper.UserMapper
import com.stacykarab.bloodpressuretracker.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class UserServiceImpl(
    private val userRepository : UserRepository,
    private val userMapper: UserMapper,
    private val bpStatisticsService: UserDailyBPStatisticsService,
    private val bpStatisticsMapper: UserBpStatisticsMapper,
) : UserService {

    override fun create(user: UserCreateUpdateDto): UserDto {
        return userMapper.toUserDto(userRepository.save(userMapper.toUserEntity(user)))
    }

    override fun getById(id : Long): UserDto? {
        return userRepository.findByIdOrNull(id)?.let { userMapper.toUserDto(it) }
    }

    override fun getAllByFilter(filter: UserRequestFilter): List<UserDto> {
        return userRepository.findAll().map { userMapper.toUserDto(it) }
    }

    override fun update(user: UserCreateUpdateDto): UserDto {
        return userMapper.toUserDto(userRepository.save(userMapper.toUserEntity(user)))
    }

    override fun getBpStatistics(id: Long, from: LocalDate, to: LocalDate): UserBpStatisticsDto? {
        val bpStatistics = bpStatisticsService.getBPStatistics(id, from, to)
        return bpStatistics.let { it[0].user }?.let {
            bpStatisticsMapper.toUserBpStatisticsDto(
                user = userMapper.toUserDto(it),
                statisticsList = bpStatistics
            )
        }
    }

}