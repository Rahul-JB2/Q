package com.example.ui.planner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.model.TestItem
import com.example.data.repository.JeeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class PlannerUiState(
    val tests: List<TestItem> = emptyList(),
    val filterType: String = "ALL", // "ALL", "PART_TEST", "FULL_TEST"
    val selectedTest: TestItem? = null,
    val loggingScoreTest: TestItem? = null
)

class PlannerViewModel(private val repository: JeeRepository) : ViewModel() {

    val filterType = MutableStateFlow("ALL")
    val selectedTest = MutableStateFlow<TestItem?>(null)
    val loggingScoreTest = MutableStateFlow<TestItem?>(null)

    val uiState: StateFlow<PlannerUiState> = combine(
        repository.allTests,
        filterType,
        selectedTest,
        loggingScoreTest
    ) { allTests, filter, selected, logging ->
        val filtered = when (filter) {
            "PART_TEST" -> allTests.filter { it.testType == "PART_TEST" }
            "FULL_TEST" -> allTests.filter { it.testType == "FULL_TEST" }
            else -> allTests
        }

        PlannerUiState(
            tests = filtered,
            filterType = filter,
            selectedTest = selected,
            loggingScoreTest = logging
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = PlannerUiState()
    )

    fun setFilter(type: String) {
        filterType.value = type
    }

    fun setSelectedTest(test: TestItem?) {
        selectedTest.value = test
    }

    fun setLoggingScoreTest(test: TestItem?) {
        loggingScoreTest.value = test
    }

    fun toggleTestCompleted(test: TestItem) {
        viewModelScope.launch {
            repository.updateTest(test.copy(isCompleted = !test.isCompleted))
        }
    }

    fun togglePrevTestRevision(test: TestItem) {
        viewModelScope.launch {
            repository.updateTest(test.copy(previousTestRevisionDone = !test.previousTestRevisionDone))
        }
    }

    fun saveTestScores(
        test: TestItem,
        pScore: Int,
        cScore: Int,
        mScore: Int,
        rank: String,
        remarks: String
    ) {
        viewModelScope.launch {
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
            repository.updateTest(updated)
            loggingScoreTest.value = null
        }
    }

    fun resetToSuper50Default() {
        viewModelScope.launch {
            repository.resetAllToDefault()
        }
    }

    class Factory(private val repository: JeeRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return PlannerViewModel(repository) as T
        }
    }
}
