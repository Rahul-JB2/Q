package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chapters")
data class ChapterItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val subject: String, // "PHYSICS", "CHEMISTRY", "MATHEMATICS"
    val subSubject: String, // "PHYSICS", "P_CHEM", "I_CHEM", "O_CHEM", "MATH"
    val chapterName: String,
    val partTestId: Int, // The Part Test introducing this chapter (1..8)
    val isCumulativeInTests: String = "", // e.g. "PT 1 to 8"
    
    // 6 Core Pillars from JEE Preparation requirements:
    val theoryCompleted: Boolean = false,
    val shortNotesCompleted: Boolean = false, // 1-page conclusion / formula notes
    val mathongoQuestionsCompleted: Boolean = false, // MathonGo Concept Builder questions
    val moduleEx2Completed: Boolean = false, // Module Ex-2 (Advanced exercise)
    val eklavyaCompleted: Boolean = false, // Eklavya batch high-level problems
    val previousTestRevisionCompleted: Boolean = false, // Revise previous test papers 1 day before test
    
    // Solved Counters:
    val mathongoTotal: Int = 20,
    val mathongoDone: Int = 0,
    val moduleEx2Total: Int = 25,
    val moduleEx2Done: Int = 0,
    val eklavyaTotal: Int = 15,
    val eklavyaDone: Int = 0,
    
    val notesSummary: String = "",
    val doubts: String = "",
    val formulaSheetSnippet: String = "",
    val isWeakTopic: Boolean = false,
    val lastStudiedTimestamp: Long = 0L
) {
    val completionPercent: Int
        get() {
            var completedCount = 0
            if (theoryCompleted) completedCount++
            if (shortNotesCompleted) completedCount++
            if (mathongoQuestionsCompleted) completedCount++
            if (moduleEx2Completed) completedCount++
            if (eklavyaCompleted) completedCount++
            if (previousTestRevisionCompleted) completedCount++
            return ((completedCount * 100) / 6).coerceIn(0, 100)
        }
}
