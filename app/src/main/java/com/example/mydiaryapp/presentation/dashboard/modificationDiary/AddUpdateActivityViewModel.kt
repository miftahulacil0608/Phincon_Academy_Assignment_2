package com.example.mydiaryapp.presentation.dashboard.modificationDiary

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mydiaryapp.domain.AuthenticationUseCase
import com.example.mydiaryapp.domain.DiaryUseCase
import com.example.mydiaryapp.domain.model.Diary
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddUpdateActivityViewModel @Inject constructor(
    private val diaryUseCase: DiaryUseCase
) : ViewModel() {

    fun addDiary(headline: String, message: String, diaryDate:String) {
        viewModelScope.launch {
            diaryUseCase.addDiary(headline, message,diaryDate)
        }
    }

    fun updateDiary(diary: Diary){
        viewModelScope.launch {
            diaryUseCase.updateDiary(diary)
        }
    }
}