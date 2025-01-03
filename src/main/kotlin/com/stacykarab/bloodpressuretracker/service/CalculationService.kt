package com.stacykarab.bloodpressuretracker.service

import com.stacykarab.bloodpressuretracker.events.KafkaBpStatistics

interface CalculationService {
    fun addBpToCalculateAverage(kafkaBpStatistics: KafkaBpStatistics)
}