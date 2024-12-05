package com.stacykarab.bloodpressuretracker.repository

import com.stacykarab.bloodpressuretracker.entity.ChronicIllness
import org.springframework.data.jpa.repository.JpaRepository

interface ChronicIllnessRepository : JpaRepository<ChronicIllness, Long> {
}