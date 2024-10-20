package com.example.mydiaryapp.data.source.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.mydiaryapp.data.model.SettingDiary
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking


class UserDiarySettingSharedPreferencesDataStore(private val context: DataStore<Preferences>) {
    suspend fun saveSession(id: Int) {
        context.edit {
            it[KEY_ID_USER] = id
        }
    }

    fun getSessionId() = runBlocking {
        context.data.first()[KEY_ID_USER]
    }

    suspend fun saveSettingDiary(sortBy: String, sortOrder: String) {
        context.edit {
            it[KEY_SORT_BY] = sortBy
            it[KEY_ORDER_BY] = sortOrder
        }
    }

    fun getSettingDiary(): Flow<SettingDiary> {
        return context.data.map {
            SettingDiary(
                sortBy = it[KEY_SORT_BY] ?: "headline",
                orderBy = it[KEY_ORDER_BY] ?: "ASC"
            )
        }
    }

    suspend fun logoutSession() {
        context.edit {
            it.clear()
        }
    }

    companion object {

        private val KEY_ID_USER = intPreferencesKey("KEY_ID_USER")
        private val KEY_SORT_BY = stringPreferencesKey("KEY_SORT_BY")
        private val KEY_ORDER_BY = stringPreferencesKey("KEY_ORDER_BY")

        //cara manual
        @Volatile
        private var INSTANCE: UserDiarySettingSharedPreferencesDataStore? = null
        fun getInstance(dataStore: DataStore<Preferences>): UserDiarySettingSharedPreferencesDataStore {
            return INSTANCE ?: synchronized(this) {
                val instance = UserDiarySettingSharedPreferencesDataStore(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}