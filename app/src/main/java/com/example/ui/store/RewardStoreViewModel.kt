package com.example.ui.store

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.model.DailyStudyLog
import com.example.data.repository.JeeRepository
import com.example.service.ActivePassInfo
import com.example.service.StudyGuardManager
import com.example.ui.profile.RewardPassItem
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.time.LocalDate

data class RewardStoreUiState(
    val passes: List<RewardPassItem> = listOf(
        RewardPassItem(
            id = "pocketfm",
            title = "Pocket FM Audio Story Pass",
            icon = "🎧",
            durationMinutes = 25,
            pointsCost = 100,
            description = "Enjoy 25 mins of audio storytelling uninterrupted by Study Guard.",
            packageName = "com.pocketfm.android"
        ),
        RewardPassItem(
            id = "youtube",
            title = "YouTube Educational & Break Pass",
            icon = "📺",
            durationMinutes = 20,
            pointsCost = 120,
            description = "Watch high-yield video lectures, derivations, or take a short mental break.",
            packageName = "com.google.android.youtube"
        ),
        RewardPassItem(
            id = "gaming",
            title = "Mobile Gaming Pass",
            icon = "🎮",
            durationMinutes = 20,
            pointsCost = 150,
            description = "Unlock 20 mins of casual gaming allowance (Free Fire, BGMI, etc.).",
            packageName = "com.dts.freefireth"
        ),
        RewardPassItem(
            id = "chrome",
            title = "Chrome Academic & Research Pass",
            icon = "🌐",
            durationMinutes = 20,
            pointsCost = 60,
            description = "Unrestricted academic browsing for JEE advanced research and Wikipedia.",
            packageName = "com.android.chrome"
        ),
        RewardPassItem(
            id = "gemini",
            title = "Gemini Forensic Mock Deep-Dive Pass",
            icon = "🤖",
            durationMinutes = 60,
            pointsCost = 80,
            description = "Unlocks detailed AI forensic post-mortem breakdown of mock test traps.",
            packageName = "com.example"
        )
    ),
    val message: String? = null
)

class RewardStoreViewModel(private val repository: JeeRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(RewardStoreUiState())
    val uiState: StateFlow<RewardStoreUiState> = _uiState.asStateFlow()

    val studyLog: StateFlow<DailyStudyLog> = repository.getStudyLogForDate(LocalDate.now().toString())
        .map { it ?: DailyStudyLog(dateString = LocalDate.now().toString()) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = DailyStudyLog(dateString = LocalDate.now().toString())
        )

    val activePass: StateFlow<ActivePassInfo?> = StudyGuardManager.activePassFlow

    private var countdownJob: Job? = null

    fun activatePass(context: Context, pass: RewardPassItem, onResult: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            val currentLog = studyLog.value
            if (currentLog.masteryPoints < pass.pointsCost) {
                onResult(false, "Insufficient JEE Mastery Points! You need ${pass.pointsCost} Pts.")
                return@launch
            }

            // Deduct points and save to repository
            val success = repository.redeemRewardPass(
                dateStr = LocalDate.now().toString(),
                passName = "${pass.icon} ${pass.title}",
                durationMinutes = pass.durationMinutes,
                pointsCost = pass.pointsCost
            )

            if (success) {
                // Activate in StudyGuardManager
                StudyGuardManager.activatePass(
                    context = context,
                    passId = pass.id,
                    passName = "${pass.icon} ${pass.title}",
                    packageName = pass.packageName,
                    durationMinutes = pass.durationMinutes
                )

                // Start countdown updater loop
                startCountdownLoop(context)

                onResult(true, "🎉 Pass Activated! You have ${pass.durationMinutes} minutes to enjoy ${pass.title}.")
            } else {
                onResult(false, "Could not activate pass. Please try again.")
            }
        }
    }

    fun endPassEarly(context: Context) {
        viewModelScope.launch {
            StudyGuardManager.endPass(context)
            countdownJob?.cancel()
            val current = studyLog.value
            repository.saveStudyLog(
                current.copy(
                    activePassName = null,
                    activePassRemainingMinutes = 0,
                    activePassExpiryEpoch = 0L
                )
            )
        }
    }

    fun startCountdownLoop(context: Context) {
        countdownJob?.cancel()
        countdownJob = viewModelScope.launch {
            while (isActive) {
                StudyGuardManager.updateFlow(context)
                if (StudyGuardManager.activePassFlow.value == null) {
                    break
                }
                delay(1000L)
            }
        }
    }

    class Factory(private val repository: JeeRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return RewardStoreViewModel(repository) as T
        }
    }
}
