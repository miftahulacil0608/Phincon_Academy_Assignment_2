package com.example.mydiaryapp.data.repository

import android.util.Log
import com.example.mydiaryapp.data.MapperDataClass.toSession
import com.example.mydiaryapp.data.MapperDataClass.toUser
import com.example.mydiaryapp.data.MapperDataClass.toUserEntity
import com.example.mydiaryapp.data.model.UserEntity
import com.example.mydiaryapp.data.model.UserPreferencesDataStore
import com.example.mydiaryapp.data.source.local.datastore.LocalDataSourceAccountPreferencesRepository
import com.example.mydiaryapp.data.source.local.room.LocalDataAccountUserRepository
import com.example.mydiaryapp.domain.model.Session
import com.example.mydiaryapp.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val databaseUser: LocalDataAccountUserRepository,
    private val dataStoreUser: LocalDataSourceAccountPreferencesRepository
) : AuthRepository {
    override suspend fun register(user: User): Boolean {
        val getUserExist = databaseUser.checkUserExist(user.username)
        return when (getUserExist) {
            null -> {
                /*saveSession(
                    databaseUser.addUser(user.toUserEntity()).toInt(),
                    user.username,
                    user.email,
                    user.password
                )*/
                val resultID = databaseUser.addUser(user.toUserEntity())
                Log.d("resultID", "register: $resultID")
                true
            }
            else -> {
                false
            }
        }
    }

    override suspend fun login(username: String, password: String): User? {
        val authUser = databaseUser.authUser(username, password)
        authUser?.let {
            saveSession(it.id, it.username, it.email, it.password)
        }
        return authUser?.toUser()
    }

    override suspend fun getSession(): Flow<Session?> {
        return dataStoreUser.getSession().map { it?.toSession() }
    }

    private suspend fun saveSession(id: Int, username: String, email: String, password: String) {
        dataStoreUser.saveSession(id, username, email, password)
    }
}