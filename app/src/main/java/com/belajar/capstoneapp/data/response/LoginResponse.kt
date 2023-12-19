package com.belajar.capstoneapp.data.response

import com.google.gson.annotations.SerializedName

data class LoginResponse (
    @SerializedName("success")
    val error: Boolean,
    @SerializedName("message")
    val message: String
)