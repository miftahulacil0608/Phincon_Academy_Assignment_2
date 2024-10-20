package com.example.mydiaryapp.data.source.local.datastore

import com.example.mydiaryapp.data.model.SettingDiary
import kotlinx.coroutines.flow.Flow

interface UserDiaryDataStoreRepository {
    suspend fun saveSession(idUser:Int)
    suspend fun logoutSession()
    fun getSessionId():Int?
    suspend fun saveSettingDiary(sortBy:String, orderBy:String)
    fun getDiarySetting(): Flow<SettingDiary>
}