package com.example.mydiaryapp.data.source.local.room.user

import android.util.Log
import com.example.mydiaryapp.data.model.UserEntity
import com.example.mydiaryapp.data.source.local.room.DiaryDatabase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import kotlin.math.log

class UserRoomImpl @Inject constructor(private val database: DiaryDatabase) :
    UserRoomRepository {
    override suspend fun authUser(username: String, password: String): UserEntity? {
        return database.userDao().authUser(username, password)
    }

    override suspend fun getUser(userId: Int): Flow<UserEntity> {
        return database.userDao().getUser(userId)
    }

    override suspend fun addUser(userEntity: UserEntity):Long {
        return database.userDao().addUser(userEntity)
    }

    override suspend fun checkUserExist(username: String): UserEntity? {
        return database.userDao().checkUserExist(username)
    }

    override suspend fun updateData(userEntity: UserEntity) {
        Log.d("user room impl", "updateData: ${userEntity.id}")
        database.userDao().updateUser(userEntity)
    }


}