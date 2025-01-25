package com.order.domain.order.application

import com.order.domain.order.dto.OrderReqDto
import com.order.global.error.OrderException
import com.order.global.feign.client.UserClient
import com.order.global.util.UserContextUtil
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OrderServiceImpl(
    private val userContextUtil: UserContextUtil,
    private val userClient: UserClient
) : OrderService {

    @Transactional
    override fun order(dto: OrderReqDto) {
        isExistUser()
    }

    private fun isExistUser() {
        val currentUserId = userContextUtil.getCurrentUserId()
        val isExists = userClient.exists(currentUserId).isExists
        if (isExists.not()) {
            throw OrderException("User Not Found by id: $currentUserId", HttpStatus.NOT_FOUND)
        }
    }

}
