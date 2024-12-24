package com.stacykarab.bloodpressuretracker.repository

import com.stacykarab.bloodpressuretracker.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface UserRepository : JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
}