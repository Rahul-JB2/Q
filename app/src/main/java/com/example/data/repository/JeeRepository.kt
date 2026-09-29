package com.example.data.repository

import com.example.data.local.AppDatabase
import com.example.data.local.PreloadedData
import com.example.data.model.ChapterItem
import com.example.data.model.DailyGoalItem
import com.example.data.model.DailyStudyLog
import com.example.data.model.TestItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class JeeRepository(private val database: AppDatabase) {

    private val testDao = database.testDao()
    private val chapterDao = database.chapterDao()
    private val dailyGoalDao = database.dailyGoalDao()
    private val studyLogDao = database.studyLogDao()

    val allTests: Flow<List<TestItem>> = testDao.getAllTests()
    val nextUpcomingTest: Flow<TestItem?> = testDao.getNextUpcomingTest()
    val allChapters: Flow<List<ChapterItem>> = chapterDao.getAllChapters()

    fun getChaptersBySubject(subject: String): Flow<List<ChapterItem>> =
        chapterDao.getChaptersBySubject(subject)

    fun getChaptersForTest(testId: Int): Flow<List<ChapterItem>> =
        chapterDao.getChaptersForTest(testId)

    fun getGoalsForDate(dateString: String): Flow<List<DailyGoalItem>> =
        dailyGoalDao.getGoalsForDate(dateString)

    fun getStudyLogForDate(dateString: String): Flow<DailyStudyLog?> =
        studyLogDao.getLogForDate(dateString)

    fun getAllStudyLogs(): Flow<List<DailyStudyLog>> =
        studyLogDao.getAllLogs()

    suspend fun updateTestScores(
        test: TestItem,
        pScore: Int,
        cScore: Int,
        mScore: Int,
        rank: String,
        remarks: String
    ) = withContext(Dispatchers.IO) {
        val total = pScore + cScore + mScore
        val accuracy = if (test.maxScore > 0) (total.toFloat() / test.maxScore) * 100f else 0f
        val updated = test.copy(
            isCompleted = true,
            scorePhysics = pScore,
            scoreChemistry = cScore,
            scoreMath = mScore,
            totalScore = total,
            accuracyPercent = accuracy,
            testRank = rank,
            analysisRemarks = remarks
        )
        testDao.updateTest(updated)
    }

    suspend fun ensureInitialized() = withContext(Dispatchers.IO) {
        AppDatabase.populateInitialData(database)
    }

    suspend fun updateChapter(chapter: ChapterItem) = withContext(Dispatchers.IO) {
        chapterDao.updateChapter(chapter)
    }

    suspend fun updateTest(test: TestItem) = withContext(Dispatchers.IO) {
        testDao.updateTest(test)
    }

    suspend fun toggleGoalCompletion(goal: DailyGoalItem) = withContext(Dispatchers.IO) {
        val updated = goal.copy(
            isCompleted = !goal.isCompleted,
            completedCount = if (!goal.isCompleted) goal.targetCount else 0
        )
        dailyGoalDao.updateGoal(updated)

        // Also sync with chapter if chapterId is valid
        if (goal.chapterId > 0 && updated.isCompleted) {
            val chapter = chapterDao.getChapterById(goal.chapterId)
            chapter?.let {
                val syncedChapter = when (goal.taskType) {
                    "THEORY" -> it.copy(theoryCompleted = true)
                    "SHORT_NOTES" -> it.copy(shortNotesCompleted = true)
                    "MATHONGO_CBQ" -> it.copy(mathongoQuestionsCompleted = true)
                    "MODULE_EX2" -> it.copy(moduleEx2Completed = true)
                    "EKLAVYA" -> it.copy(eklavyaCompleted = true)
                    "PREV_TEST_REV" -> it.copy(previousTestRevisionCompleted = true)
                    else -> it
                }
                chapterDao.updateChapter(syncedChapter)
            }
        }
    }

    suspend fun addCustomGoal(goal: DailyGoalItem) = withContext(Dispatchers.IO) {
        dailyGoalDao.insertGoal(goal)
    }

    suspend fun deleteGoal(id: Int) = withContext(Dispatchers.IO) {
        dailyGoalDao.deleteGoal(id)
    }

    suspend fun saveStudyLog(log: DailyStudyLog) = withContext(Dispatchers.IO) {
        studyLogDao.insertOrUpdate(log)
    }

    suspend fun incrementSubjectQuestions(dateStr: String, subject: String, delta: Int) = withContext(Dispatchers.IO) {
        val current = studyLogDao.getLogForDateSync(dateStr) ?: DailyStudyLog(dateString = dateStr)
        val updated = when (subject) {
            "PHYSICS" -> current.copy(
                physicsQuestions = (current.physicsQuestions + delta).coerceAtLeast(0),
                questionsSolved = (current.questionsSolved + delta).coerceAtLeast(0),
                masteryPoints = current.masteryPoints + (delta * 2)
            )
            "CHEMISTRY" -> current.copy(
                chemistryQuestions = (current.chemistryQuestions + delta).coerceAtLeast(0),
                questionsSolved = (current.questionsSolved + delta).coerceAtLeast(0),
                masteryPoints = current.masteryPoints + (delta * 2)
            )
            "MATHEMATICS" -> current.copy(
                mathQuestions = (current.mathQuestions + delta).coerceAtLeast(0),
                questionsSolved = (current.questionsSolved + delta).coerceAtLeast(0),
                masteryPoints = current.masteryPoints + (delta * 2)
            )
            else -> current
        }
        studyLogDao.insertOrUpdate(updated)
    }

    suspend fun updateTargetHours(dateStr: String, target: Float) = withContext(Dispatchers.IO) {
        val current = studyLogDao.getLogForDateSync(dateStr) ?: DailyStudyLog(dateString = dateStr)
        studyLogDao.insertOrUpdate(current.copy(targetHours = target))
    }

    suspend fun redeemRewardPass(dateStr: String, passName: String, durationMinutes: Int, pointsCost: Int): Boolean = withContext(Dispatchers.IO) {
        val current = studyLogDao.getLogForDateSync(dateStr) ?: DailyStudyLog(dateString = dateStr)
        if (current.masteryPoints < pointsCost) return@withContext false
        val expiry = System.currentTimeMillis() + (durationMinutes * 60 * 1000L)
        val updated = current.copy(
            masteryPoints = current.masteryPoints - pointsCost,
            activePassName = passName,
            activePassRemainingMinutes = durationMinutes,
            activePassExpiryEpoch = expiry
        )
        studyLogDao.insertOrUpdate(updated)
        true
    }

    suspend fun dismissLockdown(dateStr: String) = withContext(Dispatchers.IO) {
        val current = studyLogDao.getLogForDateSync(dateStr) ?: DailyStudyLog(dateString = dateStr)
        studyLogDao.insertOrUpdate(current.copy(isLockdownActive = false))
    }

    suspend fun resetAllToDefault() = withContext(Dispatchers.IO) {
        testDao.clearAll()
        chapterDao.clearAll()
        testDao.insertTests(PreloadedData.defaultTests)
        chapterDao.insertChapters(PreloadedData.defaultChapters)
    }

    /**
     * Automatically generates daily goals considering all 3 subjects (PCM)
     * based on the upcoming test and chapter completion status.
     * Special rule: 1 day before test triggers "Previous Part TEST" revision protocol.
     */
    suspend fun generateDailyGoalsIfEmpty(date: LocalDate, forceRegenerate: Boolean = false) = withContext(Dispatchers.IO) {
        ensureInitialized()

        val dateStr = date.toString()
        val existing = dailyGoalDao.getGoalsForDateSync(dateStr)
        if (existing.isNotEmpty() && !forceRegenerate) {
            return@withContext
        }

        if (forceRegenerate) {
            dailyGoalDao.clearAutoGeneratedForDate(dateStr)
        }

        val upcomingTest = testDao.getNextUpcomingTestSync() ?: return@withContext
        val testDate = try {
            // e.g. "04-Oct-2026"
            val parts = upcomingTest.testDate.split("-")
            val monthMap = mapOf(
                "Jan" to 1, "Feb" to 2, "Mar" to 3, "Apr" to 4, "May" to 5, "Jun" to 6,
                "Jul" to 7, "Aug" to 8, "Sep" to 9, "Oct" to 10, "Nov" to 11, "Dec" to 12
            )
            val d = parts[0].toInt()
            val m = monthMap[parts[1]] ?: 10
            val y = parts[2].toInt()
            LocalDate.of(y, m, d)
        } catch (_: Exception) {
            date.plusDays(7)
        }

        val daysUntilTest = ChronoUnit.DAYS.between(date, testDate)
        val isOneDayBeforeTest = (daysUntilTest == 1L)

        val newGoals = mutableListOf<DailyGoalItem>()

        if (isOneDayBeforeTest) {
            // EVE OF TEST: Previous Part TEST revision protocol!
            val prevTestNumber = (upcomingTest.testNumber - 1).coerceAtLeast(1)
            val testContext = if (upcomingTest.testNumber == 1) "Diagnostic & Speed Mock" else "Part Test 1 to $prevTestNumber"

            newGoals.add(
                DailyGoalItem(
                    dateString = dateStr,
                    subject = "PHYSICS",
                    subSubject = "PHYSICS",
                    chapterId = -1,
                    chapterName = "${upcomingTest.testName} Eve",
                    taskType = "PREV_TEST_REV",
                    title = "[Physics] Previous Test Analysis & Formula Sheet",
                    description = "Revise Previous $testContext test mistakes, error logbook, and Vernier/Screw Gauge & Mechanics formulas.",
                    targetCount = 1,
                    isCompleted = false,
                    isAutoGenerated = true,
                    isTestEveRevision = true
                )
            )

            newGoals.add(
                DailyGoalItem(
                    dateString = dateStr,
                    subject = "CHEMISTRY",
                    subSubject = "P_CHEM",
                    chapterId = -2,
                    chapterName = "${upcomingTest.testName} Eve",
                    taskType = "PREV_TEST_REV",
                    title = "[Chemistry] Previous Part Test Mock & 1-Page Summary",
                    description = "Rapid revision of 1-page summaries for Physical/Inorganic chapters + solve previous Part Test tricky questions.",
                    targetCount = 1,
                    isCompleted = false,
                    isAutoGenerated = true,
                    isTestEveRevision = true
                )
            )

            newGoals.add(
                DailyGoalItem(
                    dateString = dateStr,
                    subject = "MATHEMATICS",
                    subSubject = "MATH",
                    chapterId = -3,
                    chapterName = "${upcomingTest.testName} Eve",
                    taskType = "PREV_TEST_REV",
                    title = "[Maths] Previous Part Test Paper & Speed Drill",
                    description = "Re-attempt wrong questions from previous Part Tests under 1 hour timer. Review 3D Geometry and Vector identities.",
                    targetCount = 1,
                    isCompleted = false,
                    isAutoGenerated = true,
                    isTestEveRevision = true
                )
            )
        } else {
            // Standard Prep Day: Consider 3 subjects (Physics, Chemistry, Maths)
            val currentTestChapters = chapterDao.getChaptersForTestSync(upcomingTest.id)
            val allTargetChapters = if (currentTestChapters.isNotEmpty()) {
                currentTestChapters
            } else {
                chapterDao.getCumulativeChaptersUpToTestSync(upcomingTest.id)
            }

            // Group by Subject
            val physicsChapters = allTargetChapters.filter { it.subject == "PHYSICS" }
            val chemChapters = allTargetChapters.filter { it.subject == "CHEMISTRY" }
            val mathChapters = allTargetChapters.filter { it.subject == "MATHEMATICS" }

            // Pick 1 Physics chapter
            val selectedPhysics = selectBestChapterForDailyGoal(physicsChapters)
            selectedPhysics?.let { ch ->
                val nextTask = determineNextPillarTask(ch)
                newGoals.add(
                    DailyGoalItem(
                        dateString = dateStr,
                        subject = "PHYSICS",
                        subSubject = ch.subSubject,
                        chapterId = ch.id,
                        chapterName = ch.chapterName,
                        taskType = nextTask.taskType,
                        title = "[Physics] ${ch.chapterName}",
                        description = nextTask.description,
                        targetCount = nextTask.targetCount,
                        isCompleted = false,
                        isAutoGenerated = true,
                        isTestEveRevision = false
                    )
                )
            }

            // Pick 1 Chemistry chapter (rotates through pending P-Chem, I-Chem, O-Chem)
            val selectedChem = selectBestChapterForDailyGoal(chemChapters)
            selectedChem?.let { ch ->
                val nextTask = determineNextPillarTask(ch)
                val subBadge = when (ch.subSubject) {
                    "P_CHEM" -> "Physical"
                    "I_CHEM" -> "Inorganic"
                    "O_CHEM" -> "Organic"
                    else -> "Chemistry"
                }
                newGoals.add(
                    DailyGoalItem(
                        dateString = dateStr,
                        subject = "CHEMISTRY",
                        subSubject = ch.subSubject,
                        chapterId = ch.id,
                        chapterName = ch.chapterName,
                        taskType = nextTask.taskType,
                        title = "[$subBadge Chem] ${ch.chapterName}",
                        description = nextTask.description,
                        targetCount = nextTask.targetCount,
                        isCompleted = false,
                        isAutoGenerated = true,
                        isTestEveRevision = false
                    )
                )
            }

            // Pick 1 Math chapter
            val selectedMath = selectBestChapterForDailyGoal(mathChapters)
            selectedMath?.let { ch ->
                val nextTask = determineNextPillarTask(ch)
                newGoals.add(
                    DailyGoalItem(
                        dateString = dateStr,
                        subject = "MATHEMATICS",
                        subSubject = ch.subSubject,
                        chapterId = ch.id,
                        chapterName = ch.chapterName,
                        taskType = nextTask.taskType,
                        title = "[Maths] ${ch.chapterName}",
                        description = nextTask.description,
                        targetCount = nextTask.targetCount,
                        isCompleted = false,
                        isAutoGenerated = true,
                        isTestEveRevision = false
                    )
                )
            }
        }

        if (newGoals.isNotEmpty()) {
            dailyGoalDao.insertGoals(newGoals)
        }
    }

    private fun selectBestChapterForDailyGoal(chapters: List<ChapterItem>): ChapterItem? {
        if (chapters.isEmpty()) return null
        // Find chapter with lowest completion percentage, or oldest lastStudiedTimestamp
        return chapters.minByOrNull { it.completionPercent }
    }

    private data class PillarTaskRecommendation(
        val taskType: String,
        val description: String,
        val targetCount: Int
    )

    private fun determineNextPillarTask(chapter: ChapterItem): PillarTaskRecommendation {
        return when {
            !chapter.theoryCompleted -> PillarTaskRecommendation(
                taskType = "THEORY",
                description = "Deep Theory & Concept Review: Revise lecture notes and key textbook derivations.",
                targetCount = 1
            )
            !chapter.shortNotesCompleted -> PillarTaskRecommendation(
                taskType = "SHORT_NOTES",
                description = "Prepare/Revise 1-Page Summary conclusion sheet with all core formulas and traps.",
                targetCount = 1
            )
            !chapter.mathongoQuestionsCompleted -> PillarTaskRecommendation(
                taskType = "MATHONGO_CBQ",
                description = "Solve MathonGo Concept Builder Questions (CBQ) to master question application.",
                targetCount = 20
            )
            !chapter.moduleEx2Completed -> PillarTaskRecommendation(
                taskType = "MODULE_EX2",
                description = "Module Ex-2: Practice advanced multi-correct, comprehension, and numerical questions.",
                targetCount = 25
            )
            !chapter.eklavyaCompleted -> PillarTaskRecommendation(
                taskType = "EKLAVYA",
                description = "Eklavya Batch Problems: Tackle highest difficulty JEE Advanced problem set.",
                targetCount = 15
            )
            else -> PillarTaskRecommendation(
                taskType = "PREV_TEST_REV",
                description = "Spaced Revision: Speed solve 15 mixed questions and verify 1-page summary.",
                targetCount = 15
            )
        }
    }
}
