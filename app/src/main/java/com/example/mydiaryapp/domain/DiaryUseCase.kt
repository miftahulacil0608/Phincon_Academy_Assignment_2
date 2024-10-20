package com.example.mydiaryapp.domain

import com.example.mydiaryapp.data.repository.DiaryRepository
import com.example.mydiaryapp.data.repository.UserDiaryRepository
import com.example.mydiaryapp.domain.model.Diary
import com.example.mydiaryapp.domain.model.SettingDiaryUser
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

class DiaryUseCase @Inject constructor(
    private val diaryRepository: DiaryRepository,
    private val userDiaryRepository: UserDiaryRepository
) {

    val userOwnerId = userDiaryRepository.getSessionId()

    suspend fun addDiary(headline: String, message: String,diaryDate:String) {
        userOwnerId?.let {
            diaryRepository.addDiary(it, headline, message, diaryDate)
        }
    }

    fun getDiary(diaryId: Int): Flow<Diary?> {
        return diaryRepository.getDiary(diaryId)
    }

    fun getDiaryRecentlyAdded():Flow<Diary?>{
        return if (userOwnerId!=null) {
            diaryRepository.getDiaryRecentlyAdded(userOwnerId)
        }else{
            emptyFlow()
        }
    }

    suspend fun updateDiary(diary: Diary) {
        diaryRepository.updateDiary(diary)
    }

    suspend fun deleteDiary(diary: Diary) {
        diaryRepository.deleteDiary(diary)
    }

    fun search(query: String?): Flow<List<Diary>> {
        return if (userOwnerId != null) {
            diaryRepository.search(userOwnerId, query)
        } else {
            emptyFlow()
        }
    }

    suspend fun saveSettingDiary(sortBy: String, orderBy: String) {
        diaryRepository.saveDiarySetting(sortBy, orderBy)
    }

    fun getDiarySetting(): Flow<SettingDiaryUser> {
        return diaryRepository.getDiarySetting()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun getResultSortingListDiary(): Flow<List<Diary>> {
        //Flow yang digunakan untuk mengubah aliran data (Flow) menjadi aliran baru berdasarkan nilai terbaru yang diterima.
        return diaryRepository.getDiarySetting().flatMapLatest {
            if (userOwnerId!=null){
                diaryRepository.getResultSortingListDiary(userOwnerId,it.sortBy, it.orderBy)
            }else{
                emptyFlow()
            }
        }
    }
}