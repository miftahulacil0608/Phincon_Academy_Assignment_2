package com.example.mydiaryapp.domain

import com.example.mydiaryapp.data.repository.UserDiaryRepository
import com.example.mydiaryapp.domain.model.User
import javax.inject.Inject

//TODO revisi aturannya. usercase data selesai aja
class AuthenticationUseCase @Inject constructor(
    private val userDiaryRepository: UserDiaryRepository
) {
    suspend fun register(user: User): Boolean {
        return userDiaryRepository.register(user)
    }

    suspend fun login(username: String, password: String):User? {
       return userDiaryRepository.login(username, password)
    }

    fun getSessionId() = userDiaryRepository.getSessionId()

    suspend fun logout(){
        userDiaryRepository.logout()
    }
}