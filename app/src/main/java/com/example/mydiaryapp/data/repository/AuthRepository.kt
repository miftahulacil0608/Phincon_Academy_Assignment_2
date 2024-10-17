package com.example.mydiaryapp.data.repository

import com.example.mydiaryapp.data.model.UserEntity
import com.example.mydiaryapp.data.model.UserPreferencesDataStore
import com.example.mydiaryapp.domain.model.Session
import com.example.mydiaryapp.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun register(user: User):Boolean
    suspend fun login(username:String, password:String):User?
    suspend fun getSession():Flow<Session?>
}