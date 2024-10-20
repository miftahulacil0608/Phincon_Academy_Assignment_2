package com.example.mydiaryapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Diary(
    val userOwnerId: Int,
    val diaryId: Int,
    val diaryHeadline: String,
    val diaryMessage: String,
    val diaryDate:String
):Parcelable
