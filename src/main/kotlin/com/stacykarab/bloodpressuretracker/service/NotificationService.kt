package com.stacykarab.bloodpressuretracker.service

import com.stacykarab.bloodpressuretracker.events.KafkaBpStatistics

interface NotificationService {
    fun notify(kafkaBpStatistics: KafkaBpStatistics)
}