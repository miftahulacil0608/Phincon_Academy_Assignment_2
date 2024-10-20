package com.example.mydiaryapp.data.repository

import com.example.mydiaryapp.domain.model.Diary
import com.example.mydiaryapp.domain.model.SettingDiaryUser
import kotlinx.coroutines.flow.Flow


interface DiaryRepository {
    suspend fun addDiary(userOwnerId:Int, headline:String, message:String, diaryDate:String)
    fun getListDiary(userOwnerId: Int): Flow<List<Diary>>
    suspend fun getDiary(diaryId:Int):Flow<Diary?>
    suspend fun updateDiary(diaryDiary: Diary)
    suspend fun deleteDiary(diaryDiary: Diary)
    fun search(userOwnerId: Int,query:String?):Flow<List<Diary>>
    fun getResultSortingListDiary(userOwnerId:Int, sortBy:String, sortOrder:String):Flow<List<Diary>>
    suspend fun saveDiarySetting(sortBy:String, orderBy:String)
    suspend fun getDiarySetting(): Flow<SettingDiaryUser>

}