package com.example.mydiaryapp.data.source.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.mydiaryapp.data.model.UserPreferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class SharedPreferencesDataStore (private val context: DataStore<Preferences>) {
    suspend fun saveSession(id: Int,userName: String, email: String,password: String) {
        context.edit {
            it[KEY_ID_USER] = id
            it[KEY_USERNAME] = userName
            it[KEY_EMAIL] = email
            it[KEY_PASSWORD] = password
        }
    }

    fun getSession(): Flow<UserPreferencesDataStore?> {
        return context.data.map {
            UserPreferencesDataStore(
                idUser = it[KEY_ID_USER]?:0,
                it[KEY_USERNAME]?:"",
                it[KEY_EMAIL]?:"",
                it[KEY_PASSWORD]?:""
            )
        }
    }

    suspend fun logoutSession() {
        context.edit {
            it.clear()
        }
    }
    companion object{

        private val KEY_ID_USER = intPreferencesKey("KEY_ID_USER")
        private val KEY_USERNAME = stringPreferencesKey("KEY_USERNAME")
        private  val KEY_EMAIL = stringPreferencesKey("KEY_EMAIL")
        private val KEY_PASSWORD = stringPreferencesKey("KEY_PASSWORD")

        @Volatile
        private var INSTANCE : SharedPreferencesDataStore ?= null
         fun getInstance(dataStore: DataStore<Preferences>):SharedPreferencesDataStore{
            return INSTANCE ?: synchronized(this){
                val instance = SharedPreferencesDataStore(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}