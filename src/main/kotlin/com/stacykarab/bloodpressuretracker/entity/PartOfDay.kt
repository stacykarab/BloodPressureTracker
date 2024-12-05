package com.stacykarab.bloodpressuretracker.entity

import com.stacykarab.bloodpressuretracker.utils.StatisticsConsts.AVG_DIASTOLIC_AFTERNOON
import com.stacykarab.bloodpressuretracker.utils.StatisticsConsts.AVG_DIASTOLIC_EVENING
import com.stacykarab.bloodpressuretracker.utils.StatisticsConsts.AVG_DIASTOLIC_MORNING
import com.stacykarab.bloodpressuretracker.utils.StatisticsConsts.AVG_DIASTOLIC_NIGHT
import com.stacykarab.bloodpressuretracker.utils.StatisticsConsts.AVG_SYSTOLIC_AFTERNOON
import com.stacykarab.bloodpressuretracker.utils.StatisticsConsts.AVG_SYSTOLIC_EVENING
import com.stacykarab.bloodpressuretracker.utils.StatisticsConsts.AVG_SYSTOLIC_MORNING
import com.stacykarab.bloodpressuretracker.utils.StatisticsConsts.AVG_SYSTOLIC_NIGHT

enum class PartOfDay(
    val avgSystolic: String,
    val avgDiastolic: String,
) {
    NIGHT(AVG_SYSTOLIC_NIGHT, AVG_DIASTOLIC_NIGHT),
    MORNING(AVG_SYSTOLIC_MORNING, AVG_DIASTOLIC_MORNING),
    AFTERNOON(AVG_SYSTOLIC_AFTERNOON, AVG_DIASTOLIC_AFTERNOON),
    EVENING(AVG_SYSTOLIC_EVENING, AVG_DIASTOLIC_EVENING);

    companion object {
        val partOfDayEntries: Map<String, PartOfDay> = PartOfDay.entries.associateBy{ it.name }
    }
}

