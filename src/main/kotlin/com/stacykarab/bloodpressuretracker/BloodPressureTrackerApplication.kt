package com.stacykarab.bloodpressuretracker

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BloodPressureTrackerApplication

fun main(args: Array<String>) {
    runApplication<BloodPressureTrackerApplication>(*args)
}
