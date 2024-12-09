package com.stacykarab.bloodpressuretracker.events

import java.time.LocalDateTime

data class KafkaBpStatistics(
    val userId: Long,
    val timestamp: LocalDateTime,
    var systolicPressure: Int,
    var diastolicPressure: Int,
)
