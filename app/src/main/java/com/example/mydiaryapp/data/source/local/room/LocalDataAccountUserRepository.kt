package com.example.mydiaryapp.data.source.local.room

import com.example.mydiaryapp.data.model.UserEntity
import kotlinx.coroutines.flow.Flow

interface LocalDataAccountUserRepository {
    suspend fun authUser(username: String, password: String): UserEntity?
    suspend fun addUser(userEntity: UserEntity):Long
    suspend fun checkUserExist(username: String):UserEntity?

}