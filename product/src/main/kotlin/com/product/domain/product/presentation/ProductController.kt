package com.product.domain.product.presentation

import com.product.domain.product.application.ProductService
import com.product.domain.product.dto.ProductAllDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/product")
class ProductController(
    private val productService: ProductService
) {

    @GetMapping
    fun queryAll(): ResponseEntity<ProductAllDto> {
        val response = productService.queryAllProducts()
        return ResponseEntity.ok(response)
    }

}
