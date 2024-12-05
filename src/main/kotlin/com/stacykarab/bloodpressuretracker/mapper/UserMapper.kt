package com.stacykarab.bloodpressuretracker.mapper

import com.stacykarab.bloodpressuretracker.dto.ChronicIllnessDto
import com.stacykarab.bloodpressuretracker.dto.UserCreateUpdateDto
import com.stacykarab.bloodpressuretracker.dto.UserDto
import com.stacykarab.bloodpressuretracker.entity.ChronicIllness
import com.stacykarab.bloodpressuretracker.entity.User
import com.stacykarab.bloodpressuretracker.repository.ChronicIllnessRepository
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings
import org.mapstruct.Named
import org.mapstruct.ReportingPolicy
import org.springframework.beans.factory.annotation.Autowired

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
abstract class UserMapper {

    @Autowired
    private lateinit var chronicIllnessRepository: ChronicIllnessRepository

    @Mappings(
        Mapping(source = "chronicIllnessIds", target = "chronicIllnesses", qualifiedByName = ["getChronicIllnesses"]),
    )
    abstract fun toUserEntity(dto: UserCreateUpdateDto): User

    @Mappings(
        Mapping(source = "chronicIllnesses", target = "chronicIllnesses", qualifiedByName = ["toChronicIllnessDto"]),
    )
    abstract fun toUserDto(dto: User): UserDto

    @Named("getChronicIllnesses")
    fun getChronicIllnesses(chronicIllnessIds: List<Long>): List<ChronicIllness> {
        return chronicIllnessIds
            .map { chronicIllnessRepository.findById(it).orElseThrow() }
            .toList()
    }

    @Named("toChronicIllnessDto")
    fun toChronicIllnessDto(chronicIllnesses: List<ChronicIllness>): List<ChronicIllnessDto> {
        return chronicIllnesses
            .map { ChronicIllnessDto(it.id, it.name, it.bpRelated) }
            .toList()
    }
}