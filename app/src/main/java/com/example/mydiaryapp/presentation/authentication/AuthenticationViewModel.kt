package com.example.mydiaryapp.presentation.authentication

import androidx.lifecycle.ViewModel
import com.example.mydiaryapp.domain.AuthenticationUseCase
import com.example.mydiaryapp.domain.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(private val useCase: AuthenticationUseCase) :
    ViewModel() {
    suspend fun registerNewAccount(user: User):Boolean {
        return useCase.register(user)
    }
    suspend fun loginAccount(username: String, password: String):Boolean {
        val userDataLogin = useCase.login(username, password)
        return userDataLogin!=null
    }
}