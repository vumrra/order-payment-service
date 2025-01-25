package com.user.domain.user.application

import com.user.domain.user.dto.UserExistsDto
import com.user.domain.user.dto.UserInfoDto
import com.user.domain.user.persistence.User
import com.user.domain.user.persistence.UserRepository
import com.user.global.error.UserException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
) : UserService {

    @Transactional(readOnly = true)
    override fun queryById(userId: Long): UserInfoDto {
        val user = userRepository.findByIdOrNull(userId)
            ?: throw UserException("User Not Found by id: $userId", HttpStatus.NOT_FOUND)
        return mappingUserInfo(user)
    }

    @Transactional(readOnly = true)
    override fun existsById(userId: Long): UserExistsDto {
        val isExists = userRepository.existsById(userId)
        return UserExistsDto(isExists)
    }

    private fun mappingUserInfo(user: User) = UserInfoDto(
        userId = user.id,
        name = user.name,
        email = user.email,
        sex = user.sex,
        authority = user.authority
    )

}