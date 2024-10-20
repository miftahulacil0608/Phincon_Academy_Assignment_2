package com.example.mydiaryapp.presentation.dashboard.adapter

import com.example.mydiaryapp.domain.model.Diary

interface ItemClickListener {
    fun onClick(diary:Diary)
}