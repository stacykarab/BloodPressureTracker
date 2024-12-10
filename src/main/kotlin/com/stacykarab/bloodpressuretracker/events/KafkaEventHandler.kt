package com.stacykarab.bloodpressuretracker.events

import com.stacykarab.bloodpressuretracker.service.CalculationServiceImpl
import com.stacykarab.bloodpressuretracker.service.NotificationService
import com.stacykarab.bloodpressuretracker.utils.StatisticsConsts
import mu.KotlinLogging
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

@Component
class KafkaEventHandler(
    private val calculationService: CalculationServiceImpl,
    private val notificationService: NotificationService,
) {

    @KafkaListener(
        topics = [StatisticsConsts.KAFKA_CONSUMER_BP_STATISTICS_TOPIC],
        groupId = StatisticsConsts.KAFKA_CONSUMER_BP_STATISTICS_GROUP_ID,
    )
    fun getBpStatistics(@Payload kafkaBpStatistics: KafkaBpStatistics) {
        logger.info { "Received new BP for user ${kafkaBpStatistics.userId}" }
        calculationService.addBpToCalculateAverage(kafkaBpStatistics)
        notificationService.notify(kafkaBpStatistics)
    }

}