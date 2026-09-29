package com.example.ui.home

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Functions
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.components.CountdownHeader
import com.example.ui.dashboard.DashboardViewModel
import com.example.ui.theme.ChemistryColor
import com.example.ui.theme.FlameOrange
import com.example.ui.theme.MathColor
import com.example.ui.theme.PhysicsColor
import com.example.ui.theme.PortalBackground
import com.example.ui.theme.PortalBorder
import com.example.ui.theme.PortalSurface
import com.example.ui.theme.PortalSurfaceCard
import com.example.ui.theme.Super50Gold

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: DashboardViewModel,
    onNavigateToTests: () -> Unit,
    onNavigateToGemini: (prefilledPrompt: String?) -> Unit,
    onNavigateToProfile: () -> Unit,
    onNavigateToRewardStore: () -> Unit,
    onNavigateToTimer: () -> Unit,
    onNavigateToVault: () -> Unit,
    onNavigateToChapters: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val todayGoals by viewModel.todayGoals.collectAsStateWithLifecycle()
    val studyLog by viewModel.todayStudyLog.collectAsStateWithLifecycle()
    val context = LocalContext.current

    var showEditTargetHoursDialog by remember { mutableStateOf(false) }
    var targetHoursInput by remember { mutableStateOf(studyLog.targetHours.toString()) }

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
                                    text = "IIT SUPER-50",
                                    fontWeight = FontWeight.Black,
                                    fontSize = 17.sp,
                                    letterSpacing = 0.5.sp,
                                    color = Color.White
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Box(
                                    modifier = Modifier
                                        .background(
                                            Brush.horizontalGradient(listOf(Color(0xFF6366F1), Color(0xFFA855F7))),
                                            RoundedCornerShape(4.dp)
                                        )
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = "JEE 2027",
                                        color = Color.White,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                            Text(
                                text = "Hardcore Discipline & Preparation Tracker",
                                fontSize = 11.sp,
                                color = Color(0xFF94A3B8)
                            )
                        }

                        // Top Stats: Streak & Mastery Points
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Streak badge
                            Box(
                                modifier = Modifier
                                    .background(FlameOrange.copy(alpha = 0.15f), RoundedCornerShape(16.dp))
                                    .border(1.dp, FlameOrange.copy(alpha = 0.4f), RoundedCornerShape(16.dp))
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
                                        text = "${studyLog.streak}d",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = FlameOrange
                                    )
                                }
                            }

                            // Mastery Points Wallet Badge
                            Box(
                                modifier = Modifier
                                    .clickable { onNavigateToRewardStore() }
                                    .background(Super50Gold.copy(alpha = 0.15f), RoundedCornerShape(16.dp))
                                    .border(1.dp, Super50Gold.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                                    .testTag("mastery_points_header_badge")
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = "🏆 ${studyLog.masteryPoints} Pts",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Super50Gold
                                    )
                                }
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
                .padding(horizontal = 14.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item { Spacer(modifier = Modifier.height(2.dp)) }

            // 1. ACTIVE REWARD PASS TICKER (if any pass is active)
            studyLog.activePassName?.let { passName ->
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onNavigateToRewardStore() },
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF064E3B)),
                        border = BorderStroke(1.dp, Color(0xFF10B981))
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp, vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(8.dp)
                                        .background(Color(0xFF34D399), CircleShape)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "ACTIVE PASS: $passName",
                                    color = Color.White,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Text(
                                text = "${studyLog.activePassRemainingMinutes}m left",
                                color = Color(0xFF6EE7B7),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            // 2. DISCIPLINE LOCKDOWN WARNING BOX (REDUCED TO COMPACT 40% SIZE)
            if (studyLog.isLockdownActive) {
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("discipline_lockdown_box_compact"),
                        shape = RoundedCornerShape(10.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF4C0519).copy(alpha = 0.85f)),
                        border = BorderStroke(1.dp, Color(0xFFF43F5E).copy(alpha = 0.6f))
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp, vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                modifier = Modifier.weight(1f),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Warning,
                                    contentDescription = "Alert",
                                    tint = Color(0xFFFB7185),
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Column {
                                    Text(
                                        text = "1-PAGE SUMMARY DUE",
                                        color = Color(0xFFFECDD3),
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Black
                                    )
                                    Text(
                                        text = "Submit notes for ${studyLog.lockdownChapterName}",
                                        color = Color(0xFFFDA4AF),
                                        fontSize = 10.sp
                                    )
                                }
                            }

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                TextButton(
                                    onClick = onNavigateToVault,
                                    contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 8.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = "Write Now",
                                        color = Color(0xFFFFE4E6),
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                IconButton(
                                    onClick = { viewModel.dismissLockdown() },
                                    modifier = Modifier.size(24.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Dismiss",
                                        tint = Color(0xFFFDA4AF),
                                        modifier = Modifier.size(14.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // 3. DAILY STUDY HOURS TRACKER (Editable Target + Progress)
            item {
                val totalStudiedHours = studyLog.physicsHours + studyLog.chemistryHours + studyLog.mathHours
                val targetHours = studyLog.targetHours
                val hoursRatio = (totalStudiedHours / targetHours.coerceAtLeast(1f)).coerceIn(0f, 1f)

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("study_hours_tracker_card"),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = PortalSurfaceCard),
                    border = BorderStroke(1.dp, PortalBorder)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = "Daily Study Hours Mandate",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp,
                                        color = Color.White
                                    )
                                    IconButton(
                                        onClick = {
                                            targetHoursInput = studyLog.targetHours.toString()
                                            showEditTargetHoursDialog = true
                                        },
                                        modifier = Modifier.size(24.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Edit,
                                            contentDescription = "Edit Target",
                                            tint = Super50Gold,
                                            modifier = Modifier.size(14.dp)
                                        )
                                    }
                                }
                                Text(
                                    text = "Mandatory target for discipline",
                                    fontSize = 11.sp,
                                    color = Color(0xFF94A3B8)
                                )
                            }

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = String.format("%.1fh", totalStudiedHours),
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Black,
                                    color = if (totalStudiedHours >= targetHours) Color(0xFF34D399) else Super50Gold
                                )
                                Text(
                                    text = " / ${targetHours.toInt()}h",
                                    fontSize = 13.sp,
                                    color = Color(0xFF94A3B8)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        LinearProgressIndicator(
                            progress = { hoursRatio },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp),
                            color = if (totalStudiedHours >= targetHours) Color(0xFF10B981) else Color(0xFF6366F1),
                            trackColor = Color(0xFF1E293B)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Phy: ${studyLog.physicsHours}h • Chem: ${studyLog.chemistryHours}h • Math: ${studyLog.mathHours}h",
                                fontSize = 11.sp,
                                color = Color(0xFF94A3B8)
                            )
                            TextButton(
                                onClick = onNavigateToTimer,
                                contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Timer,
                                    contentDescription = null,
                                    tint = Color(0xFF38BDF8),
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Focus Room", color = Color(0xFF38BDF8), fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }

            // 4. DAILY 100-QUESTION PRACTICE QUOTA BAR
            item {
                val totalSolved = studyLog.physicsQuestions + studyLog.chemistryQuestions + studyLog.mathQuestions
                val quota = studyLog.targetQuestionsQuota
                val quotaRatio = (totalSolved.toFloat() / quota.toFloat()).coerceIn(0f, 1f)

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("questions_quota_card"),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = PortalSurfaceCard),
                    border = BorderStroke(1.dp, PortalBorder)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "Daily 100-Question Quota",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = Color.White
                                )
                                Text(
                                    text = "Combined PCM Target (+50 Pts on completion)",
                                    fontSize = 11.sp,
                                    color = Color(0xFF94A3B8)
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .background(
                                        if (totalSolved >= quota) Color(0xFF064E3B) else Color(0xFF1E293B),
                                        RoundedCornerShape(8.dp)
                                    )
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = "$totalSolved / $quota Qs",
                                    color = if (totalSolved >= quota) Color(0xFF34D399) else Super50Gold,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Segmented bar
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .background(Color(0xFF1E293B), RoundedCornerShape(4.dp))
                        ) {
                            val pWeight = (studyLog.physicsQuestions.toFloat() / quota).coerceIn(0f, 1f)
                            val cWeight = (studyLog.chemistryQuestions.toFloat() / quota).coerceIn(0f, 1f)
                            val mWeight = (studyLog.mathQuestions.toFloat() / quota).coerceIn(0f, 1f)

                            if (pWeight > 0) {
                                Box(modifier = Modifier.weight(pWeight).fillMaxSize().background(PhysicsColor))
                            }
                            if (cWeight > 0) {
                                Box(modifier = Modifier.weight(cWeight).fillMaxSize().background(ChemistryColor))
                            }
                            if (mWeight > 0) {
                                Box(modifier = Modifier.weight(mWeight).fillMaxSize().background(MathColor))
                            }
                            val remaining = (1f - (pWeight + cWeight + mWeight)).coerceAtLeast(0f)
                            if (remaining > 0) {
                                Box(modifier = Modifier.weight(remaining).fillMaxSize())
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "Physics: ${studyLog.physicsQuestions} Qs", color = PhysicsColor, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                            Text(text = "Chem: ${studyLog.chemistryQuestions} Qs", color = ChemistryColor, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                            Text(text = "Math: ${studyLog.mathQuestions} Qs", color = MathColor, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
            }

            // 5. PCM 3-SUBJECT MASTER CARDS (Physics, Chemistry, Mathematics)
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Daily PCM Master Quota",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = Color.White
                    )
                    TextButton(onClick = { viewModel.refreshDailyGoals() }) {
                        Icon(imageVector = Icons.Default.AutoAwesome, contentDescription = null, tint = Super50Gold, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Regenerate", color = Super50Gold, fontSize = 11.sp)
                    }
                }
            }

            // Subject 1: PHYSICS CARD
            item {
                SubjectMasterCard(
                    subject = "PHYSICS",
                    accentColor = PhysicsColor,
                    chapterName = "Rotational Motion & Inertia",
                    weightage = "High Weightage",
                    questionsSolved = studyLog.physicsQuestions,
                    targetQuestions = 35,
                    onIncrementQuestions = { delta ->
                        viewModel.incrementSubjectQuestions("PHYSICS", delta)
                        Toast.makeText(context, "+$delta Physics questions logged! (+${delta * 2} Pts)", Toast.LENGTH_SHORT).show()
                    },
                    onOpenGeminiDoubts = {
                        onNavigateToGemini("Rotational Motion: How do I easily resolve moment of inertia for composite bodies?")
                    }
                )
            }

            // Subject 2: CHEMISTRY CARD
            item {
                SubjectMasterCard(
                    subject = "CHEMISTRY",
                    accentColor = ChemistryColor,
                    chapterName = "Chemical Kinetics & Radioactivity",
                    weightage = "High Weightage",
                    questionsSolved = studyLog.chemistryQuestions,
                    targetQuestions = 35,
                    onIncrementQuestions = { delta ->
                        viewModel.incrementSubjectQuestions("CHEMISTRY", delta)
                        Toast.makeText(context, "+$delta Chemistry questions logged! (+${delta * 2} Pts)", Toast.LENGTH_SHORT).show()
                    },
                    onOpenGeminiDoubts = {
                        onNavigateToGemini("Chemical Kinetics: Shortcut to determine order of reaction from initial rate data?")
                    }
                )
            }

            // Subject 3: MATHEMATICS CARD
            item {
                SubjectMasterCard(
                    subject = "MATHEMATICS",
                    accentColor = MathColor,
                    chapterName = "Definite Integrals & Area Under Curves",
                    weightage = "Crucial",
                    questionsSolved = studyLog.mathQuestions,
                    targetQuestions = 30,
                    onIncrementQuestions = { delta ->
                        viewModel.incrementSubjectQuestions("MATHEMATICS", delta)
                        Toast.makeText(context, "+$delta Mathematics questions logged! (+${delta * 2} Pts)", Toast.LENGTH_SHORT).show()
                    },
                    onOpenGeminiDoubts = {
                        onNavigateToGemini("Definite Integrals: Best tricks for King's Rule & Queen's Rule applications?")
                    }
                )
            }

            // 6. NEXT TEST COUNTDOWN PREVIEW
            item {
                CountdownHeader(
                    upcomingTest = uiState.upcomingTest,
                    currentDate = uiState.currentDate,
                    onViewPlannerClicked = onNavigateToTests,
                    modifier = Modifier.testTag("countdown_header")
                )
            }

            item { Spacer(modifier = Modifier.height(24.dp)) }
        }
    }

    if (showEditTargetHoursDialog) {
        AlertDialog(
            onDismissRequest = { showEditTargetHoursDialog = false },
            title = { Text("Edit Daily Study Hours Target", fontWeight = FontWeight.Bold) },
            text = {
                Column {
                    Text("Customize your daily study hours mandate (e.g. 8, 10, or 12 hours):", fontSize = 13.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = targetHoursInput,
                        onValueChange = { targetHoursInput = it },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val hours = targetHoursInput.toFloatOrNull() ?: 10f
                        viewModel.updateTargetHours(hours)
                        showEditTargetHoursDialog = false
                    }
                ) {
                    Text("Save Target")
                }
            },
            dismissButton = {
                TextButton(onClick = { showEditTargetHoursDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
private fun SubjectMasterCard(
    subject: String,
    accentColor: Color,
    chapterName: String,
    weightage: String,
    questionsSolved: Int,
    targetQuestions: Int,
    onIncrementQuestions: (Int) -> Unit,
    onOpenGeminiDoubts: () -> Unit
) {
    var formulaRevised by remember { mutableStateOf(false) }
    var summaryDone by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("subject_card_${subject.lowercase()}"),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = PortalSurfaceCard),
        border = BorderStroke(1.dp, accentColor.copy(alpha = 0.35f))
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            // Header Row: Subject, Weightage tag, Gemini Doubt button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .background(accentColor.copy(alpha = 0.15f), RoundedCornerShape(6.dp))
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = subject,
                            color = accentColor,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Box(
                        modifier = Modifier
                            .background(Color(0xFF1E293B), RoundedCornerShape(6.dp))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = weightage,
                            color = Color(0xFFCBD5E1),
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                // AI Doubt Solver Button
                IconButton(
                    onClick = onOpenGeminiDoubts,
                    modifier = Modifier
                        .size(32.dp)
                        .background(Color(0xFF6366F1).copy(alpha = 0.2f), CircleShape)
                        .testTag("ai_doubt_btn_${subject.lowercase()}")
                ) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = "Ask Gemini AI",
                        tint = Color(0xFFA5B4FC),
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = chapterName,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Stepper Row: - / +5 / +10
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Questions: $questionsSolved / $targetQuestions",
                        color = Color(0xFFCBD5E1),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "+2 Pts per solved problem",
                        color = Super50Gold,
                        fontSize = 10.sp
                    )
                }

                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    OutlinedButton(
                        onClick = { onIncrementQuestions(-5) },
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                        border = BorderStroke(1.dp, Color(0xFF334155))
                    ) {
                        Text("-5", color = Color(0xFF94A3B8), fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }

                    Button(
                        onClick = { onIncrementQuestions(5) },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = accentColor.copy(alpha = 0.2f)),
                        border = BorderStroke(1.dp, accentColor),
                        contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text("+5", color = accentColor, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }

                    Button(
                        onClick = { onIncrementQuestions(10) },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = accentColor),
                        contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 12.dp, vertical = 4.dp)
                    ) {
                        Text("+10", color = Color.Black, fontSize = 11.sp, fontWeight = FontWeight.Black)
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Checkbox options: Formula revised & Summary done
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = formulaRevised,
                        onCheckedChange = { formulaRevised = it },
                        colors = CheckboxDefaults.colors(checkedColor = accentColor)
                    )
                    Text(
                        text = "Formulas Revised",
                        fontSize = 11.sp,
                        color = if (formulaRevised) Color.White else Color(0xFF94A3B8)
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = summaryDone,
                        onCheckedChange = { summaryDone = it },
                        colors = CheckboxDefaults.colors(checkedColor = Color(0xFF10B981))
                    )
                    Text(
                        text = "1-Page Summary",
                        fontSize = 11.sp,
                        color = if (summaryDone) Color(0xFF34D399) else Color(0xFF94A3B8)
                    )
                }
            }
        }
    }
}
