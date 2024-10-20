package com.example.mydiaryapp.data.source.local.room.diary

import com.example.mydiaryapp.data.model.DiaryEntity
import kotlinx.coroutines.flow.Flow

interface DiaryRoomRepository {
    suspend fun addDiary(diaryEntity: DiaryEntity)
    fun getDiary(diaryId:Int):Flow<DiaryEntity?>
    suspend fun updateDiary(diaryEntity: DiaryEntity)
    suspend fun deleteDiary(diaryEntity: DiaryEntity)
    fun searchDiary(userOwnerId: Int,query:String?):Flow<List<DiaryEntity>>
    fun getResultSortingListDiary(userOwnerId: Int, sortBy:String, sortOrder:String):Flow<List<DiaryEntity>>
    fun getDiaryRecentlyAdded(userOwnerId: Int):Flow<DiaryEntity?>
}