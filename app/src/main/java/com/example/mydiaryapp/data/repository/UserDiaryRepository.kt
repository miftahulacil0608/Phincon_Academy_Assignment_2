package com.example.mydiaryapp.data.repository

import com.example.mydiaryapp.domain.model.SettingDiaryUser
import com.example.mydiaryapp.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserDiaryRepository {
    suspend fun register(user: User):Boolean
    suspend fun login(username:String, password:String):User?
    suspend fun logout()
    fun getSessionId():Int?
}