package com.product.domain.product.presentation

import com.product.domain.product.application.ProductCommandService
import com.product.domain.product.application.ProductQueryService
import com.product.domain.product.dto.CreateProductDto
import com.product.domain.product.persistence.ProductDocument
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/product")
class ProductController(
    private val productCommand: ProductCommandService,
    private val productQuery: ProductQueryService,
) {

    @PostMapping
    fun createProduct(
        @RequestBody dto: CreateProductDto
    ): ResponseEntity<Map<String, Long>> {
        val response = productCommand.createProduct(dto)
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(mapOf("productId" to response))
    }

    @GetMapping("/{product_id}")
    fun queryProduct(
        @PathVariable("product_id") productId: Long,
    ): ResponseEntity<ProductDocument> {
        val response = productQuery.queryProduct(productId)
        return ResponseEntity.ok(response)
    }

}
