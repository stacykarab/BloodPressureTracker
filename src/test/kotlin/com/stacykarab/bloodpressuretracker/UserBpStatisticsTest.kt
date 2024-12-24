package com.stacykarab.bloodpressuretracker

import com.stacykarab.bloodpressuretracker.events.KafkaBpStatistics
import com.stacykarab.bloodpressuretracker.utils.StatisticsConsts
import com.stacykarab.bloodpressuretracker.utils.TestDtos
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.greaterThan
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.KafkaContainer
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName
import java.time.LocalDate
import java.time.LocalDateTime


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = [BloodPressureTrackerApplication::class])
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
class UserBpStatisticsTest {

    @LocalServerPort
    private var port: Int = 0

    @Autowired
    private lateinit var kafkaTemplate: KafkaTemplate<String, KafkaBpStatistics>

    companion object {

        @Container
        private val postgresSQLContainer =
            PostgreSQLContainer<Nothing>("postgres:15-alpine").apply {
                start()
            }

        @Container
        @ServiceConnection
        private val kafka =
            KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest"))

        @JvmStatic
        @DynamicPropertySource
        fun registerProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url") { postgresSQLContainer.jdbcUrl }
            registry.add("spring.datasource.username") { postgresSQLContainer.username }
            registry.add("spring.datasource.password") { postgresSQLContainer.password }
        }
    }

    @Test
    fun `test creating and getting a user`() {
        val userDto = TestDtos.userCreateUpdateDto
        val createdUserId = given()
            .contentType(ContentType.JSON)
            .body(userDto)
            .port(port)
            .`when`()
            .post("/api/v1/users")
            .then()
            .statusCode(200)
            .body("name", equalTo(userDto.name))
            .body("gender", equalTo(userDto.gender.name))
            .body("height", equalTo(userDto.height))
            .body("weight", equalTo(userDto.weight))
            .body("avgDiastolic", equalTo(userDto.avgDiastolic))
            .body("avgSystolic", equalTo(userDto.avgSystolic))
            .body("smoking", equalTo(userDto.smoking))
            .body("chronicIllnesses[0].id", equalTo(userDto.chronicIllnessIds[0].toInt()))
            .extract().body().jsonPath().getLong("id")

        given()
            .port(port)
            .`when`()["/api/v1/users/{id}", createdUserId]
            .then()
            .statusCode(200)
            .body("id", equalTo(createdUserId.toInt()))
            .body("name", equalTo(userDto.name))
    }

    @Test
    fun `test getting all users by filter`() {
        val createdUserResponse = given()
            .contentType(ContentType.JSON)
            .body(TestDtos.userCreateUpdateDto)
            .port(port)
            .`when`()
            .post("/api/v1/users")
            .then()
            .statusCode(200)
            .extract().body().jsonPath()

        given()
            .port(port)
            .`when`()["/api/v1/users?userIds={ids}&name={name}",
            createdUserResponse.getLong("id"), createdUserResponse.getString("name")]
            .then()
            .statusCode(200)
            .body("size()", greaterThan(0))
    }

    @Test
    fun `test processing user bp from kafka`() {
        val userId = given()
            .contentType(ContentType.JSON)
            .body(TestDtos.userCreateUpdateDto)
            .port(port)
            .`when`()
            .post("/api/v1/users")
            .then()
            .statusCode(200)
            .extract().body().jsonPath().getLong("id")

        kafkaTemplate.send(
            StatisticsConsts.KAFKA_CONSUMER_BP_STATISTICS_TOPIC,
            KafkaBpStatistics(
                userId = userId,
                timestamp = LocalDateTime.of(2024, 1, 1, 17, 0, 0),
                systolicPressure = 120,
                diastolicPressure = 75,
            )
        )

        kafkaTemplate.send(
            StatisticsConsts.KAFKA_CONSUMER_BP_STATISTICS_TOPIC,
            KafkaBpStatistics(
                userId = userId,
                timestamp = LocalDateTime.of(2024, 1, 1, 18, 30, 0),
                systolicPressure = 135,
                diastolicPressure = 95,
            )
        )

        Thread.sleep(2000);

        given()
            .port(port)
            .`when`()["/api/v1/users/bp/{userId}?from={from}&to={to}", userId, "2023-12-31", "2024-12-31"]
            .then()
            .statusCode(200)
            .body("size()", greaterThan(0))
            .body("bpStatistics[0].date", equalTo(LocalDate.of(2024, 1, 1).toString()))
            .body("bpStatistics[0].avgSystolicAfternoon", equalTo(120))
            .body("bpStatistics[0].avgDiastolicAfternoon", equalTo(75))
    }
}