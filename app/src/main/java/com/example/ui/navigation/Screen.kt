package com.example.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.Functions
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Timer
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    // 5 Primary Mobile Tabs
    object Home : Screen("home", "Home", Icons.Default.Home)
    object Tests : Screen("tests", "Tests", Icons.Default.Assignment)
    object Gemini : Screen("gemini", "Gemini", Icons.Default.AutoAwesome)
    object Score : Screen("score", "Score", Icons.Default.BarChart)
    object Profile : Screen("profile", "Profile", Icons.Default.Person)

    // Deep-dive & Tool Sub-screens
    object RewardStore : Screen("reward_store", "Reward Store", Icons.Default.CardGiftcard)
    object Chapters : Screen("chapters", "6-Pillars", Icons.Default.MenuBook)
    object FormulaVault : Screen("vault", "Formula Vault", Icons.Default.Functions)
    object FocusTimer : Screen("timer", "Focus Room", Icons.Default.Timer)
}

