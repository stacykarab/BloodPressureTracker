package com.stacykarab.bloodpressuretracker.controller

import com.stacykarab.bloodpressuretracker.dto.UserBpStatisticsDto
import com.stacykarab.bloodpressuretracker.dto.UserCreateUpdateDto
import com.stacykarab.bloodpressuretracker.dto.UserDto
import com.stacykarab.bloodpressuretracker.dto.UserRequestFilter
import com.stacykarab.bloodpressuretracker.service.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService,
) {

    @GetMapping
    fun findAll(@RequestBody userRequestFilter: UserRequestFilter): List<UserDto> {
        return userService.getAllByFilter(userRequestFilter)
    }

    @PostMapping
    fun create(@RequestBody user: UserCreateUpdateDto): UserDto {
        return userService.create(user);
    }

    @GetMapping("/{userId}")
    fun findById(@PathVariable userId: Long): UserDto? {
        return userService.getById(userId)
    }

    @PutMapping("/{userId}")
    fun update(@RequestBody userDto: UserCreateUpdateDto,
               @PathVariable userId: Long): UserDto {
        return userService.update(userDto)
    }

    @GetMapping("/bp/{userId}")
    fun findBpStatistics(@PathVariable userId: Long,
                         @RequestParam from: LocalDate,
                         @RequestParam to: LocalDate): UserBpStatisticsDto? {
        return userService.getBpStatistics(userId, from, to)
    }
}