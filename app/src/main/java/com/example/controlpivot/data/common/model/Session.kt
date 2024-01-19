package com.example.controlpivot.data.common.model

import com.google.gson.annotations.SerializedName

data class Session(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("user_name")
    val userName: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("role")
    val role: String
)
