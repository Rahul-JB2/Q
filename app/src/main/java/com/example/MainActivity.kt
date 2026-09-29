package com.example

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.data.local.AppDatabase
import com.example.data.repository.JeeRepository
import com.example.service.AppBlockerService
import com.example.ui.chapters.ChapterListScreen
import com.example.ui.chapters.ChapterViewModel
import com.example.ui.dashboard.DashboardViewModel
import com.example.ui.gemini.GeminiHubScreen
import com.example.ui.gemini.GeminiHubViewModel
import com.example.ui.home.HomeScreen
import com.example.ui.navigation.Screen
import com.example.ui.planner.PlannerViewModel
import com.example.ui.planner.TestScheduleScreen
import com.example.ui.profile.ProfileScreen
import com.example.ui.profile.ProfileViewModel
import com.example.ui.score.ScoreScreen
import com.example.ui.score.ScoreViewModel
import com.example.ui.store.RewardStoreScreen
import com.example.ui.store.RewardStoreViewModel
import com.example.ui.timer.FocusTimerScreen
import com.example.ui.timer.FocusTimerViewModel
import com.example.ui.vault.FormulaVaultScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.PortalBackground
import com.example.ui.theme.PortalBorder
import com.example.ui.theme.PortalSurface
import com.example.ui.theme.Super50Gold
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainActivity : ComponentActivity() {

    private val _blockedAppEvent = MutableStateFlow<String?>(null)
    val blockedAppEvent: StateFlow<String?> = _blockedAppEvent.asStateFlow()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        handleBlockIntent(intent)

        val database = AppDatabase.getDatabase(applicationContext, lifecycleScope)
        val repository = JeeRepository(database)

        setContent {
            MyApplicationTheme {
                MainAppContent(
                    repository = repository,
                    blockedAppEvent = blockedAppEvent,
                    onClearBlockedAppEvent = { _blockedAppEvent.value = null }
                )
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleBlockIntent(intent)
    }

    private fun handleBlockIntent(intent: Intent?) {
        val blockedApp = intent?.getStringExtra(AppBlockerService.EXTRA_BLOCKED_APP)
        if (!blockedApp.isNullOrBlank()) {
            _blockedAppEvent.value = blockedApp
            intent.removeExtra(AppBlockerService.EXTRA_BLOCKED_APP)
        }
    }
}

@Composable
fun MainAppContent(
    repository: JeeRepository,
    blockedAppEvent: StateFlow<String?>? = null,
    onClearBlockedAppEvent: () -> Unit = {}
) {
    val navController = rememberNavController()
    val blockedApp by (blockedAppEvent?.collectAsStateWithLifecycle() ?: remember { MutableStateFlow(null) }.collectAsStateWithLifecycle())

    val dashboardViewModel: DashboardViewModel = viewModel(
        factory = remember { DashboardViewModel.Factory(repository) }
    )
    val plannerViewModel: PlannerViewModel = viewModel(
        factory = remember { PlannerViewModel.Factory(repository) }
    )
    val geminiHubViewModel: GeminiHubViewModel = viewModel(
        factory = remember { GeminiHubViewModel.Factory(repository) }
    )
    val scoreViewModel: ScoreViewModel = viewModel(
        factory = remember { ScoreViewModel.Factory(repository) }
    )
    val profileViewModel: ProfileViewModel = viewModel(
        factory = remember { ProfileViewModel.Factory(repository) }
    )
    val chapterViewModel: ChapterViewModel = viewModel(
        factory = remember { ChapterViewModel.Factory(repository) }
    )
    val focusTimerViewModel: FocusTimerViewModel = viewModel(
        factory = remember { FocusTimerViewModel.Factory(repository) }
    )
    val rewardStoreViewModel: RewardStoreViewModel = viewModel(
        factory = remember { RewardStoreViewModel.Factory(repository) }
    )

    // Strict 5 Primary Mobile Tabs
    val navItems = listOf(
        Screen.Home,
        Screen.Tests,
        Screen.Gemini,
        Screen.Score,
        Screen.Profile
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar(
                containerColor = PortalSurface,
                contentColor = MaterialTheme.colorScheme.onSurface,
                tonalElevation = 8.dp
            ) {
                navItems.forEach { screen ->
                    val isSelected = currentRoute == screen.route
                    NavigationBarItem(
                        selected = isSelected,
                        onClick = {
                            if (currentRoute != screen.route) {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = screen.icon,
                                contentDescription = screen.title
                            )
                        },
                        label = {
                            Text(
                                text = screen.title,
                                fontSize = 11.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Super50Gold,
                            selectedTextColor = Super50Gold,
                            indicatorColor = Super50Gold.copy(alpha = 0.15f),
                            unselectedIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                            unselectedTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        ),
                        modifier = Modifier.testTag("nav_tab_${screen.route}")
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            // Tab 1: Home (Daily PCM & Discipline Lockdown)
            composable(Screen.Home.route) {
                HomeScreen(
                    viewModel = dashboardViewModel,
                    onNavigateToTests = {
                        navController.navigate(Screen.Tests.route) {
                            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                            launchSingleTop = true
                        }
                    },
                    onNavigateToGemini = { prompt ->
                        if (!prompt.isNullOrBlank()) {
                            geminiHubViewModel.askQuestion(prompt)
                        }
                        navController.navigate(Screen.Gemini.route) {
                            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                            launchSingleTop = true
                        }
                    },
                    onNavigateToProfile = {
                        navController.navigate(Screen.Profile.route) {
                            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                            launchSingleTop = true
                        }
                    },
                    onNavigateToRewardStore = {
                        navController.navigate(Screen.RewardStore.route)
                    },
                    onNavigateToTimer = {
                        navController.navigate(Screen.FocusTimer.route)
                    },
                    onNavigateToVault = {
                        navController.navigate(Screen.FormulaVault.route)
                    },
                    onNavigateToChapters = {
                        navController.navigate(Screen.Chapters.route)
                    }
                )
            }

            // Tab 2: Tests (Sunday Mock Test Planner & Blitz Countdown)
            composable(Screen.Tests.route) {
                TestScheduleScreen(
                    viewModel = plannerViewModel
                )
            }

            // Tab 3: Gemini (AI Mentor & Forensic Mock Deep-Dive)
            composable(Screen.Gemini.route) {
                GeminiHubScreen(
                    viewModel = geminiHubViewModel
                )
            }

            // Tab 4: Score (Mock Scores, Mistake Notebook & 7-Day AIR Trends)
            composable(Screen.Score.route) {
                ScoreScreen(
                    viewModel = scoreViewModel
                )
            }

            // Tab 5: Profile (Student Profile, Rewards Store & Android App Blocker)
            composable(Screen.Profile.route) {
                ProfileScreen(
                    viewModel = profileViewModel,
                    onNavigateToChapters = { navController.navigate(Screen.Chapters.route) },
                    onNavigateToVault = { navController.navigate(Screen.FormulaVault.route) },
                    onNavigateToTimer = { navController.navigate(Screen.FocusTimer.route) },
                    onNavigateToRewardStore = { navController.navigate(Screen.RewardStore.route) }
                )
            }

            // Reward Store Screen
            composable(Screen.RewardStore.route) {
                RewardStoreScreen(
                    viewModel = rewardStoreViewModel,
                    onNavigateBack = { navController.popBackStack() }
                )
            }

            // Deep-Dive Tool Sub-screens
            composable(Screen.Chapters.route) {
                ChapterListScreen(
                    viewModel = chapterViewModel
                )
            }

            composable(Screen.FormulaVault.route) {
                FormulaVaultScreen(
                    viewModel = chapterViewModel
                )
            }

            composable(Screen.FocusTimer.route) {
                FocusTimerScreen(
                    viewModel = focusTimerViewModel
                )
            }
        }

        // Global Study Guard Block Alert Dialog
        blockedApp?.let { appName ->
            AlertDialog(
                onDismissRequest = { onClearBlockedAppEvent() },
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = null,
                            tint = Color(0xFFEF4444),
                            modifier = Modifier.size(22.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "⛔ STUDY GUARD ACTIVE",
                            fontWeight = FontWeight.Black,
                            fontSize = 15.sp,
                            color = Color(0xFFF87171)
                        )
                    }
                },
                text = {
                    Column {
                        Text(
                            text = "$appName is restricted during your JEE Advanced study session.",
                            fontSize = 13.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Solve questions and complete daily targets to earn JEE Mastery Points, or redeem an authorized temporary pass in the Reward Store.",
                            fontSize = 11.sp,
                            color = Color(0xFFCBD5E1)
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            onClearBlockedAppEvent()
                            navController.navigate(Screen.RewardStore.route)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Super50Gold)
                    ) {
                        Text("Open Reward Store", color = Color.Black, fontWeight = FontWeight.Bold)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { onClearBlockedAppEvent() }) {
                        Text("Back to Study", color = Color(0xFF94A3B8))
                    }
                },
                containerColor = Color(0xFF1E1B4B),
                shape = RoundedCornerShape(16.dp)
            )
        }
    }
}
