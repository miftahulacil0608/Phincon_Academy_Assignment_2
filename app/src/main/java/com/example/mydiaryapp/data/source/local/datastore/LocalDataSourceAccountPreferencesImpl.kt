package com.example.mydiaryapp.data.source.local.datastore

import com.example.mydiaryapp.data.model.UserPreferencesDataStore
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSourceAccountPreferencesImpl @Inject constructor(private val dataStore: SharedPreferencesDataStore):
    LocalDataSourceAccountPreferencesRepository {
    override suspend fun saveSession(
        idUser: Int,
        username: String,
        email: String,
        password: String
    ) {
        dataStore.saveSession(idUser, username, email, password)
    }

    override fun getSession(): Flow<UserPreferencesDataStore?> {
        return dataStore.getSession()
    }

    override suspend fun logoutSession() {
        dataStore.logoutSession()
    }

}