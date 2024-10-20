package com.example.mydiaryapp.data.repository

import com.example.mydiaryapp.data.MapperDataClass.toUser
import com.example.mydiaryapp.data.MapperDataClass.toUserEntity
import com.example.mydiaryapp.data.MapperDataClass.toUserEntityEdit
import com.example.mydiaryapp.data.source.local.datastore.UserDiaryDataStoreRepository
import com.example.mydiaryapp.data.source.local.room.user.UserRoomRepository
import com.example.mydiaryapp.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserDiaryRepositoryImpl @Inject constructor(
    private val databaseUser: UserRoomRepository,
    private val dataStoreUser: UserDiaryDataStoreRepository
) : UserDiaryRepository {
    override suspend fun register(user: User): Boolean {
        val getUserExist = databaseUser.checkUserExist(user.username)
        return when (getUserExist) {
            null -> {
                //return type must be long. karena udah merupakan default documentaionnya
                val resultID = databaseUser.addUser(user.toUserEntity())
                dataStoreUser.saveSession(resultID.toInt())
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
            dataStoreUser.saveSession(it.id)
        }
        return authUser?.toUser()
    }

    override suspend fun logout() {
        dataStoreUser.logoutSession()
    }

    override fun getSessionId(): Int? {
        return dataStoreUser.getSessionId()
    }

    override suspend fun getUser(userId: Int): Flow<User> {
        return databaseUser.getUser(userId).map {
            it.toUser()
        }
    }

    override suspend fun updateData(user: User):Boolean {
        return try {
            databaseUser.updateData(user.toUserEntityEdit())
            true
        } catch (e: Exception) {
            false
        }
    }


}