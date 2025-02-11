package com.product.domain.product.persistence

import jakarta.persistence.*

@Entity
@Table(name = "tbl_category")
class Category(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val name: String,
) {
}
