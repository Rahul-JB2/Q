package com.example.ui.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.Functions
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.components.CircularProgressBadge
import com.example.ui.components.CountdownHeader
import com.example.ui.components.GoalItemCard
import com.example.ui.components.LabeledProgressBar
import com.example.ui.theme.ChemistryColor
import com.example.ui.theme.EklavyaColor
import com.example.ui.theme.FlameOrange
import com.example.ui.theme.MathColor
import com.example.ui.theme.MathonGoColor
import com.example.ui.theme.ModuleEx2Color
import com.example.ui.theme.NotesColor
import com.example.ui.theme.PhysicsColor
import com.example.ui.theme.PortalBackground
import com.example.ui.theme.PortalBorder
import com.example.ui.theme.PortalSurface
import com.example.ui.theme.PortalSurfaceCard
import com.example.ui.theme.PrevTestColor
import com.example.ui.theme.Super50Gold
import com.example.ui.theme.TheoryColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel,
    onNavigateToGoals: () -> Unit,
    onNavigateToChapters: () -> Unit,
    onNavigateToPlanner: () -> Unit,
    onNavigateToVault: () -> Unit,
    onNavigateToTimer: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val todayGoals by viewModel.todayGoals.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = PortalBackground,
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "SUPER-50",
                                    fontWeight = FontWeight.Black,
                                    fontSize = 17.sp,
                                    letterSpacing = 0.5.sp,
                                    color = Color.White
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Box(
                                    modifier = Modifier
                                        .background(Super50Gold, RoundedCornerShape(4.dp))
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = "PORTAL",
                                        color = Color.Black,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.ExtraBold
                                    )
                                }
                            }
                            Text(
                                text = "BSEB JEE Main & Advanced 2025-27",
                                fontSize = 11.sp,
                                color = Color(0xFF94A3B8)
                            )
                        }

                        // Streak and Target Badges
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .background(FlameOrange.copy(alpha = 0.15f), RoundedCornerShape(20.dp))
                                    .border(1.dp, FlameOrange.copy(alpha = 0.4f), RoundedCornerShape(20.dp))
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.LocalFireDepartment,
                                        contentDescription = "Streak",
                                        tint = FlameOrange,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Spacer(modifier = Modifier.width(3.dp))
                                    Text(
                                        text = "14d Streak",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = FlameOrange
                                    )
                                }
                            }

                            IconButton(
                                onClick = { viewModel.refreshDailyGoals() },
                                modifier = Modifier
                                    .size(34.dp)
                                    .testTag("refresh_goals_button")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Refresh,
                                    contentDescription = "Refresh Daily PCM Goals",
                                    tint = Color.White
                                )
                            }
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = PortalSurface
                )
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { Spacer(modifier = Modifier.height(4.dp)) }

            // 1. Next Test Countdown Banner
            item {
                CountdownHeader(
                    upcomingTest = uiState.upcomingTest,
                    currentDate = uiState.currentDate,
                    onViewPlannerClicked = onNavigateToPlanner,
                    modifier = Modifier.testTag("countdown_header")
                )
            }

            // 2. Web Portal Quick Action Dock
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    PortalQuickAction(
                        icon = Icons.Default.Functions,
                        title = "Formula Vault",
                        subtitle = "1-Page Summaries",
                        accentColor = Color(0xFF38BDF8),
                        onClick = onNavigateToVault
                    )
                    PortalQuickAction(
                        icon = Icons.Default.Timer,
                        title = "Focus Timer",
                        subtitle = "PCM Pomodoro",
                        accentColor = Color(0xFF10B981),
                        onClick = onNavigateToTimer
                    )
                    PortalQuickAction(
                        icon = Icons.Default.MenuBook,
                        title = "6-Pillars Matrix",
                        subtitle = "Full Chapter Grid",
                        accentColor = Color(0xFF8B5CF6),
                        onClick = onNavigateToChapters
                    )
                }
            }

            // 3. Web KPI Metrics Grid (Cards)
            item {
                val stats = uiState.stats
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Total Mastery KPI
                    WebMetricCard(
                        title = "Overall Readiness",
                        value = "${stats.overallPercent}%",
                        subtitle = "${stats.totalChapters} Total Chapters",
                        accentColor = Color(0xFF6366F1),
                        modifier = Modifier.weight(1f)
                    )
                    // Solved Problems KPI
                    WebMetricCard(
                        title = "Target Problems",
                        value = "${stats.totalQuestionsSolved}",
                        subtitle = "CBQ + Ex-2 + Eklavya",
                        accentColor = Super50Gold,
                        modifier = Modifier.weight(1f)
                    )
                    // Test Series KPI
                    WebMetricCard(
                        title = "Tests Done",
                        value = "${uiState.completedTestsCount}/${uiState.totalTestsCount}",
                        subtitle = "Part & Full Mocks",
                        accentColor = Color(0xFF10B981),
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            // 4. Today's Automatic PCM Daily Mission
            item {
                val completedTodayCount = todayGoals.count { it.isCompleted }
                val totalTodayCount = todayGoals.size

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("pcm_daily_mission_card"),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = PortalSurfaceCard),
                    border = androidx.compose.foundation.BorderStroke(1.dp, PortalBorder),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(32.dp)
                                        .background(Color(0xFF6366F1).copy(alpha = 0.2f), CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.AutoAwesome,
                                        contentDescription = null,
                                        tint = Color(0xFF818CF8),
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text(
                                        text = "Today's PCM Mission",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp,
                                        color = Color.White
                                    )
                                    Text(
                                        text = "Auto-generated 3-Subject targets",
                                        fontSize = 11.sp,
                                        color = Color(0xFF94A3B8)
                                    )
                                }
                            }

                            Box(
                                modifier = Modifier
                                    .background(
                                        if (completedTodayCount == totalTodayCount && totalTodayCount > 0) Color(0xFF064E3B)
                                        else Color(0xFF1E293B),
                                        RoundedCornerShape(12.dp)
                                    )
                                    .padding(horizontal = 10.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = "$completedTodayCount / $totalTodayCount Done",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (completedTodayCount == totalTodayCount && totalTodayCount > 0) Color(0xFF34D399)
                                    else Color(0xFFCBD5E1)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        if (todayGoals.isEmpty()) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 12.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Button(
                                    onClick = { viewModel.refreshDailyGoals() },
                                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.AutoAwesome,
                                        contentDescription = null,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Generate Today's PCM Targets")
                                }
                            }
                        } else {
                            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                todayGoals.forEach { goal ->
                                    GoalItemCard(
                                        goal = goal,
                                        onToggleCompleted = { viewModel.toggleGoal(it) }
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            TextButton(
                                onClick = onNavigateToGoals,
                                modifier = Modifier.testTag("open_goals_button")
                            ) {
                                Text("Open Daily Planner & Log Hours", color = Color(0xFF38BDF8))
                                Spacer(modifier = Modifier.width(4.dp))
                                Icon(
                                    imageVector = Icons.Default.ChevronRight,
                                    contentDescription = null,
                                    tint = Color(0xFF38BDF8),
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }
                }
            }

            // 5. 6-Pillar Chapter Tracking Matrix Progress
            item {
                val stats = uiState.stats
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("six_pillars_card"),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = PortalSurfaceCard),
                    border = androidx.compose.foundation.BorderStroke(1.dp, PortalBorder),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "6-Pillar Chapter Checklist",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    color = Color.White
                                )
                                Text(
                                    text = "Theory • Notes • MathonGo • Ex-2 • Eklavya • Prev Tests",
                                    fontSize = 11.sp,
                                    color = Color(0xFF94A3B8)
                                )
                            }
                            CircularProgressBadge(
                                progressPercent = stats.overallPercent,
                                size = 52.dp,
                                progressColor = Color(0xFF38BDF8)
                            )
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            LabeledProgressBar(
                                label = "1. Theory Revision",
                                current = stats.theoryDone,
                                total = stats.totalChapters,
                                color = TheoryColor
                            )
                            LabeledProgressBar(
                                label = "2. Short 1-Page Summary / Formula Sheet",
                                current = stats.shortNotesDone,
                                total = stats.totalChapters,
                                color = NotesColor
                            )
                            LabeledProgressBar(
                                label = "3. MathonGo Concept Builder Questions",
                                current = stats.mathongoDone,
                                total = stats.totalChapters,
                                color = MathonGoColor
                            )
                            LabeledProgressBar(
                                label = "4. Module Ex-2 (Advanced Level)",
                                current = stats.moduleEx2Done,
                                total = stats.totalChapters,
                                color = ModuleEx2Color
                            )
                            LabeledProgressBar(
                                label = "5. EKLAVYA High-Yield Problems",
                                current = stats.eklavyaDone,
                                total = stats.totalChapters,
                                color = EklavyaColor
                            )
                            LabeledProgressBar(
                                label = "6. Previous Part Test Revision (1 Day Before)",
                                current = stats.prevTestRevDone,
                                total = stats.totalChapters,
                                color = PrevTestColor
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            TextButton(
                                onClick = onNavigateToChapters,
                                modifier = Modifier.testTag("manage_chapters_button")
                            ) {
                                Text("Open 6-Pillar Interactive Matrix", color = Color(0xFF38BDF8))
                                Spacer(modifier = Modifier.width(4.dp))
                                Icon(
                                    imageVector = Icons.Default.ChevronRight,
                                    contentDescription = null,
                                    tint = Color(0xFF38BDF8),
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }
                }
            }

            // 6. PCM Subject Balance
            item {
                val stats = uiState.stats
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = PortalSurfaceCard),
                    border = androidx.compose.foundation.BorderStroke(1.dp, PortalBorder),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "PCM Subject Balance",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            SubjectProgressItem(
                                title = "Physics",
                                percent = stats.physicsPercent,
                                color = PhysicsColor
                            )
                            SubjectProgressItem(
                                title = "Chemistry",
                                percent = stats.chemPercent,
                                color = ChemistryColor
                            )
                            SubjectProgressItem(
                                title = "Maths",
                                percent = stats.mathPercent,
                                color = MathColor
                            )
                        }
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}

