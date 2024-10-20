package com.example.mydiaryapp.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "diary_entity")
@Parcelize
data class DiaryEntity(
    @ColumnInfo("user_owner_id")
    val userOwnerId: Int,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("diary_id")
    val diaryId: Int = 0,
    @ColumnInfo("diary_headline")
    val diaryHeadline: String,
    @ColumnInfo("diary_message")
    val diaryMessage: String,
    @ColumnInfo("diary_date")
    val diaryDate: String
    /*@ColumnInfo("diary_mood")
    val diaryMood: String,
    @ColumnInfo("diary_date")
    val diaryDate: String*/
) : Parcelable
