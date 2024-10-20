package com.example.mydiaryapp.data.source.local.datastore

import com.example.mydiaryapp.data.model.SettingDiary
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserDiaryDataStoreImpl @Inject constructor(private val dataStore: UserDiarySettingSharedPreferencesDataStore):
    UserDiaryDataStoreRepository {
    override suspend fun saveSession(
        idUser: Int
    ) {
        dataStore.saveSession(idUser)
    }
    override suspend fun logoutSession() {
        dataStore.logoutSession()
    }

    override fun getSessionId(): Int? {
        return dataStore.getSessionId()
    }

    override suspend fun saveSettingDiary(sortBy: String, orderBy: String) {
        dataStore.saveSettingDiary(sortBy, orderBy)
    }

    override fun getDiarySetting(): Flow<SettingDiary> {
        return dataStore.getSettingDiary()
    }

}