package com.belajar.capstoneapp.data.response

import com.belajar.capstoneapp.data.pref.UserModel
import com.google.gson.annotations.SerializedName

data class UserResponse (
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("publicUrl")
    val publicUrl: String,
    @SerializedName("data")
    val user: UserModel,
)