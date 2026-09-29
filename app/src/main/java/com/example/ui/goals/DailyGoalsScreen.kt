package com.example.ui.goals

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.components.GoalItemCard
import com.example.ui.theme.PortalBackground
import com.example.ui.theme.PortalBorder
import com.example.ui.theme.PortalSurface
import com.example.ui.theme.PortalSurfaceCard
import com.example.ui.theme.Super50Gold
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DailyGoalsScreen(
    viewModel: DailyGoalsViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var showAddDialog by remember { mutableStateOf(false) }
    var showLogDialog by remember { mutableStateOf(false) }

    val dateFormatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy")
    val formattedDate = uiState.selectedDate.format(dateFormatter)
    val isToday = uiState.selectedDate == LocalDate.now()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = PortalBackground,
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "DAILY PCM MISSION",
                                fontWeight = FontWeight.Black,
                                fontSize = 16.sp,
                                letterSpacing = 0.5.sp,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Box(
                                modifier = Modifier
                                    .background(Super50Gold.copy(alpha = 0.2f), RoundedCornerShape(4.dp))
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = "3-SUBJECTS",
                                    color = Super50Gold,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                        Text(
                            text = "Automatic Physics, Chemistry & Maths targets",
                            fontSize = 11.sp,
                            color = Color(0xFF94A3B8)
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { viewModel.regenerateGoals() },
                        modifier = Modifier.testTag("regenerate_goals_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = "Regenerate PCM Suggestions",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = PortalSurface
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White,
                modifier = Modifier.testTag("add_custom_goal_fab")
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Custom Goal")
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item { Spacer(modifier = Modifier.height(4.dp)) }

            // Date Navigation Header
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = PortalSurfaceCard),
                    border = androidx.compose.foundation.BorderStroke(1.dp, PortalBorder)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = { viewModel.previousDay() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Previous Day", tint = Color.White)
                        }

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = if (isToday) "Today" else uiState.selectedDate.dayOfWeek.name.take(3),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = formattedDate,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White
                            )
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            if (!isToday) {
                                TextButton(onClick = { viewModel.goToToday() }) {
                                    Text("Today", fontSize = 12.sp, color = Color(0xFF38BDF8))
                                }
                            }
                            IconButton(onClick = { viewModel.nextDay() }) {
                                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Next Day", tint = Color.White)
                            }
                        }
                    }
                }
            }

            // 1 Day Before Test Revision Alert Banner
            if (uiState.isOneDayBeforeTest) {
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("test_eve_alert_card"),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFFEF2F2)),
                        border = androidx.compose.foundation.BorderStroke(1.5.dp, Color(0xFFEF4444))
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Warning,
                                contentDescription = null,
                                tint = Color(0xFFDC2626),
                                modifier = Modifier.size(28.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = "1 DAY BEFORE TEST PRIORITY",
                                    color = Color(0xFF991B1B),
                                    fontWeight = FontWeight.Black,
                                    fontSize = 13.sp
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = "Revise Previous Part Test papers, formulas, and error logbook. No heavy new concepts today!",
                                    color = Color(0xFFB91C1C),
                                    fontSize = 12.sp,
                                    lineHeight = 16.sp
                                )
                            }
                        }
                    }
                }
            }

            // Daily Study Hours Summary Card
            item {
                val log = uiState.studyLog
                val totalHours = (log?.physicsHours ?: 0f) + (log?.chemistryHours ?: 0f) + (log?.mathHours ?: 0f)
                val questionsSolved = log?.questionsSolved ?: 0

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "Study Time & Solved Count",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Total Logged: %.1fh • %d questions".format(totalHours, questionsSolved),
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            IconButton(onClick = { showLogDialog = true }) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = "Edit Study Hours",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }

                        if (log != null && totalHours > 0) {
                            Spacer(modifier = Modifier.height(10.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Physics: %.1fh".format(log.physicsHours), fontSize = 12.sp, fontWeight = FontWeight.Medium)
                                Text("Chemistry: %.1fh".format(log.chemistryHours), fontSize = 12.sp, fontWeight = FontWeight.Medium)
                                Text("Maths: %.1fh".format(log.mathHours), fontSize = 12.sp, fontWeight = FontWeight.Medium)
                            }
                            if (log.notes.isNotBlank()) {
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = "Notes: ${log.notes}",
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }

            // Goals Section Header
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Today's PCM Checklist (${uiState.goals.size})",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                    TextButton(onClick = { viewModel.regenerateGoals() }) {
                        Text("Regenerate", fontSize = 12.sp)
                    }
                }
            }

            // Goals list
            if (uiState.goals.isEmpty()) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "No goals set for this date yet.",
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Button(
                                onClick = { viewModel.ensureGoalsForCurrentDate() },
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                            ) {
                                Icon(Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Generate PCM Goals Now")
                            }
                        }
                    }
                }
            } else {
                items(uiState.goals, key = { it.id }) { goal ->
                    GoalItemCard(
                        goal = goal,
                        onToggleCompleted = { viewModel.toggleGoal(it) },
                        onDeleteGoal = { viewModel.deleteGoal(it) }
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(80.dp)) }
        }
    }

    if (showAddDialog) {
        AddGoalDialog(
            chapters = uiState.chapters,
            onDismiss = { showAddDialog = false },
            onAddGoal = { sub, subSub, chId, chName, pillar, title, desc ->
                viewModel.addCustomGoal(sub, subSub, chId, chName, pillar, title, desc)
            }
        )
    }

    if (showLogDialog) {
        StudySessionDialog(
            initialLog = uiState.studyLog,
            dateString = formattedDate,
            onDismiss = { showLogDialog = false },
            onSave = { p, c, m, q, notes ->
                viewModel.saveStudyLog(p, c, m, q, notes)
            }
        )
    }
}
