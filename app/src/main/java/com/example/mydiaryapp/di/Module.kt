package com.example.mydiaryapp.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.example.mydiaryapp.data.repository.AuthRepository
import com.example.mydiaryapp.data.repository.AuthRepositoryImpl
import com.example.mydiaryapp.data.source.local.datastore.LocalDataSourceAccountPreferencesImpl
import com.example.mydiaryapp.data.source.local.datastore.LocalDataSourceAccountPreferencesRepository
import com.example.mydiaryapp.data.source.local.datastore.SharedPreferencesDataStore
import com.example.mydiaryapp.data.source.local.room.DiaryDatabase
import com.example.mydiaryapp.data.source.local.room.LocalDataAccountUserImpl
import com.example.mydiaryapp.data.source.local.room.LocalDataAccountUserRepository
import com.example.mydiaryapp.domain.AuthenticationUseCase
import com.example.mydiaryapp.presentation.authentication.AuthenticationViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {

    private val Context.dataStoreContext: DataStore<Preferences> by preferencesDataStore(name = "user_setting")

    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context): Context {
        return context
    }

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.dataStoreContext
    }

    @Provides
    @Singleton
    fun provideSharedPreferencesDataStore(dataStoreContext: DataStore<Preferences>): SharedPreferencesDataStore {
        return SharedPreferencesDataStore(dataStoreContext)
    }

    @Provides
    @Singleton
    fun provideDiaryDatabase(@ApplicationContext context: Context): DiaryDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            klass = DiaryDatabase::class.java,
            name = "diary_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideLocalDataSourceAccountPreferencesRepository(dataStoreSharedPreferences: SharedPreferencesDataStore): LocalDataSourceAccountPreferencesRepository {
        return LocalDataSourceAccountPreferencesImpl(dataStoreSharedPreferences)
    }

    @Provides
    @Singleton
    fun provideLocalDataAccountUserRepository(diaryDataBase: DiaryDatabase): LocalDataAccountUserRepository {
        return LocalDataAccountUserImpl(diaryDataBase)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        localDataAccountUserRepository: LocalDataAccountUserRepository,
        localDataSourceAccountPreferencesRepository: LocalDataSourceAccountPreferencesRepository
    ): AuthRepository {
        return AuthRepositoryImpl(
            localDataAccountUserRepository,
            localDataSourceAccountPreferencesRepository
        )
    }

    @Provides
    @Singleton
    fun provideAuthUseCase(authRepository: AuthRepository):AuthenticationUseCase{
        return AuthenticationUseCase(authRepository)
    }

    @Provides
    @Singleton
    fun provideAuthViewModel(authenticationUseCase: AuthenticationUseCase):AuthenticationViewModel{
        return AuthenticationViewModel(authenticationUseCase)
    }

}