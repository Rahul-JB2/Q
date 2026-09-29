package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.data.model.ChapterItem
import com.example.data.model.DailyGoalItem
import com.example.data.model.DailyStudyLog
import com.example.data.model.TestItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        TestItem::class,
        ChapterItem::class,
        DailyGoalItem::class,
        DailyStudyLog::class
    ],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun testDao(): TestDao
    abstract fun chapterDao(): ChapterDao
    abstract fun dailyGoalDao(): DailyGoalDao
    abstract fun studyLogDao(): StudyLogDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "jee_super50_database"
                )
                .fallbackToDestructiveMigration()
                .addCallback(DatabaseCallback(scope))
                .build()
                INSTANCE = instance
                instance
            }
        }

        private class DatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateInitialData(database)
                    }
                }
            }
        }

        suspend fun populateInitialData(database: AppDatabase) {
            val testDao = database.testDao()
            val chapterDao = database.chapterDao()
            if (testDao.getCount() == 0) {
                testDao.insertTests(PreloadedData.defaultTests)
            }
            if (chapterDao.getCount() == 0) {
                chapterDao.insertChapters(PreloadedData.defaultChapters)
            }
        }
    }
}
