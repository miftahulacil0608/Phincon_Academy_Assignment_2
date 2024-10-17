package com.example.mydiaryapp.data

import com.example.mydiaryapp.data.model.UserEntity
import com.example.mydiaryapp.data.model.UserPreferencesDataStore
import com.example.mydiaryapp.domain.model.Session
import com.example.mydiaryapp.domain.model.User

object MapperDataClass {
    fun User.toUserEntity(): UserEntity {
        return UserEntity(
            username = this.username,
            email = this.email,
            password = this.password
        )
    }

    fun UserEntity.toUser(): User {
        return User(this.id, this.username, this.email, this.password)
    }

    fun UserPreferencesDataStore.toSession(): Session {
        return Session(
            this.idUser,
            this.username
        )
    }
}
