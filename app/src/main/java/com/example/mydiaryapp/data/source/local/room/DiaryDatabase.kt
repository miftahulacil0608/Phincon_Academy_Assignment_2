package com.example.mydiaryapp.data.source.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mydiaryapp.data.model.DiaryEntity
import com.example.mydiaryapp.data.model.UserEntity
import com.example.mydiaryapp.data.source.local.room.diary.DiaryDao
import com.example.mydiaryapp.data.source.local.room.user.UserDao

//TODO buat clean
@Database(entities = [UserEntity::class, DiaryEntity::class], version = 1, exportSchema = false)
abstract class DiaryDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun diaryDao(): DiaryDao
    companion object {
        private var INSTANCE: DiaryDatabase? = null
        fun getInstance(context: Context): DiaryDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context = context.applicationContext,
                    klass = DiaryDatabase::class.java,
                    "diary_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}