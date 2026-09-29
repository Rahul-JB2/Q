package com.example.ui.score

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.model.DailyStudyLog
import com.example.data.model.TestItem
import com.example.data.repository.JeeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class MistakeItem(
    val id: Int,
    val testName: String,
    val subject: String,
    val category: String, // "Silly Error", "Formula Forgot", "Conceptual Gap", "Time Shortage"
    val questionNote: String,
    val correctiveAction: String
)

data class ScoreUiState(
    val selectedSubTab: Int = 0, // 0: Mock Scores & Mistake Notebook, 1: 7-Day Trends & Strategy Coach
    val mistakes: List<MistakeItem> = listOf(
        MistakeItem(1, "Part Test-1", "PHYSICS", "Silly Error", "Calculation mistake in cross product vector magnitude", "Write down full determinant instead of mental calculation"),
        MistakeItem(2, "Part Test-1", "CHEMISTRY", "Formula Forgot", "Forgot van 't Hoff factor i for weak electrolyte dissociation", "Add i = 1 + (n-1)alpha to 1-Page Summary"),
        MistakeItem(3, "Part Test-1", "MATHEMATICS", "Time Shortage", "Spent 18 minutes on a single 3D geometry plane equation", "Strictly flag and skip question after 3 minutes")
    )
)

class ScoreViewModel(private val repository: JeeRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(ScoreUiState())
    val uiState: StateFlow<ScoreUiState> = _uiState.asStateFlow()

    val tests: StateFlow<List<TestItem>> = repository.allTests
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val studyLogs: StateFlow<List<DailyStudyLog>> = repository.getAllStudyLogs()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun selectSubTab(tab: Int) {
        _uiState.value = _uiState.value.copy(selectedSubTab = tab)
    }

    fun addMistakeNote(testName: String, subject: String, category: String, note: String, action: String) {
        val newId = (_uiState.value.mistakes.maxOfOrNull { it.id } ?: 0) + 1
        val item = MistakeItem(newId, testName, subject, category, note, action)
        _uiState.value = _uiState.value.copy(mistakes = listOf(item) + _uiState.value.mistakes)
    }

    fun logTestScore(test: TestItem, p: Int, c: Int, m: Int, rank: String, remarks: String) {
        viewModelScope.launch {
            repository.updateTestScores(test, p, c, m, rank, remarks)
        }
    }

    class Factory(private val repository: JeeRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ScoreViewModel(repository) as T
        }
    }
}
