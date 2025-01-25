package com.user.domain.user.persistence

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "tbl_user")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long = 0,

    @Column(name = "email", nullable = false)
    val email: String,

    @Column(name = "name", nullable = false)
    val name: String,

    @Column(name = "password", nullable = false)
    val password: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "sex", nullable = false)
    val sex: Sex,

    @Enumerated(EnumType.STRING)
    @Column(name = "authority", nullable = false)
    val authority: Authority,

    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),
) {
}

enum class Sex {
    MALE, FEMALE
}

enum class Authority {
    USER, ADMIN
}