@Composable
private fun PortalQuickAction(
    icon: ImageVector,
    title: String,
    subtitle: String,
    accentColor: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .clickable(onClick = onClick)
            .width(160.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = PortalSurfaceCard),
        border = androidx.compose.foundation.BorderStroke(1.dp, PortalBorder)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(accentColor.copy(alpha = 0.15f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = accentColor,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = Color.White
                )
                Text(
                    text = subtitle,
                    fontSize = 10.sp,
                    color = Color(0xFF94A3B8)
                )
            }
        }
    }
}

@Composable
private fun WebMetricCard(
    title: String,
    value: String,
    subtitle: String,
    accentColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = PortalSurfaceCard),
        border = androidx.compose.foundation.BorderStroke(1.dp, PortalBorder)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = title,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF94A3B8)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = value,
                fontSize = 18.sp,
                fontWeight = FontWeight.Black,
                color = accentColor
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = subtitle,
                fontSize = 10.sp,
                color = Color(0xFF64748B)
            )
        }
    }
}

@Composable
private fun SubjectProgressItem(
    title: String,
    percent: Int,
    color: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressBadge(
            progressPercent = percent,
            size = 58.dp,
            strokeWidth = 5.dp,
            progressColor = color
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = title,
            fontWeight = FontWeight.SemiBold,
            fontSize = 13.sp,
            color = Color.White
        )
    }
}
