package com.example.mydiaryapp.data.source.local.room.user

import com.example.mydiaryapp.data.model.UserEntity
import com.example.mydiaryapp.data.source.local.room.DiaryDatabase
import javax.inject.Inject

class UserRoomImpl @Inject constructor(private val database: DiaryDatabase) :
    UserRoomRepository {
    override suspend fun authUser(username: String, password: String): UserEntity? {
        return database.userDao().authUser(username, password)
    }

    override suspend fun addUser(userEntity: UserEntity):Long {
        return database.userDao().addUser(userEntity)
    }

    override suspend fun checkUserExist(username: String): UserEntity? {
        return database.userDao().checkUserExist(username)
    }


}