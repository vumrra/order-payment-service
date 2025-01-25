package com.user.domain.auth.application

import com.user.domain.auth.dto.LoginReqDto
import com.user.domain.auth.dto.LoginResDto
import com.user.domain.auth.dto.SignupDto
import com.user.domain.auth.persistence.RefreshToken
import com.user.domain.auth.persistence.RefreshTokenRepository
import com.user.domain.user.persistence.User
import com.user.domain.user.persistence.UserRepository
import com.user.global.error.UserException
import com.user.global.jwt.JwtGenerator
import com.user.global.jwt.JwtProperties
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthServiceImpl(
    private val passwordEncoder: PasswordEncoder,
    private val userRepository: UserRepository,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val jwtProperties: JwtProperties,
    private val jwtGenerator: JwtGenerator
) : AuthService {

    @Transactional
    override fun signup(dto: SignupDto) {
        isNotDuplicateEmail(dto.email)
        saveUser(dto)
    }

    @Transactional
    override fun login(dto: LoginReqDto): LoginResDto {
        val user = findUserByEmail(dto.email)
        validPassword(user, dto.password)

        val tokenDto = jwtGenerator.generateToken(user.id.toString(), user.authority)
        saveRefreshToken(user.id, tokenDto.refreshToken)

        return LoginResDto(
            accessToken = tokenDto.accessToken,
            refreshToken = tokenDto.refreshToken
        )
    }

    private fun findUserByEmail(email: String): User =
        userRepository.findByEmail(email)
            ?: throw UserException("User not found by email: $email", HttpStatus.NOT_FOUND)

    private fun validPassword(user: User, rawPassword: String) {
        val isMatched = passwordEncoder.matches(rawPassword, user.password)
        if (isMatched.not()) {
            throw UserException("Passwords do not match", HttpStatus.BAD_REQUEST)
        }
    }

    private fun saveRefreshToken(userId: Long, token: String) {
        val refreshToken = RefreshToken(
            userId = userId,
            token = token,
            expirationTime = jwtProperties.refreshExp
        )
        refreshTokenRepository.save(refreshToken)
    }

    private fun isNotDuplicateEmail(email: String) {
        val isDuplicate = userRepository.existsByEmail(email)
        if (isDuplicate) {
            throw UserException("Email already exists", HttpStatus.BAD_REQUEST)
        }
    }

    private fun saveUser(dto: SignupDto) {
        val encryptPassword = passwordEncode(dto)
        val user = User.of(dto, encryptPassword)
        userRepository.save(user)
    }

    private fun passwordEncode(dto: SignupDto) = passwordEncoder.encode(dto.password)

}
