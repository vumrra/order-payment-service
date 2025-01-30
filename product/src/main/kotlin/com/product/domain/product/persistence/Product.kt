package com.product.domain.product.persistence

import com.product.global.error.ProductException
import jakarta.persistence.*
import org.springframework.http.HttpStatus
import java.time.LocalDateTime

@Entity
@Table(name = "tbl_product")
class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val name: String,
    val price: Int,
    var quantity: Int,
    val createdDate: LocalDateTime = LocalDateTime.now(),
) {

    fun deduction(count: Int) {
        if (quantity - count >= 0) {
            quantity -= count
        } else {
            throw ProductException("재고가 부족합니다.", HttpStatus.BAD_REQUEST)
        }
    }

    companion object {
        fun of(name: String, price: Int, quantity: Int) =
            Product(
                name = name,
                price = price,
                quantity = quantity,
                createdDate = LocalDateTime.now()
            )
    }
}