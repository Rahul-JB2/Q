package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tests")
data class TestItem(
    @PrimaryKey val id: Int,
    val testNumber: Int,
    val testName: String,
    val testType: String, // "PART_TEST" or "FULL_TEST"
    val pattern: String,  // "JEE MAIN & JEE ADV", "JEE MAIN"
    val examMode: String, // "Offline", "CBT"
    val testDate: String, // e.g. "04-Oct-2026"
    val epochDateMillis: Long,
    val physicsSyllabus: String,
    val mathSyllabus: String,
    val pchemSyllabus: String,
    val ichemSyllabus: String,
    val ochemSyllabus: String,
    val cumulativeNote: String = "",
    val isCompleted: Boolean = false,
    val previousTestRevisionDone: Boolean = false,
    val scorePhysics: Int? = null,
    val scoreChemistry: Int? = null,
    val scoreMath: Int? = null,
    val totalScore: Int? = null,
    val maxScore: Int = 300,
    val accuracyPercent: Float? = null,
    val testRank: String? = null,
    val analysisRemarks: String? = null
)
