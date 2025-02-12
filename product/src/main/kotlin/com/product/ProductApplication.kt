package com.product

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class ProductApplication
fun main(args: Array<String>) {
    runApplication<ProductApplication>(*args)
}
