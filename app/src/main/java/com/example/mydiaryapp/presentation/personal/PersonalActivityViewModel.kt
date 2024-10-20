package com.example.mydiaryapp.presentation.personal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mydiaryapp.domain.AuthenticationUseCase
import com.example.mydiaryapp.domain.DiaryUseCase
import com.example.mydiaryapp.domain.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonalActivityViewModel @Inject constructor(private val authenticationUseCase: AuthenticationUseCase):ViewModel(){

    suspend fun updateData(user: User):Boolean{
        return  authenticationUseCase.updateData(user)
    }
}