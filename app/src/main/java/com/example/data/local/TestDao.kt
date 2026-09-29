package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.TestItem
import kotlinx.coroutines.flow.Flow

@Dao
interface TestDao {
    @Query("SELECT * FROM tests ORDER BY epochDateMillis ASC, testNumber ASC")
    fun getAllTests(): Flow<List<TestItem>>

    @Query("SELECT * FROM tests WHERE id = :id LIMIT 1")
    suspend fun getTestById(id: Int): TestItem?

    @Query("SELECT * FROM tests WHERE isCompleted = 0 ORDER BY epochDateMillis ASC LIMIT 1")
    fun getNextUpcomingTest(): Flow<TestItem?>

    @Query("SELECT * FROM tests WHERE isCompleted = 0 ORDER BY epochDateMillis ASC LIMIT 1")
    suspend fun getNextUpcomingTestSync(): TestItem?

    @Query("SELECT COUNT(*) FROM tests")
    suspend fun getCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTests(tests: List<TestItem>)

    @Update
    suspend fun updateTest(test: TestItem)

    @Query("DELETE FROM tests")
    suspend fun clearAll()
}
