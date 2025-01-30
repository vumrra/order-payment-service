package com.payment.global.user.stub

data class UserStub(
    val userId: Long,
    val name: String,
    val email: String,
    val sex: Sex,
    val authority: Authority
)

data class UserExistsStub(
    val isExists: Boolean
)