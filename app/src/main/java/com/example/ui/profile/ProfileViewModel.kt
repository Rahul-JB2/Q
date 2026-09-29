package com.example.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.model.DailyStudyLog
import com.example.data.repository.JeeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate

data class RewardPassItem(
    val id: String,
    val title: String,
    val icon: String,
    val durationMinutes: Int,
    val pointsCost: Int,
    val description: String,
    val packageName: String
)

data class ProfileUiState(
    val studentName: String = "JEE Aspirant",
    val targetExam: String = "IIT JEE Advanced & Main 2027",
    val showBlockOverlay: Boolean = false,
    val blockedAppName: String = "YouTube",
    val availablePasses: List<RewardPassItem> = listOf(
        RewardPassItem("pocketfm", "Pocket FM Audio Story Pass", "🎧", 25, 100, "25-min audio story listening window", "com.pocketfm.android"),
        RewardPassItem("youtube", "YouTube Educational/Break Pass", "📺", 20, 120, "20-min YouTube educational break window", "com.google.android.youtube"),
        RewardPassItem("gaming", "Mobile Gaming Pass", "🎮", 20, 150, "20-min casual mobile gaming allowance", "com.dts.freefireth"),
        RewardPassItem("gemini", "Gemini Mock Deep-Dive Pass", "🤖", 60, 80, "Exhaustive AI forensic error breakdown", "com.example"),
        RewardPassItem("chrome", "Chrome Research / Web Pass", "🌐", 20, 60, "20-min academic web search window", "com.android.chrome")
    )
)

class ProfileViewModel(private val repository: JeeRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    val studyLog: StateFlow<DailyStudyLog> = repository.getStudyLogForDate(LocalDate.now().toString())
        .map { it ?: DailyStudyLog(dateString = LocalDate.now().toString()) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = DailyStudyLog(dateString = LocalDate.now().toString())
        )

    fun redeemPass(pass: RewardPassItem, onResult: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            val success = repository.redeemRewardPass(
                dateStr = LocalDate.now().toString(),
                passName = "${pass.icon} ${pass.title}",
                durationMinutes = pass.durationMinutes,
                pointsCost = pass.pointsCost
            )
            if (success) {
                onResult(true, "Pass unlocked! You have ${pass.durationMinutes} minutes to use ${pass.title}.")
            } else {
                onResult(false, "Not enough JEE Mastery Points! Solve more questions or complete daily goals.")
            }
        }
    }

    fun triggerBlockOverlay(appName: String) {
        _uiState.value = _uiState.value.copy(showBlockOverlay = true, blockedAppName = appName)
    }

    fun dismissBlockOverlay() {
        _uiState.value = _uiState.value.copy(showBlockOverlay = false)
    }

    class Factory(private val repository: JeeRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ProfileViewModel(repository) as T
        }
    }
}
