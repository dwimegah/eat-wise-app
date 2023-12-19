package com.belajar.capstoneapp.data.pref

data class UserModel(
    val user: User,
    val token: String,
    val isLogin: Boolean
)

data class User(
    val email: String
)