package com.example.mydiaryapp.data.source.local.room

import android.database.sqlite.SQLiteConstraintException
import com.example.mydiaryapp.data.model.UserEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

//TODO tambahin hilt ya abis ini
class LocalDataAccountUserImpl @Inject constructor(private val database: DiaryDatabase) :
    LocalDataAccountUserRepository {
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