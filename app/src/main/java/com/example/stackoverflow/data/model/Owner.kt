package com.example.stackoverflow.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Owner(
    @SerializedName("accept_rate") val acceptRate: Int,
    @SerializedName("display_name") val displayName: String,
    @SerializedName("link") val link: String,
    @SerializedName("profile_image") val profileImage: String,
    @SerializedName("reputation") val reputation: Int,
    @SerializedName("user_id") val userId: Int,
    @SerializedName("user_type") val userType: String
)