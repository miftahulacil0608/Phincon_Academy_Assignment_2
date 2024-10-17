package com.example.mydiaryapp.domain

import com.example.mydiaryapp.data.source.local.datastore.LocalDataSourceAccountPreferencesRepository
import com.example.mydiaryapp.data.source.local.room.LocalDataAccountUserRepository
import com.example.mydiaryapp.data.model.UserEntity
import com.example.mydiaryapp.data.repository.AuthRepository
import com.example.mydiaryapp.domain.model.Session
import com.example.mydiaryapp.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

//TODO revisi aturannya. usercase data selesai aja
class AuthenticationUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend fun register(user: User): Boolean {
        return authRepository.register(user)
    }

    suspend fun login(username: String, password: String):User? {
       return authRepository.login(username, password)
    }

    suspend fun getSession(): Flow<Session?> {
        return authRepository.getSession()
    }
}