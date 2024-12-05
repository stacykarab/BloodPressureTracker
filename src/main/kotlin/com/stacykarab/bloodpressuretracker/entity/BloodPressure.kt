package com.stacykarab.bloodpressuretracker.entity

import jakarta.persistence.Embeddable

@Embeddable
class BloodPressure(
    var systolicPressure: Int? = null,
    var diastolicPressure: Int? = null,
)