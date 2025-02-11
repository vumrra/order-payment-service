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
    val price: Long,
    var quantity: Int,
    @JoinColumn(name = "category_id", nullable = false)
    @ManyToOne(cascade = [(CascadeType.ALL)])
    val category: Category,
    @JoinColumn(name = "sale_id", nullable = true, unique = false)
    @OneToOne(cascade = [(CascadeType.ALL)])
    val sale: Sale? = null,
    val createdDate: LocalDateTime = LocalDateTime.now(),
) {

    fun deduction(count: Int) {
        if (quantity - count >= 0) {
            quantity -= count
        } else {
            throw ProductException("재고가 부족합니다.", HttpStatus.BAD_REQUEST)
        }
    }

    fun add(count: Int) {
        quantity += count
    }

    companion object {
        fun of(name: String, price: Long, quantity: Int, category: Category) =
            Product(
                name = name,
                price = price,
                quantity = quantity,
                category = category,
                createdDate = LocalDateTime.now()
            )
    }
}