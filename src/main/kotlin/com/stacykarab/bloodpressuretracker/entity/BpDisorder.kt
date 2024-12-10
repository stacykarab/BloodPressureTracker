package com.stacykarab.bloodpressuretracker.entity

enum class BpDisorder(val systolic: IntRange, val diastolic: IntRange) {
    HYPERTENSION(IntRange(130, 200), IntRange(80, 120)),
    HYPOTENSION(IntRange(40, 90), IntRange(20, 60)),
    NONE(IntRange(91, 129), IntRange(61, 79));

    companion object {
        private val messageMap = mapOf(
            HYPOTENSION to "Your blood pressure is too LOW, consider taking caffeine or Fludrocortisone and check your blood sugar",
            HYPERTENSION to "Your blood pressure is too HIGH, consider taking Prinivil, Zestril or HCTZ and drink some green tea"
        )

        fun getMessage(bpDisorder: BpDisorder): String? {
            return messageMap[bpDisorder]
        }
    }
}