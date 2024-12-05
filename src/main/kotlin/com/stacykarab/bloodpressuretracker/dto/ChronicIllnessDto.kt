package com.stacykarab.bloodpressuretracker.dto

import com.stacykarab.bloodpressuretracker.entity.BpDisorder

data class ChronicIllnessDto(
    val id: Long? = null,
    val name: String? = null,
    val bpRelated: BpDisorder? = null,
)