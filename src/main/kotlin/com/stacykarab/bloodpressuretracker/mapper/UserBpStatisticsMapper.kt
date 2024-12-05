package com.stacykarab.bloodpressuretracker.mapper

import com.stacykarab.bloodpressuretracker.dto.UserBpStatisticsDto
import com.stacykarab.bloodpressuretracker.dto.UserDailyBPStatisticsDto
import com.stacykarab.bloodpressuretracker.dto.UserDto
import com.stacykarab.bloodpressuretracker.entity.UserDailyBPStatistics
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings
import org.mapstruct.Named
import org.mapstruct.ReportingPolicy

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
)
abstract class UserBpStatisticsMapper {

    @Mappings(
        Mapping(source = "user", target = "userDto"),
        Mapping(source = "statisticsList", target = "bpStatistics", qualifiedByName = ["getBpStatistics"]),
    )
    abstract fun toUserBpStatisticsDto(
        user: UserDto,
        statisticsList: List<UserDailyBPStatistics>
    ): UserBpStatisticsDto

    @Mappings(
        Mapping(source = "id.date", target = "date"),
        Mapping(source = "avgNight.diastolicPressure", target = "avgDiastolicNight"),
        Mapping(source = "avgNight.systolicPressure", target = "avgSystolicNight"),
        Mapping(source = "avgAfternoon.systolicPressure", target = "avgSystolicAfternoon"),
        Mapping(source = "avgAfternoon.diastolicPressure", target = "avgDiastolicAfternoon"),
        Mapping(source = "avgEvening.diastolicPressure", target = "avgDiastolicEvening"),
        Mapping(source = "avgEvening.systolicPressure", target = "avgSystolicEvening"),
        Mapping(source = "avgMorning.diastolicPressure", target = "avgDiastolicMorning"),
        Mapping(source = "avgMorning.systolicPressure", target = "avgSystolicMorning"),
    )
    abstract fun toUserDailyBPStatisticsDto(userDailyBPStatistics: UserDailyBPStatistics): UserDailyBPStatisticsDto

    @Named("getBpStatistics")
    fun getBpStatistics(statisticsList: List<UserDailyBPStatistics>): List<UserDailyBPStatisticsDto> {
        return statisticsList.map { toUserDailyBPStatisticsDto(it) }
    }
}
