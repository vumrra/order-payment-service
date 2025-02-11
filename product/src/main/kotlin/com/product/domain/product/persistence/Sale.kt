package com.product.domain.product.persistence

import jakarta.persistence.*
import java.time.LocalDateTime


@Entity
@Table(name = "tbl_sale")
class Sale(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val name: String,
    val percentage: Int,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
) {
}
