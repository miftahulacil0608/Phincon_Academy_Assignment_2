package com.example.mydiaryapp.domain

import android.util.Log
import com.example.mydiaryapp.data.repository.UserDiaryRepository
import com.example.mydiaryapp.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import javax.inject.Inject

class AuthenticationUseCase @Inject constructor(
    private val userDiaryRepository: UserDiaryRepository
) {
    suspend fun register(user: User): Boolean {
        return userDiaryRepository.register(user)
    }

    suspend fun login(username: String, password: String): User? {
        return userDiaryRepository.login(username, password)
    }

    fun getSessionId() = userDiaryRepository.getSessionId()

    suspend fun getUser(): Flow<User> {
        val userId = getSessionId()
        Log.d("UserId", "getUser: $userId")
        return if (userId != null){
            userDiaryRepository.getUser(userId)
        }else{
            emptyFlow()
        }
    }

    suspend fun updateData(user: User):Boolean{
        val userId = getSessionId()
        val isValid = if (userId !=null){
            userDiaryRepository.updateData(User(id = userId, user.username, user.email, user.password))
        } else {
            false
        }
        return isValid
    }

    suspend fun logout() {
        userDiaryRepository.logout()
    }




}