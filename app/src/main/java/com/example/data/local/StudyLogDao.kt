package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.DailyStudyLog
import kotlinx.coroutines.flow.Flow

@Dao
interface StudyLogDao {
    @Query("SELECT * FROM study_logs WHERE dateString = :dateString LIMIT 1")
    fun getLogForDate(dateString: String): Flow<DailyStudyLog?>

    @Query("SELECT * FROM study_logs WHERE dateString = :dateString LIMIT 1")
    suspend fun getLogForDateSync(dateString: String): DailyStudyLog?

    @Query("SELECT * FROM study_logs ORDER BY dateString DESC")
    fun getAllLogs(): Flow<List<DailyStudyLog>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(log: DailyStudyLog)
}
