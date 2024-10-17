package com.example.mydiaryapp.presentation.authentication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mydiaryapp.domain.AuthenticationUseCase
import com.example.mydiaryapp.domain.model.Session
import com.example.mydiaryapp.domain.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
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