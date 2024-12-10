package com.stacykarab.bloodpressuretracker.service

import com.stacykarab.bloodpressuretracker.dto.BPNotificationDto
import com.stacykarab.bloodpressuretracker.entity.BpDisorder
import com.stacykarab.bloodpressuretracker.events.KafkaBpStatistics
import com.stacykarab.bloodpressuretracker.utils.StatisticsConsts
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class NotificationServiceImpl(
    val kafkaTemplate: KafkaTemplate<String, BPNotificationDto>
) : NotificationService {

    override fun notify(kafkaBpStatistics: KafkaBpStatistics) {
        val systolicPressure = kafkaBpStatistics.systolicPressure
        val diastolicPressure = kafkaBpStatistics.diastolicPressure
        when {
            systolicPressure in BpDisorder.HYPOTENSION.systolic
                    || diastolicPressure in BpDisorder.HYPOTENSION.diastolic -> BpDisorder.getMessage(BpDisorder.HYPOTENSION)
            systolicPressure in BpDisorder.HYPERTENSION.systolic
                    || diastolicPressure in BpDisorder.HYPERTENSION.diastolic -> BpDisorder.getMessage(BpDisorder.HYPERTENSION)
            else -> null
        }?.let {
            kafkaTemplate.send(
                StatisticsConsts.KAFKA_PRODUCER_NOTIFICATIONS_TOPIC,
                BPNotificationDto(
                    systolic = kafkaBpStatistics.systolicPressure,
                    diastolic = kafkaBpStatistics.diastolicPressure,
                    message = it
                )
            )
        }
    }
}


