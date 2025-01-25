package com.user.global.util

import com.user.domain.user.persistence.User
import com.user.domain.user.persistence.UserRepository
import com.user.global.error.UserException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class UserUtil(
    private val userRepository: UserRepository
) {

    fun getCurrentUserId(): Long {
        return SecurityContextHolder.getContext().authentication.name.toLong()
    }

    fun getCurrentUser(): User {
        return userRepository.findByIdOrNull(getCurrentUserId())
            ?: throw UserException("Current User Not Found", HttpStatus.NOT_FOUND)
    }

}