package com.example.mydiaryapp.data.source.local.room.diary

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.mydiaryapp.data.model.DiaryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DiaryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addDiary(diaryEntity: DiaryEntity)

    //TODO Sementara untuk selectnya
    @Query("SELECT * FROM diary_entity WHERE user_owner_id LIKE :userOwnerId")
    fun getListDiary(userOwnerId: Int): Flow<List<DiaryEntity>>

    @Query("SELECT * FROM diary_entity WHERE diary_id LIKE :diaryId")
    fun getDiary(diaryId: Int): Flow<DiaryEntity?>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateDiary(diaryEntity: DiaryEntity)

    @Delete
    suspend fun deleteDiary(diaryEntity: DiaryEntity)

    @Query("SELECT * FROM diary_entity WHERE user_owner_id LIKE :userOwnerId AND (diary_headline LIKE :query OR diary_message LIKE :query)")
    fun search(userOwnerId: Int, query: String?): Flow<List<DiaryEntity>>

    @Query(
        """
        SELECT * FROM diary_entity 
        WHERE user_owner_id = :userOwnerId
        ORDER BY
        CASE WHEN :sortBy = 'headline' AND :sortOrder = 'ASC' THEN diary_headline END ASC,
        CASE WHEN :sortBy = 'headline' AND :sortOrder = 'DESC' THEN diary_headline END DESC,
         CASE WHEN :sortBy = 'date' AND :sortOrder = 'ASC' THEN diary_message END ASC,
        CASE WHEN :sortBy = 'date' AND :sortOrder = 'DESC' THEN diary_message END DESC
    """
    )
    fun getResultSortingListDiary(userOwnerId: Int, sortBy: String, sortOrder: String):Flow<List<DiaryEntity>>
}