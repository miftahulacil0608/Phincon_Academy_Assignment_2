package com.example.mydiaryapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: Int? = null,
    val username: String,
    val email: String,
    val password: String
) : Parcelable
