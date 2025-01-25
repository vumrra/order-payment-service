package com.user.domain.auth.persistence

import org.springframework.data.repository.CrudRepository

interface RefreshTokenRepository : CrudRepository<RefreshToken, Long>