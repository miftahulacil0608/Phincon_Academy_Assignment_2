package com.example.mydiaryapp.data.source.local.room.user

import com.example.mydiaryapp.data.model.UserEntity

interface UserRoomRepository {
    suspend fun authUser(username: String, password: String): UserEntity?
    suspend fun addUser(userEntity: UserEntity):Long
    suspend fun checkUserExist(username: String):UserEntity?


}