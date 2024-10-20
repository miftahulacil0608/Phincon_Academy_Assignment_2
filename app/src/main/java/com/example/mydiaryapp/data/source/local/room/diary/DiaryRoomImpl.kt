package com.example.mydiaryapp.data.source.local.room.diary

import com.example.mydiaryapp.data.model.DiaryEntity
import com.example.mydiaryapp.data.source.local.room.DiaryDatabase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DiaryRoomImpl @Inject constructor(private val database:DiaryDatabase):DiaryRoomRepository {
    override suspend fun addDiary(diaryEntity: DiaryEntity) {
        database.diaryDao().addDiary(diaryEntity)
    }

    override fun getDiary(diaryId:Int): Flow<DiaryEntity?> {
        return database.diaryDao().getDiary(diaryId)
    }

    override suspend fun updateDiary(diaryEntity: DiaryEntity) {
        database.diaryDao().updateDiary(diaryEntity)
    }

    override suspend fun deleteDiary(diaryEntity: DiaryEntity) {
        database.diaryDao().deleteDiary(diaryEntity)
    }

    override fun searchDiary(userOwnerId: Int,query: String?): Flow<List<DiaryEntity>> {
        return database.diaryDao().search(userOwnerId,query)
    }

    override fun getResultSortingListDiary(
        userOwnerId: Int,
        sortBy: String,
        sortOrder: String
    ): Flow<List<DiaryEntity>> {
        return database.diaryDao().getResultSortingListDiary(userOwnerId, sortBy, sortOrder)
    }

    override fun getDiaryRecentlyAdded(userOwnerId: Int): Flow<DiaryEntity?> {
        return database.diaryDao().getDiaryRecentlyAdded(userOwnerId)
    }
}