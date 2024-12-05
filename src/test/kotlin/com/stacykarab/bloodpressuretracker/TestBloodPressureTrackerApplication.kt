package com.stacykarab.bloodpressuretracker

import org.springframework.boot.fromApplication
import org.springframework.boot.with


fun main(args: Array<String>) {
    fromApplication<BloodPressureTrackerApplication>().with(TestcontainersConfiguration::class).run(*args)
}
