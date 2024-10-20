package com.example.mydiaryapp.data.source.local.room.user

import com.example.mydiaryapp.data.model.UserEntity
import kotlinx.coroutines.flow.Flow

interface UserRoomRepository {
    suspend fun authUser(username: String, password: String): UserEntity?
    suspend fun getUser(userId:Int): Flow<UserEntity>
    suspend fun addUser(userEntity: UserEntity):Long
    suspend fun checkUserExist(username: String):UserEntity?
    suspend fun updateData(userEntity: UserEntity)

}