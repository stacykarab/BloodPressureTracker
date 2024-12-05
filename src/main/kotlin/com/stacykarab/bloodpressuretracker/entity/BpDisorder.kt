package com.stacykarab.bloodpressuretracker.entity

enum class BpDisorder(val systolic: IntRange, val diastolic: IntRange) {
    HYPERTENSION(IntRange(130, 200), IntRange(80, 120)),
    HYPOTENSION(IntRange(40, 90), IntRange(20, 60)),
    NONE(IntRange(91, 129), IntRange(61, 79)),
}