package com.stacykarab.bloodpressuretracker.entity

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.time.LocalDate

@Embeddable
class UserDailyBpStatisticsId(
    @Column(name = "user_id")
    val userId: Long,
    @Column(name = "date")
    val date: LocalDate,
)