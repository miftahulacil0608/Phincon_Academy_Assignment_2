package com.example.mydiaryapp.presentation.dashboard

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.mydiaryapp.domain.AuthenticationUseCase
import com.example.mydiaryapp.domain.DiaryUseCase
import com.example.mydiaryapp.domain.model.Diary
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashBoardViewModel @Inject constructor(
    private val authenticationUseCase: AuthenticationUseCase,
    private val diaryUseCase: DiaryUseCase
) :
    ViewModel() {
    private val _listDiary = MutableLiveData<List<Diary>?>()
    val listDiary: LiveData<List<Diary>?> = _listDiary

    private val _itemDiary = MutableLiveData<Diary?>()
    val itemDiary: LiveData<Diary?> = _itemDiary

    //dilakukan disini berarti untuk fitur listnya, bisa ditambahkan untuk dinit aja valuenya di dalam listDiary saja
    fun getListDiary() {
        viewModelScope.launch {
            diaryUseCase.getListDiary().collect {
                _listDiary.value = it
            }
        }
    }

    fun saveSettingDiary(sortBy: String, orderBy: String) {
        viewModelScope.launch {
            diaryUseCase.saveSettingDiary(sortBy, orderBy)
        }
    }

    fun getSettingDiary() = liveData {
        diaryUseCase.getDiarySetting().collect{
            emit(it)
            Log.d("Setting Diary User", "getSettingDiary: ${it.sortBy} and ${it.orderBy}")
        }

    }

    fun getResultSortingListDiary() = liveData{
        diaryUseCase.getResultSortingListDiary().collect{
            emit(it)
        }
    }

    fun search(query: String?) = liveData {
        diaryUseCase.search(query).collect {
            emit(it)
        }
    }

    fun getItemDiary(diaryId: Int) {
        viewModelScope.launch {
            diaryUseCase.getDiary(diaryId).collect {
                _itemDiary.value = it
            }
        }
    }

    fun deleteItemDiary(diary: Diary) {
        viewModelScope.launch {
            diaryUseCase.deleteDiary(diary)
        }
    }

    fun logout() {
        viewModelScope.launch {
            authenticationUseCase.logout()
        }
    }
}