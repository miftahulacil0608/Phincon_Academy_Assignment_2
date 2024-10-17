package com.example.mydiaryapp.data.source.local.datastore

import com.example.mydiaryapp.data.model.UserPreferencesDataStore
import kotlinx.coroutines.flow.Flow

interface LocalDataSourceAccountPreferencesRepository {
    suspend fun saveSession(idUser:Int, username:String, email:String ,password:String)
    fun getSession():Flow<UserPreferencesDataStore?>
    suspend fun logoutSession()
}