package com.stacykarab.bloodpressuretracker.dto

data class BPNotificationDto(
    val systolic: Int,
    val diastolic: Int,
    val message: String,
)
