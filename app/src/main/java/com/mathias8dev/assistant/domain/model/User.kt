package com.mathias8dev.assistant.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class User(
    val username: String,
    val personality: String,
    val firstname: String,
    val lastname: String
): Parcelable
