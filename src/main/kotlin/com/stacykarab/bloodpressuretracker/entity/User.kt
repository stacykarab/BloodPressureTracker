package com.stacykarab.bloodpressuretracker.entity

import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToMany
import jakarta.persistence.Table
import java.time.LocalDate

@Entity
@Table(name = "users")
data class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    var name: String? = null,
    @Enumerated(EnumType.STRING)
    var gender: Gender? = null,
    var birthDate: LocalDate? = null,
    var smoking: Boolean? = null,
    var height: Int? = null,
    var weight: Int? = null,
    var avgSystolic: Int? = null,
    var avgDiastolic: Int? = null,
    @ManyToMany
    @JoinTable(
        name = "user_chronic_illnesses",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "chronic_illness_id")]
    )
    var chronicIllnesses: MutableList<ChronicIllness>? = mutableListOf(),
)