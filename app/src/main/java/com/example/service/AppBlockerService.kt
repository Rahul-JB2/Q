package com.example.service

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.content.Intent
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import com.example.MainActivity

/**
 * AppBlockerService monitors foreground package activity using the Android AccessibilityService API.
 * When a forbidden or blacklisted app (e.g. YouTube, Chrome, Pocket FM, games) is launched during study
 * hours without an active reward pass, this service redirects the user back to Home and triggers
 * the Study Guard overlay in Super50 Tracker.
 */
class AppBlockerService : AccessibilityService() {

    companion object {
        const val TAG = "AppBlockerService"
        const val EXTRA_BLOCKED_APP = "extra_blocked_app"
        const val EXTRA_PACKAGE_NAME = "extra_package_name"
    }

    override fun onServiceConnected() {
        super.onServiceConnected()
        val info = AccessibilityServiceInfo().apply {
            eventTypes = AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED
            feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC
            flags = AccessibilityServiceInfo.FLAG_INCLUDE_NOT_IMPORTANT_VIEWS
            notificationTimeout = 100
        }
        serviceInfo = info
        Log.d(TAG, "Study Guard AppBlockerService connected and active")
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event == null || event.eventType != AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) return

        val packageName = event.packageName?.toString() ?: return

        // Ignore our own application
        if (packageName == applicationContext.packageName) return

        // Check if package is forbidden / blacklisted (e.g., YouTube, Chrome, Pocket FM, Games)
        if (StudyGuardManager.isBlacklisted(packageName)) {
            val isAllowed = StudyGuardManager.isAppAllowed(applicationContext, packageName)
            if (!isAllowed) {
                val appName = StudyGuardManager.getAppName(packageName)
                Log.w(TAG, "Enforcing block on forbidden app: $appName ($packageName)")

                // 1. Redirect user back to home immediately
                performGlobalAction(GLOBAL_ACTION_HOME)

                // 2. Launch Super50 Tracker with the block alert overlay
                val blockIntent = Intent(applicationContext, MainActivity::class.java).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
                    putExtra(EXTRA_BLOCKED_APP, appName)
                    putExtra(EXTRA_PACKAGE_NAME, packageName)
                }
                applicationContext.startActivity(blockIntent)
            }
        }
    }

    override fun onInterrupt() {
        Log.d(TAG, "AppBlockerService interrupted")
    }
}
