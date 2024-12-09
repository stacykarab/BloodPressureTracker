package com.stacykarab.bloodpressuretracker.events

import com.stacykarab.bloodpressuretracker.service.CalculationService
import mu.KotlinLogging
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

@Component
class KafkaEventHandler(
    private val calculationService: CalculationService,
) {

    @KafkaListener(
        topics = ["bp-statistics"],
        groupId = "bp-statistics-consumers",
        properties = ["spring.json.value.default.type=com.stacykarab.bloodpressuretracker.events.KafkaBpStatistics"]
    )
    fun getBpStatistics(@Payload kafkaBpStatistics: KafkaBpStatistics) {
        logger.info { "Received new BP for user ${kafkaBpStatistics.userId}" }
        calculationService.addBpToCalculateAverage(kafkaBpStatistics)
    }

}