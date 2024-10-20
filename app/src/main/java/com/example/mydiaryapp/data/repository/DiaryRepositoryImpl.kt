package com.example.mydiaryapp.data.repository

import com.example.mydiaryapp.data.MapperDataClass.toDiary
import com.example.mydiaryapp.data.MapperDataClass.toDiaryEntity
import com.example.mydiaryapp.data.MapperDataClass.toSettingDiaryUser
import com.example.mydiaryapp.data.model.DiaryEntity
import com.example.mydiaryapp.data.source.local.datastore.UserDiaryDataStoreRepository
import com.example.mydiaryapp.data.source.local.room.diary.DiaryRoomRepository
import com.example.mydiaryapp.domain.model.Diary
import com.example.mydiaryapp.domain.model.SettingDiaryUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DiaryRepositoryImpl @Inject constructor(private val diaryRoomRepository: DiaryRoomRepository, private val userDiaryDataStoreRepository: UserDiaryDataStoreRepository) :
    DiaryRepository {
    override suspend fun addDiary(userOwnerId: Int, headline: String, message: String, diaryDate:String) {
        diaryRoomRepository.addDiary(
            DiaryEntity(
                userOwnerId = userOwnerId,
                diaryHeadline = headline,
                diaryMessage = message,
                diaryDate = diaryDate
            )
        )
    }

    override fun getListDiary(userOwnerId: Int): Flow<List<Diary>> {
        return diaryRoomRepository.getListDiary(userOwnerId).map {
            it.toDiary()
        }
    }

    override suspend fun getDiary(diaryId: Int): Flow<Diary?> {
        return diaryRoomRepository.getDiary(diaryId).map {
            it?.toDiary()
        }
    }

    override suspend fun updateDiary(diaryDiary: Diary) {
        diaryRoomRepository.updateDiary(diaryDiary.toDiaryEntity())
    }

    override suspend fun deleteDiary(diaryDiary: Diary) {
        diaryRoomRepository.deleteDiary(diaryDiary.toDiaryEntity())
    }

    override fun search(userOwnerId: Int,query: String?): Flow<List<Diary>> {
        return diaryRoomRepository.searchDiary(userOwnerId,query).map {
            it.toDiary()
        }
    }

    override fun getResultSortingListDiary(
        userOwnerId: Int,
        sortBy: String,
        sortOrder: String
    ): Flow<List<Diary>> {
        return diaryRoomRepository.getResultSortingListDiary(userOwnerId, sortBy, sortOrder).map {
            it.toDiary()
        }
    }

    override suspend fun saveDiarySetting(sortBy: String, orderBy: String) {
        userDiaryDataStoreRepository.saveSettingDiary(sortBy, orderBy)
    }

    override suspend fun getDiarySetting(): Flow<SettingDiaryUser> {
        return userDiaryDataStoreRepository.getDiarySetting().map {
            it.toSettingDiaryUser()
        }
    }


}