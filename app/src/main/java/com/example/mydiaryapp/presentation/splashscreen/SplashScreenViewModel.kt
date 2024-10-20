package com.example.mydiaryapp.presentation.splashscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mydiaryapp.domain.AuthenticationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(private val useCase: AuthenticationUseCase) :
    ViewModel() {
    private val _session: MutableLiveData<Int?> = MutableLiveData()
    val session : LiveData<Int?> = _session
    init {
        _session.value = useCase.getSessionId()
    }
}