package com.example.mydiaryapp.data.source.local.room.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mydiaryapp.data.model.UserEntity

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