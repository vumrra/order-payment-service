package com.user.domain.user.application

import com.user.domain.user.dto.UserInfoDto
import com.user.domain.user.persistence.User
import com.user.global.util.UserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserServiceImpl(
    private val userUtil: UserUtil
) : UserService {

    @Transactional(readOnly = true)
    override fun queryUserInfo(): UserInfoDto {
        val currentUser = userUtil.getCurrentUser()
        return mappingUserInfo(currentUser)
    }

    private fun mappingUserInfo(user: User) = UserInfoDto(
        userId = user.id,
        name = user.name,
        email = user.email,
        sex = user.sex,
        authority = user.authority
    )

}