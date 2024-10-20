package com.example.mydiaryapp.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.example.mydiaryapp.data.repository.UserDiaryRepository
import com.example.mydiaryapp.data.repository.UserDiaryRepositoryImpl
import com.example.mydiaryapp.data.repository.DiaryRepository
import com.example.mydiaryapp.data.repository.DiaryRepositoryImpl
import com.example.mydiaryapp.data.source.local.datastore.UserDiaryDataStoreImpl
import com.example.mydiaryapp.data.source.local.datastore.UserDiaryDataStoreRepository
import com.example.mydiaryapp.data.source.local.datastore.UserDiarySettingSharedPreferencesDataStore
import com.example.mydiaryapp.data.source.local.room.DiaryDatabase
import com.example.mydiaryapp.data.source.local.room.diary.DiaryRoomImpl
import com.example.mydiaryapp.data.source.local.room.diary.DiaryRoomRepository
import com.example.mydiaryapp.data.source.local.room.user.UserRoomImpl
import com.example.mydiaryapp.data.source.local.room.user.UserRoomRepository
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

    /*@Provides
    @Singleton
    fun provideDiarySharedPreferencesDataStore()*/

    @Provides
    @Singleton
    fun provideSharedPreferencesDataStore(dataStoreContext: DataStore<Preferences>): UserDiarySettingSharedPreferencesDataStore {
        return UserDiarySettingSharedPreferencesDataStore(dataStoreContext)
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
    fun provideLocalDataSourceAccountPreferencesRepository(dataStoreSharedPreferences: UserDiarySettingSharedPreferencesDataStore): UserDiaryDataStoreRepository {
        return UserDiaryDataStoreImpl(dataStoreSharedPreferences)
    }

    @Provides
    @Singleton
    fun provideLocalDataAccountUserRepository(diaryDataBase: DiaryDatabase): UserRoomRepository {
        return UserRoomImpl(diaryDataBase)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        userRoomRepository: UserRoomRepository,
        userDiaryDataStoreRepository: UserDiaryDataStoreRepository
    ): UserDiaryRepository {
        return UserDiaryRepositoryImpl(
            userRoomRepository,
            userDiaryDataStoreRepository
        )
    }

    @Provides
    @Singleton
    fun provideLocalDataDiaryRepository(database:DiaryDatabase):DiaryRoomRepository{
        return DiaryRoomImpl(database)
    }

    @Provides
    @Singleton
    fun provideDiaryRepository(
        diaryRoomRepository: DiaryRoomRepository,
        userDiaryDataStoreRepository: UserDiaryDataStoreRepository
    ): DiaryRepository {
        return DiaryRepositoryImpl(diaryRoomRepository, userDiaryDataStoreRepository)
    }

    /*@Provides
    @Singleton
    fun provideAuthUseCase(authRepository: UserDiaryRepository):AuthenticationUseCase{
        return AuthenticationUseCase(authRepository)
    }

    @Provides
    @Singleton
    fun provideAuthViewModel(authenticationUseCase: AuthenticationUseCase):AuthenticationViewModel{
        return AuthenticationViewModel(authenticationUseCase)
    }*/

}