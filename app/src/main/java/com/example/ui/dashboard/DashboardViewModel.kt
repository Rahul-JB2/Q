package com.example.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.model.ChapterItem
import com.example.data.model.DailyGoalItem
import com.example.data.model.DailyStudyLog
import com.example.data.model.TestItem
import com.example.data.repository.JeeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate

data class PillarStats(
    val theoryDone: Int = 0,
    val shortNotesDone: Int = 0,
    val mathongoDone: Int = 0,
    val moduleEx2Done: Int = 0,
    val eklavyaDone: Int = 0,
    val prevTestRevDone: Int = 0,
    val totalChapters: Int = 0,
    val physicsPercent: Int = 0,
    val chemPercent: Int = 0,
    val mathPercent: Int = 0,
    val overallPercent: Int = 0,
    val totalQuestionsSolved: Int = 0
)

data class DashboardUiState(
    val upcomingTest: TestItem? = null,
    val currentDate: LocalDate = LocalDate.now(),
    val todayGoals: List<DailyGoalItem> = emptyList(),
    val stats: PillarStats = PillarStats(),
    val completedTestsCount: Int = 0,
    val totalTestsCount: Int = 0
)

class DashboardViewModel(private val repository: JeeRepository) : ViewModel() {

    private val currentDate = MutableStateFlow(LocalDate.now())

    val uiState: StateFlow<DashboardUiState> = combine(
        repository.nextUpcomingTest,
        repository.allChapters,
        repository.allTests,
        currentDate
    ) { upcoming, chapters, tests, date ->
        val stats = calculateStats(chapters)
        val completedTests = tests.count { it.isCompleted }

        DashboardUiState(
            upcomingTest = upcoming,
            currentDate = date,
            stats = stats,
            completedTestsCount = completedTests,
            totalTestsCount = tests.size
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = DashboardUiState()
    )

    // Separate flow for today's goals
    val todayGoals: StateFlow<List<DailyGoalItem>> = repository.getGoalsForDate(LocalDate.now().toString())
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val todayStudyLog: StateFlow<DailyStudyLog> = repository.getStudyLogForDate(LocalDate.now().toString())
        .combine(currentDate) { log, _ ->
            log ?: DailyStudyLog(dateString = LocalDate.now().toString())
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = DailyStudyLog(dateString = LocalDate.now().toString())
        )

    init {
        viewModelScope.launch {
            repository.ensureInitialized()
            repository.generateDailyGoalsIfEmpty(LocalDate.now())
        }
    }

    fun toggleGoal(goal: DailyGoalItem) {
        viewModelScope.launch {
            repository.toggleGoalCompletion(goal)
        }
    }

    fun refreshDailyGoals() {
        viewModelScope.launch {
            repository.generateDailyGoalsIfEmpty(LocalDate.now(), forceRegenerate = true)
        }
    }

    fun incrementSubjectQuestions(subject: String, delta: Int) {
        viewModelScope.launch {
            repository.incrementSubjectQuestions(LocalDate.now().toString(), subject, delta)
        }
    }

    fun updateTargetHours(target: Float) {
        viewModelScope.launch {
            repository.updateTargetHours(LocalDate.now().toString(), target)
        }
    }

    fun dismissLockdown() {
        viewModelScope.launch {
            repository.dismissLockdown(LocalDate.now().toString())
        }
    }

    fun redeemPass(passName: String, durationMinutes: Int, pointsCost: Int) {
        viewModelScope.launch {
            repository.redeemRewardPass(LocalDate.now().toString(), passName, durationMinutes, pointsCost)
        }
    }

    private fun calculateStats(chapters: List<ChapterItem>): PillarStats {
        if (chapters.isEmpty()) return PillarStats()

        val total = chapters.size
        val theory = chapters.count { it.theoryCompleted }
        val notes = chapters.count { it.shortNotesCompleted }
        val mathongo = chapters.count { it.mathongoQuestionsCompleted }
        val ex2 = chapters.count { it.moduleEx2Completed }
        val eklavya = chapters.count { it.eklavyaCompleted }
        val prevRev = chapters.count { it.previousTestRevisionCompleted }

        val physicsChapters = chapters.filter { it.subject == "PHYSICS" }
        val chemChapters = chapters.filter { it.subject == "CHEMISTRY" }
        val mathChapters = chapters.filter { it.subject == "MATHEMATICS" }

        val physicsAvg = if (physicsChapters.isNotEmpty()) {
            physicsChapters.map { it.completionPercent }.average().toInt()
        } else 0

        val chemAvg = if (chemChapters.isNotEmpty()) {
            chemChapters.map { it.completionPercent }.average().toInt()
        } else 0

        val mathAvg = if (mathChapters.isNotEmpty()) {
            mathChapters.map { it.completionPercent }.average().toInt()
        } else 0

        val overallAvg = chapters.map { it.completionPercent }.average().toInt()

        val totalQuestions = chapters.sumOf { it.mathongoDone + it.moduleEx2Done + it.eklavyaDone }

        return PillarStats(
            theoryDone = theory,
            shortNotesDone = notes,
            mathongoDone = mathongo,
            moduleEx2Done = ex2,
            eklavyaDone = eklavya,
            prevTestRevDone = prevRev,
            totalChapters = total,
            physicsPercent = physicsAvg,
            chemPercent = chemAvg,
            mathPercent = mathAvg,
            overallPercent = overallAvg,
            totalQuestionsSolved = totalQuestions
        )
    }

    class Factory(private val repository: JeeRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return DashboardViewModel(repository) as T
        }
    }
}
