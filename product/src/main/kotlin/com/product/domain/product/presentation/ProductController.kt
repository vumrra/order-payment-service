package com.product.domain.product.presentation

import com.product.domain.product.application.ProductService
import com.product.domain.product.dto.CreateProductDto
import com.product.domain.product.persistence.ProductDocument
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/product")
class ProductController(
    private val productService: ProductService
) {

    /*
    @GetMapping
    fun queryProducts(
        @RequestParam categoryId: Long,
    ): ResponseEntity<ProductsDto> {
        val response = productService.queryProducts(categoryId)
        return ResponseEntity.ok(response)
    }
    */

    @PostMapping
    fun createProduct(
        @RequestBody dto: CreateProductDto
    ): ResponseEntity<Map<String, Long>> {
        val response = productService.createProduct(dto)
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(mapOf("productId" to response))
    }

    @GetMapping("/{product_id}")
    fun queryProduct(
        @PathVariable("product_id") productId: Long,
    ): ResponseEntity<ProductDocument> {
        val response = productService.queryProduct(productId)
        return ResponseEntity.ok(response)
    }

    // product:
    // Hibernate: select p1_0.id,p1_0.category_id,c1_0.id,c1_0.name,p1_0.created_date,p1_0.name,p1_0.price,p1_0.quantity,s1_0.id,s1_0.end_date,s1_0.name,s1_0.percentage,s1_0.start_date from tbl_product p1_0 join tbl_category c1_0 on c1_0.id=p1_0.category_id left join tbl_sale s1_0 on s1_0.id=p1_0.sale_id where p1_0.id=?

    // order:
    // Hibernate: select op1_0.id,op1_0.order_id,op1_0.product_id,op1_0.quantity from tbl_order o1_0 join tbl_order_product op1_0 on op1_0.order_id=o1_0.order_id where op1_0.product_id=?

}
