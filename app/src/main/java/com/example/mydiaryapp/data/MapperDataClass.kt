package com.example.mydiaryapp.data

import com.example.mydiaryapp.data.model.DiaryEntity
import com.example.mydiaryapp.data.model.SettingDiary
import com.example.mydiaryapp.data.model.UserEntity
import com.example.mydiaryapp.domain.model.Diary
import com.example.mydiaryapp.domain.model.SettingDiaryUser
import com.example.mydiaryapp.domain.model.User

object MapperDataClass {
    fun User.toUserEntity(): UserEntity {
        return UserEntity(
            username = this.username,
            email = this.email,
            password = this.password
        )
    }

    fun UserEntity.toUser(): User {
        return User(this.id, this.username, this.email, this.password)
    }

    fun List<DiaryEntity>.toDiary(): List<Diary> {
        return this.map {
            Diary(it.userOwnerId, it.diaryId, it.diaryHeadline, it.diaryMessage, diaryDate = it.diaryDate)
        }
    }
    fun DiaryEntity.toDiary(): Diary {
        return Diary(
            this.userOwnerId,
            this.diaryId,
            this.diaryHeadline,
            this.diaryMessage,
            this.diaryDate
        )
    }

    fun Diary.toDiaryEntity(): DiaryEntity {
        return DiaryEntity(
            this.userOwnerId,
            this.diaryId,
            this.diaryHeadline,
            this.diaryMessage,
            this.diaryDate
        )
    }

    fun SettingDiary.toSettingDiaryUser():SettingDiaryUser{
        return SettingDiaryUser(this.sortBy, this.orderBy)
    }
}
