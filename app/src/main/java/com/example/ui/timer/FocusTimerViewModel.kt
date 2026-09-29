package com.example.ui.timer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.model.DailyStudyLog
import com.example.data.repository.JeeRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate

data class FocusTimerUiState(
    val selectedSubject: String = "PHYSICS", // "PHYSICS", "CHEMISTRY", "MATHEMATICS"
    val totalDurationSeconds: Int = 25 * 60,
    val remainingSeconds: Int = 25 * 60,
    val isRunning: Boolean = false,
    val isStopwatchMode: Boolean = false,
    val completedSessions: Int = 0,
    val todayStudyLog: DailyStudyLog? = null
)

class FocusTimerViewModel(private val repository: JeeRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(FocusTimerUiState())
    val uiState: StateFlow<FocusTimerUiState> = _uiState.asStateFlow()

    private var timerJob: Job? = null

    val studyLog: StateFlow<DailyStudyLog?> = repository.getStudyLogForDate(LocalDate.now().toString())
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    fun selectSubject(subject: String) {
        _uiState.value = _uiState.value.copy(selectedSubject = subject)
    }

    fun setPreset(minutes: Int) {
        pause()
        val secs = minutes * 60
        _uiState.value = _uiState.value.copy(
            totalDurationSeconds = secs,
            remainingSeconds = secs,
            isStopwatchMode = false
        )
    }

    fun setStopwatchMode() {
        pause()
        _uiState.value = _uiState.value.copy(
            totalDurationSeconds = 0,
            remainingSeconds = 0,
            isStopwatchMode = true
        )
    }

    fun start() {
        if (_uiState.value.isRunning) return
        _uiState.value = _uiState.value.copy(isRunning = true)
        timerJob = viewModelScope.launch {
            while (_uiState.value.isRunning) {
                delay(1000)
                val current = _uiState.value
                if (current.isStopwatchMode) {
                    _uiState.value = current.copy(remainingSeconds = current.remainingSeconds + 1)
                } else {
                    if (current.remainingSeconds > 0) {
                        _uiState.value = current.copy(remainingSeconds = current.remainingSeconds - 1)
                    } else {
                        // Finished timer
                        pause()
                        _uiState.value = _uiState.value.copy(
                            completedSessions = _uiState.value.completedSessions + 1
                        )
                        break
                    }
                }
            }
        }
    }

    fun pause() {
        timerJob?.cancel()
        timerJob = null
        _uiState.value = _uiState.value.copy(isRunning = false)
    }

    fun reset() {
        pause()
        val current = _uiState.value
        _uiState.value = current.copy(
            remainingSeconds = if (current.isStopwatchMode) 0 else current.totalDurationSeconds
        )
    }

    fun logCurrentSessionToDatabase(questionsSolvedInSession: Int = 0) {
        val current = _uiState.value
        val elapsedSeconds = if (current.isStopwatchMode) {
            current.remainingSeconds
        } else {
            (current.totalDurationSeconds - current.remainingSeconds).coerceAtLeast(0)
        }

        if (elapsedSeconds < 30) return // Less than 30s not logged

        val addedHours = elapsedSeconds / 3600f
        val todayStr = LocalDate.now().toString()

        viewModelScope.launch {
            val existing = studyLog.value ?: DailyStudyLog(dateString = todayStr)
            val updated = when (current.selectedSubject) {
                "PHYSICS" -> existing.copy(
                    physicsHours = existing.physicsHours + addedHours,
                    questionsSolved = existing.questionsSolved + questionsSolvedInSession
                )
                "CHEMISTRY" -> existing.copy(
                    chemistryHours = existing.chemistryHours + addedHours,
                    questionsSolved = existing.questionsSolved + questionsSolvedInSession
                )
                "MATHEMATICS" -> existing.copy(
                    mathHours = existing.mathHours + addedHours,
                    questionsSolved = existing.questionsSolved + questionsSolvedInSession
                )
                else -> existing
            }
            repository.saveStudyLog(updated)
            reset()
        }
    }

    class Factory(private val repository: JeeRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return FocusTimerViewModel(repository) as T
        }
    }
}
