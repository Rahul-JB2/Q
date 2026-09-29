package com.example.service

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class ActivePassInfo(
    val passId: String,
    val passName: String,
    val packageName: String,
    val expiryTimestamp: Long,
    val remainingSeconds: Long
)

object StudyGuardManager {

    private const val PREFS_NAME = "study_guard_prefs"
    private const val KEY_PASS_ID = "pass_id"
    private const val KEY_PASS_NAME = "pass_name"
    private const val KEY_PACKAGE_NAME = "package_name"
    private const val KEY_EXPIRY_TIMESTAMP = "expiry_timestamp"

    val blacklistedPackages = mapOf(
        "com.google.android.youtube" to "YouTube",
        "com.android.chrome" to "Google Chrome",
        "com.pocketfm.android" to "Pocket FM",
        "com.instagram.android" to "Instagram",
        "com.facebook.katana" to "Facebook",
        "com.dts.freefireth" to "Free Fire",
        "com.pubg.imobile" to "BGMI"
    )

    private val _activePassFlow = MutableStateFlow<ActivePassInfo?>(null)
    val activePassFlow: StateFlow<ActivePassInfo?> = _activePassFlow.asStateFlow()

    private fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun isBlacklisted(packageName: String): Boolean {
        return blacklistedPackages.containsKey(packageName)
    }

    fun getAppName(packageName: String): String {
        return blacklistedPackages[packageName] ?: packageName
    }

    fun isAppAllowed(context: Context, packageName: String): Boolean {
        // If not blacklisted, allowed
        if (!isBlacklisted(packageName)) return true

        val prefs = getPrefs(context)
        val activePackage = prefs.getString(KEY_PACKAGE_NAME, null)
        val expiry = prefs.getLong(KEY_EXPIRY_TIMESTAMP, 0L)
        val now = System.currentTimeMillis()

        if (activePackage != null && activePackage == packageName && now < expiry) {
            return true
        }

        // Pass expired, clean up
        if (activePackage != null && now >= expiry) {
            endPass(context)
        }
        return false
    }

    fun activatePass(context: Context, passId: String, passName: String, packageName: String, durationMinutes: Int) {
        val prefs = getPrefs(context)
        val expiry = System.currentTimeMillis() + (durationMinutes * 60 * 1000L)

        prefs.edit()
            .putString(KEY_PASS_ID, passId)
            .putString(KEY_PASS_NAME, passName)
            .putString(KEY_PACKAGE_NAME, packageName)
            .putLong(KEY_EXPIRY_TIMESTAMP, expiry)
            .apply()

        updateFlow(context)
    }

    fun endPass(context: Context) {
        val prefs = getPrefs(context)
        prefs.edit().clear().apply()
        _activePassFlow.value = null
    }

    fun updateFlow(context: Context) {
        val prefs = getPrefs(context)
        val passId = prefs.getString(KEY_PASS_ID, null)
        val passName = prefs.getString(KEY_PASS_NAME, null)
        val packageName = prefs.getString(KEY_PACKAGE_NAME, null)
        val expiry = prefs.getLong(KEY_EXPIRY_TIMESTAMP, 0L)
        val now = System.currentTimeMillis()

        if (passId != null && passName != null && packageName != null && now < expiry) {
            val remainingSecs = (expiry - now) / 1000L
            _activePassFlow.value = ActivePassInfo(
                passId = passId,
                passName = passName,
                packageName = packageName,
                expiryTimestamp = expiry,
                remainingSeconds = remainingSecs
            )
        } else {
            if (passId != null && now >= expiry) {
                prefs.edit().clear().apply()
            }
            _activePassFlow.value = null
        }
    }
}
