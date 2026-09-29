package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "study_logs")
data class DailyStudyLog(
    @PrimaryKey val dateString: String, // e.g. "2026-09-28"
    val physicsHours: Float = 3.5f,
    val chemistryHours: Float = 2.5f,
    val mathHours: Float = 2.5f,
    val targetHours: Float = 10.0f,
    val physicsQuestions: Int = 35,
    val chemistryQuestions: Int = 35,
    val mathQuestions: Int = 30,
    val questionsSolved: Int = 100,
    val targetQuestionsQuota: Int = 100,
    val masteryPoints: Int = 350,
    val streak: Int = 14,
    val isLockdownActive: Boolean = true,
    val lockdownChapterName: String = "Kinematics & NLM",
    val activePassName: String? = null, // e.g. "🎧 Pocket FM Story Pass", "📺 YouTube Educational Pass"
    val activePassRemainingMinutes: Int = 0,
    val activePassExpiryEpoch: Long = 0L,
    val notes: String = ""
)
