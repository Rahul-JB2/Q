package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.ChapterItem
import kotlinx.coroutines.flow.Flow

@Dao
interface ChapterDao {
    @Query("SELECT * FROM chapters ORDER BY partTestId ASC, subject ASC, chapterName ASC")
    fun getAllChapters(): Flow<List<ChapterItem>>

    @Query("SELECT * FROM chapters WHERE id = :id LIMIT 1")
    suspend fun getChapterById(id: Int): ChapterItem?

    @Query("SELECT * FROM chapters WHERE subject = :subject ORDER BY partTestId ASC")
    fun getChaptersBySubject(subject: String): Flow<List<ChapterItem>>

    @Query("SELECT * FROM chapters WHERE partTestId = :partTestId ORDER BY subject ASC")
    fun getChaptersForTest(partTestId: Int): Flow<List<ChapterItem>>

    @Query("SELECT * FROM chapters WHERE partTestId = :partTestId ORDER BY subject ASC")
    suspend fun getChaptersForTestSync(partTestId: Int): List<ChapterItem>

    @Query("SELECT * FROM chapters WHERE partTestId <= :partTestId ORDER BY subject ASC")
    suspend fun getCumulativeChaptersUpToTestSync(partTestId: Int): List<ChapterItem>

    @Query("SELECT COUNT(*) FROM chapters")
    suspend fun getCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChapters(chapters: List<ChapterItem>)

    @Update
    suspend fun updateChapter(chapter: ChapterItem)

    @Query("DELETE FROM chapters")
    suspend fun clearAll()
}
