package com.stacykarab.bloodpressuretracker.repository

import com.stacykarab.bloodpressuretracker.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
}