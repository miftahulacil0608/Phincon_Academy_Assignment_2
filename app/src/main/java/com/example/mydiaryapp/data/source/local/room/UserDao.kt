package com.example.mydiaryapp.data.source.local.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.mydiaryapp.data.model.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    //TODO update using flow
    //TODO wajib tambah embeded untuk one to many

    /*@Query("SELECT * FROM user_entity")
    fun getUserData():Flow<UserEntity>*/

    @Query("SELECT * FROM user_entity where username LIKE :username AND password LIKE :password LIMIT 1")
    suspend fun authUser(username: String, password: String): UserEntity?

    @Query("SELECT * FROM user_entity where username LIKE :username LIMIT 1")
    suspend fun checkUserExist(username: String): UserEntity?

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addUser(userEntity: UserEntity):Long

    /*@Update
    suspend fun updateUser(userEntity: UserEntity)

    @Delete
    suspend fun delete(userEntity: UserEntity)*/

}