package com.stacykarab.bloodpressuretracker.controller

import com.stacykarab.bloodpressuretracker.api.UsersApiDelegate
import com.stacykarab.bloodpressuretracker.dto.UserBpStatisticsDto
import com.stacykarab.bloodpressuretracker.dto.UserCreateUpdateDto
import com.stacykarab.bloodpressuretracker.dto.UserDto
import com.stacykarab.bloodpressuretracker.dto.UserRequestFilterDto
import com.stacykarab.bloodpressuretracker.entity.Gender
import com.stacykarab.bloodpressuretracker.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
@RequestMapping("/api/v1/users")
class UserController(
    private val userService: UserService,
) : UsersApiDelegate {

    override fun create(user: UserCreateUpdateDto): ResponseEntity<UserDto> {
        return ResponseEntity.ok(
            userService.create(user)
        )
    }

    override fun findAll(
        userIds: List<Long>?,
        name: String?,
        gender: String?,
        birthDateFrom: LocalDate?,
        birthDateTo: LocalDate?,
        smoking: Boolean?,
        height: List<Int>?,
        weight: List<Int>?,
        chronicIllnessIds: List<Long>?
    ): ResponseEntity<List<UserDto>> {
        return ResponseEntity.ok(
            userService.getAllByFilter(
                UserRequestFilterDto(
                    userIds = userIds,
                    name = name,
                    gender = gender?.let { listOf(Gender.valueOf(it)) },
                    birthDateFrom = birthDateFrom,
                    birthDateTo = birthDateTo,
                    smoking = smoking,
                    height = height?.let { IntRange(it.first(), height.last()) },
                    weight = weight?.let { IntRange(it.first(), weight.last()) },
                    chronicIllnessIds = chronicIllnessIds
                )
            )
        )
    }

    override fun findBpStatistics(
        id: Long,
        from: LocalDate,
        to: LocalDate
    ): ResponseEntity<UserBpStatisticsDto> {
        return ResponseEntity.ok(
            userService.getBpStatistics(
                id, from, to
            )
        )
    }

    override fun findById(id: Long): ResponseEntity<UserDto> {
        return ResponseEntity.ok(
            userService.getById(id)
        )
    }
}