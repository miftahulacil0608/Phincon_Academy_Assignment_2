package com.example.mydiaryapp.domain.model

data class User(val id: Int ?= null, val username: String, val email: String, val password: String)
