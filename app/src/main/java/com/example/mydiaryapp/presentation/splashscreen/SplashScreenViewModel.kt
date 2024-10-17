package com.example.mydiaryapp.presentation.splashscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mydiaryapp.domain.AuthenticationUseCase
import com.example.mydiaryapp.domain.model.Session
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(private val useCase: AuthenticationUseCase) :
    ViewModel() {
    private val _session: MutableLiveData<Session?> = MutableLiveData()
    val session : LiveData<Session?> = _session
    init {
        getSession()
    }

    private fun getSession() {
        viewModelScope.launch {
            useCase.getSession().collectLatest {
                _session.value = it
            }
        }
    }
}