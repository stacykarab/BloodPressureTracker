package com.stacykarab.bloodpressuretracker.entity

import com.stacykarab.bloodpressuretracker.utils.StatisticsConsts.AVG_DIASTOLIC_AFTERNOON
import com.stacykarab.bloodpressuretracker.utils.StatisticsConsts.AVG_DIASTOLIC_EVENING
import com.stacykarab.bloodpressuretracker.utils.StatisticsConsts.AVG_DIASTOLIC_MORNING
import com.stacykarab.bloodpressuretracker.utils.StatisticsConsts.AVG_DIASTOLIC_NIGHT
import com.stacykarab.bloodpressuretracker.utils.StatisticsConsts.AVG_SYSTOLIC_AFTERNOON
import com.stacykarab.bloodpressuretracker.utils.StatisticsConsts.AVG_SYSTOLIC_EVENING
import com.stacykarab.bloodpressuretracker.utils.StatisticsConsts.AVG_SYSTOLIC_MORNING
import com.stacykarab.bloodpressuretracker.utils.StatisticsConsts.AVG_SYSTOLIC_NIGHT
import com.stacykarab.bloodpressuretracker.utils.StatisticsConsts.DIASTOLIC_PRESSURE
import com.stacykarab.bloodpressuretracker.utils.StatisticsConsts.SYSTOLIC_PRESSURE
import jakarta.persistence.AttributeOverride
import jakarta.persistence.AttributeOverrides
import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "user_daily_bp_statistics")
data class UserDailyBPStatistics(
    @EmbeddedId
    val id: UserDailyBpStatisticsId? = null,
    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    var user: User? = null,
    @Embedded
    @AttributeOverrides(
        AttributeOverride(name = SYSTOLIC_PRESSURE, column = Column(name = AVG_SYSTOLIC_NIGHT)),
        AttributeOverride(name = DIASTOLIC_PRESSURE, column = Column(name = AVG_DIASTOLIC_NIGHT))
    )
    var avgNight: BloodPressure? = null,
    @Embedded
    @AttributeOverrides(
        AttributeOverride(name = SYSTOLIC_PRESSURE, column = Column(name = AVG_SYSTOLIC_MORNING)),
        AttributeOverride(name = DIASTOLIC_PRESSURE, column = Column(name = AVG_DIASTOLIC_MORNING))
    )
    var avgMorning: BloodPressure? = null,
    @Embedded
    @AttributeOverrides(
        AttributeOverride(name = SYSTOLIC_PRESSURE, column = Column(name = AVG_SYSTOLIC_AFTERNOON)),
        AttributeOverride(name = DIASTOLIC_PRESSURE, column = Column(name = AVG_DIASTOLIC_AFTERNOON))
    )
    var avgAfternoon: BloodPressure? = null,
    @Embedded
    @AttributeOverrides(
        AttributeOverride(name = SYSTOLIC_PRESSURE, column = Column(name = AVG_SYSTOLIC_EVENING)),
        AttributeOverride(name = DIASTOLIC_PRESSURE, column = Column(name = AVG_DIASTOLIC_EVENING))
    )
    var avgEvening: BloodPressure? = null,
)