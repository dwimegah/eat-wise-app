package com.belajar.capstoneapp.data.response

import com.belajar.capstoneap.model.Goal
import com.google.gson.annotations.SerializedName

data class CommonResponse (
    @SerializedName("success")
    val error: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: List<Goal>
)