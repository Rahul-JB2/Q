package com.example.ui.chapters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.model.ChapterItem
import com.example.data.repository.JeeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class ChapterUiState(
    val chapters: List<ChapterItem> = emptyList(),
    val selectedSubject: String = "ALL", // "ALL", "PHYSICS", "CHEMISTRY", "MATHEMATICS"
    val selectedTestFilter: Int = 0, // 0 means all, 1..8 for PT 1-8
    val searchQuery: String = "",
    val editingChapter: ChapterItem? = null
)

class ChapterViewModel(private val repository: JeeRepository) : ViewModel() {

    val selectedSubject = MutableStateFlow("ALL")
    val selectedTestFilter = MutableStateFlow(0)
    val searchQuery = MutableStateFlow("")
    val editingChapter = MutableStateFlow<ChapterItem?>(null)

    val uiState: StateFlow<ChapterUiState> = combine(
        repository.allChapters,
        selectedSubject,
        selectedTestFilter,
        searchQuery,
        editingChapter
    ) { allChapters, subject, testFilter, query, editing ->
        val filtered = allChapters.filter { ch ->
            val matchSubject = if (subject == "ALL") true else ch.subject.equals(subject, ignoreCase = true)
            val matchTest = if (testFilter == 0) true else ch.partTestId == testFilter
            val matchQuery = if (query.isBlank()) true else {
                ch.chapterName.contains(query, ignoreCase = true) ||
                ch.subSubject.contains(query, ignoreCase = true)
            }
            matchSubject && matchTest && matchQuery
        }

        ChapterUiState(
            chapters = filtered,
            selectedSubject = subject,
            selectedTestFilter = testFilter,
            searchQuery = query,
            editingChapter = editing
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ChapterUiState()
    )

    fun selectSubject(subject: String) {
        selectedSubject.value = subject
    }

    fun selectTestFilter(testId: Int) {
        selectedTestFilter.value = testId
    }

    fun updateSearchQuery(query: String) {
        searchQuery.value = query
    }

    fun setEditingChapter(chapter: ChapterItem?) {
        editingChapter.value = chapter
    }

    fun toggleWeakTopic(chapter: ChapterItem) {
        viewModelScope.launch {
            repository.updateChapter(chapter.copy(isWeakTopic = !chapter.isWeakTopic))
        }
    }

    fun togglePillar(chapter: ChapterItem, pillar: String) {
        val updated = when (pillar) {
            "THEORY" -> chapter.copy(theoryCompleted = !chapter.theoryCompleted)
            "SHORT_NOTES" -> chapter.copy(shortNotesCompleted = !chapter.shortNotesCompleted)
            "MATHONGO_CBQ" -> chapter.copy(mathongoQuestionsCompleted = !chapter.mathongoQuestionsCompleted)
            "MODULE_EX2" -> chapter.copy(moduleEx2Completed = !chapter.moduleEx2Completed)
            "EKLAVYA" -> chapter.copy(eklavyaCompleted = !chapter.eklavyaCompleted)
            "PREV_TEST_REV" -> chapter.copy(previousTestRevisionCompleted = !chapter.previousTestRevisionCompleted)
            else -> chapter
        }.copy(lastStudiedTimestamp = System.currentTimeMillis())

        viewModelScope.launch {
            repository.updateChapter(updated)
        }
    }

    fun saveChapterDetails(updated: ChapterItem) {
        viewModelScope.launch {
            repository.updateChapter(updated.copy(lastStudiedTimestamp = System.currentTimeMillis()))
            editingChapter.value = null
        }
    }

    class Factory(private val repository: JeeRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ChapterViewModel(repository) as T
        }
    }
}
