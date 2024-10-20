package com.example.mydiaryapp.presentation

object Helper {
    fun String.toFormatDatesUI():String{
        return this.split(" ").take(3).joinToString(" ")
    }
}