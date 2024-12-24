package com.stacykarab.bloodpressuretracker.repository

import com.stacykarab.bloodpressuretracker.dto.UserRequestFilterDto
import com.stacykarab.bloodpressuretracker.entity.Gender
import com.stacykarab.bloodpressuretracker.entity.User
import org.springframework.data.jpa.domain.Specification
import java.time.LocalDate

object UserSpecifications {

    private fun hasIdIn(ids: List<Long>?): Specification<User> {
        return Specification { root, _, criteriaBuilder ->
            criteriaBuilder.and(root.get<Long>("id").`in`(ids))
        }
    }

    private fun hasName(name: String?): Specification<User> {
        return Specification { root, _, criteriaBuilder ->
            criteriaBuilder.equal(root.get<String>("name"), name)
        }
    }

    private fun hasGenderIn(gender: Gender): Specification<User> {
        return Specification { root, _, criteriaBuilder ->
            criteriaBuilder.equal(root.get<String>("gender"), gender.name)
        }
    }

    private fun isSmoking(smoking: Boolean): Specification<User> {
        return Specification { root, _, criteriaBuilder ->
            criteriaBuilder.equal(root.get<String>("smoking"), smoking)
        }
    }

    private fun hasHeightIn(height: IntRange): Specification<User> {
        return Specification { root, _, criteriaBuilder ->
            criteriaBuilder.and(root.get<String>("height").`in`(height))
        }
    }

    private fun hasWeightIn(weight: IntRange): Specification<User> {
        return Specification { root, _, criteriaBuilder ->
            criteriaBuilder.and(root.get<String>("weight").`in`(weight))
        }
    }

    private fun hasBirthDateFrom(birthDateFrom: LocalDate): Specification<User> {
        return Specification { root, _, criteriaBuilder ->
            criteriaBuilder.greaterThan(root.get("birthDate"), birthDateFrom)
        }
    }

    private fun hasBirthDateTo(birthDateTo: LocalDate): Specification<User> {
        return Specification { root, _, criteriaBuilder ->
            criteriaBuilder.lessThan(root.get("birthDate"), birthDateTo)
        }
    }

    fun getUserSpecification(filter: UserRequestFilterDto?): Specification<User> {
        var spec: Specification<User> = Specification.where(null)
        if (!filter?.userIds.isNullOrEmpty()) {
            spec = spec.and(hasIdIn(filter?.userIds))
        }
        if (!filter?.name.isNullOrEmpty()) {
            spec = spec.and(hasName(filter?.name))
        }
        if (!filter?.gender.isNullOrEmpty()) {
            spec = spec.and(hasGenderIn(filter?.gender?.get(0)!!))
        }
        if (filter?.smoking != null) {
            spec = spec.and(isSmoking(filter.smoking))
        }
        if (filter?.height != null) {
            spec = spec.and(hasHeightIn(filter.height))
        }
        if (filter?.weight != null) {
            spec = spec.and(hasWeightIn(filter.weight))
        }
        if (filter?.birthDateFrom != null) {
            spec = spec.and(hasBirthDateFrom(filter.birthDateFrom))
        }
        if (filter?.birthDateTo != null) {
            spec = spec.and(hasBirthDateTo(filter.birthDateTo))
        }

        return spec
    }
}