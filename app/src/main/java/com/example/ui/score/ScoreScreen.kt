package com.example.ui.score

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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
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
import com.example.ui.planner.LogTestScoreDialog
import com.example.ui.theme.ChemistryColor
import com.example.ui.theme.MathColor
import com.example.ui.theme.PhysicsColor
import com.example.ui.theme.PortalBackground
import com.example.ui.theme.PortalBorder
import com.example.ui.theme.PortalSurface
import com.example.ui.theme.PortalSurfaceCard
import com.example.ui.theme.Super50Gold

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScoreScreen(
    viewModel: ScoreViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val tests by viewModel.tests.collectAsStateWithLifecycle()
    val studyLogs by viewModel.studyLogs.collectAsStateWithLifecycle()

    var showAddMistakeDialog by remember { mutableStateOf(false) }
    var selectedTestToScore by remember { mutableStateOf<com.example.data.model.TestItem?>(null) }

    var mistakeSubject by remember { mutableStateOf("PHYSICS") }
    var mistakeCategory by remember { mutableStateOf("Silly Error") }
    var mistakeQuestionNote by remember { mutableStateOf("") }
    var mistakeCorrectiveAction by remember { mutableStateOf("") }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = PortalBackground,
        topBar = {
            Column(modifier = Modifier.background(PortalSurface)) {
                TopAppBar(
                    title = {
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "SCORE & ANALYTICS",
                                    fontWeight = FontWeight.Black,
                                    fontSize = 16.sp,
                                    color = Color.White
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Box(
                                    modifier = Modifier
                                        .background(Color(0xFF0284C7).copy(alpha = 0.2f), RoundedCornerShape(4.dp))
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = "FORENSICS",
                                        color = Color(0xFF38BDF8),
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                            Text(
                                text = "Mock Test Scores, Mistake Notebook & AIR Trends",
                                fontSize = 11.sp,
                                color = Color(0xFF94A3B8)
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = PortalSurface
                    )
                )

                TabRow(
                    selectedTabIndex = uiState.selectedSubTab,
                    containerColor = PortalSurface,
                    contentColor = Color.White,
                    indicator = { tabPositions ->
                        TabRowDefaults.SecondaryIndicator(
                            Modifier.tabIndicatorOffset(tabPositions[uiState.selectedSubTab]),
                            color = Color(0xFF38BDF8)
                        )
                    }
                ) {
                    Tab(
                        selected = uiState.selectedSubTab == 0,
                        onClick = { viewModel.selectSubTab(0) },
                        text = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(imageVector = Icons.Default.Assessment, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Scores & Mistakes", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    )
                    Tab(
                        selected = uiState.selectedSubTab == 1,
                        onClick = { viewModel.selectSubTab(1) },
                        text = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(imageVector = Icons.Default.TrendingUp, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("7-Day Trends & AIR", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    )
                }
            }
        },
        floatingActionButton = {
            if (uiState.selectedSubTab == 0) {
                FloatingActionButton(
                    onClick = { showAddMistakeDialog = true },
                    containerColor = Color(0xFF0284C7),
                    contentColor = Color.White,
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier.testTag("add_mistake_fab")
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "Add Mistake")
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Log Mistake", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    }
                }
            }
        }
    ) { innerPadding ->
        if (uiState.selectedSubTab == 0) {
            // SUB-TAB 0: MOCK SCORES & MISTAKE NOTEBOOK
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Section 1: Mock Test Scores History
                item {
                    Text(
                        text = "Mock Test Performance History",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = Color.White
                    )
                }

                val scoredTests = tests.filter { it.totalScore != null }
                if (scoredTests.isEmpty()) {
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = PortalSurfaceCard),
                            border = BorderStroke(1.dp, PortalBorder)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text("No mock test scores logged yet.", color = Color(0xFF94A3B8), fontSize = 13.sp)
                                Spacer(modifier = Modifier.height(8.dp))
                                Button(
                                    onClick = { selectedTestToScore = tests.firstOrNull() },
                                    colors = ButtonDefaults.buttonColors(containerColor = Super50Gold)
                                ) {
                                    Text("Log Part Test-1 Score", color = Color.Black, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                } else {
                    items(scoredTests) { test ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = PortalSurfaceCard),
                            border = BorderStroke(1.dp, PortalBorder)
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column {
                                        Text(text = test.testName, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = Color.White)
                                        Text(text = "${test.pattern} • ${test.testDate}", fontSize = 11.sp, color = Color(0xFF94A3B8))
                                    }
                                    Box(
                                        modifier = Modifier
                                            .background(Color(0xFF064E3B), RoundedCornerShape(8.dp))
                                            .padding(horizontal = 8.dp, vertical = 4.dp)
                                    ) {
                                        Text(
                                            text = "${test.totalScore} / ${test.maxScore}",
                                            color = Color(0xFF34D399),
                                            fontSize = 13.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(text = "Physics: ${test.scorePhysics ?: 0}/100", color = PhysicsColor, fontSize = 11.sp)
                                    Text(text = "Chem: ${test.scoreChemistry ?: 0}/100", color = ChemistryColor, fontSize = 11.sp)
                                    Text(text = "Math: ${test.scoreMath ?: 0}/100", color = MathColor, fontSize = 11.sp)
                                }
                            }
                        }
                    }
                }

                // Section 2: Mistake Notebook
                item {
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Mistake Notebook (${uiState.mistakes.size})",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = Color.White
                        )
                        Text(
                            text = "Classified Error Analysis",
                            fontSize = 11.sp,
                            color = Color(0xFF94A3B8)
                        )
                    }
                }

                items(uiState.mistakes) { mistake ->
                    val categoryColor = when (mistake.category) {
                        "Silly Error" -> Color(0xFFF43F5E)
                        "Formula Forgot" -> Super50Gold
                        "Conceptual Gap" -> Color(0xFFA855F7)
                        else -> Color(0xFF38BDF8) // Time shortage
                    }

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = PortalSurfaceCard),
                        border = BorderStroke(1.dp, categoryColor.copy(alpha = 0.35f))
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(
                                        modifier = Modifier
                                            .background(categoryColor.copy(alpha = 0.15f), RoundedCornerShape(6.dp))
                                            .padding(horizontal = 7.dp, vertical = 2.dp)
                                    ) {
                                        Text(text = mistake.category, color = categoryColor, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                    }
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(text = mistake.subject, color = Color(0xFFCBD5E1), fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                                }
                                Text(text = mistake.testName, color = Color(0xFF94A3B8), fontSize = 11.sp)
                            }

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = "Problem: ${mistake.questionNote}",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.White
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color(0xFF1E293B), RoundedCornerShape(6.dp))
                                    .padding(horizontal = 8.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = "Action: ${mistake.correctiveAction}",
                                    fontSize = 11.sp,
                                    color = Color(0xFF6EE7B7)
                                )
                            }
                        }
                    }
                }

                item { Spacer(modifier = Modifier.height(70.dp)) }
            }
        } else {
            // SUB-TAB 1: 7-DAY PERFORMANCE TRENDS & STRATEGY COACH
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // 1. Projected JEE Percentile & AIR Rank Band
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF131D31)),
                        border = BorderStroke(1.dp, Super50Gold.copy(alpha = 0.5f))
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(imageVector = Icons.Default.MilitaryTech, contentDescription = null, tint = Super50Gold, modifier = Modifier.size(22.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "PROJECTED JEE MAIN & ADVANCED RANK",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Black,
                                    color = Super50Gold
                                )
                            }
                            Spacer(modifier = Modifier.height(10.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column {
                                    Text("Predicted Percentile", fontSize = 11.sp, color = Color(0xFF94A3B8))
                                    Text("98.85%ile", fontSize = 20.sp, fontWeight = FontWeight.Black, color = Color(0xFF38BDF8))
                                }
                                Column(horizontalAlignment = Alignment.End) {
                                    Text("Projected AIR Band", fontSize = 11.sp, color = Color(0xFF94A3B8))
                                    Text("Top 1,800 - 3,200", fontSize = 18.sp, fontWeight = FontWeight.Black, color = Color(0xFF34D399))
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Based on BSEB Super-50 accuracy rate and 100-Question daily quota compliance.",
                                fontSize = 10.sp,
                                color = Color(0xFF64748B)
                            )
                        }
                    }
                }

                // 2. 7-Day Consistency Visualization
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = PortalSurfaceCard),
                        border = BorderStroke(1.dp, PortalBorder)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Text(
                                text = "7-Day Study Hours Trajectory",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.height(12.dp))

                            val days = listOf("Mon" to 8.5f, "Tue" to 9.0f, "Wed" to 7.5f, "Thu" to 10.0f, "Fri" to 8.0f, "Sat" to 9.5f, "Sun" to 11.0f)
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.Bottom
                            ) {
                                days.forEach { (day, hrs) ->
                                    val heightDp = (hrs * 7).dp
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text(text = "${hrs.toInt()}h", fontSize = 10.sp, color = Color(0xFFCBD5E1))
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Box(
                                            modifier = Modifier
                                                .width(22.dp)
                                                .height(heightDp)
                                                .background(
                                                    if (hrs >= 10f) Color(0xFF10B981) else Color(0xFF6366F1),
                                                    RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp)
                                                )
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(text = day, fontSize = 10.sp, color = Color(0xFF94A3B8))
                                    }
                                }
                            }
                        }
                    }
                }

                // 3. Subject-wise Strike Rate & Accuracy
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = PortalSurfaceCard),
                        border = BorderStroke(1.dp, PortalBorder)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Text(text = "Subject-Wise Question Accuracy", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color.White)
                            Spacer(modifier = Modifier.height(10.dp))

                            // Physics
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("Physics", color = PhysicsColor, fontSize = 12.sp, modifier = Modifier.width(75.dp))
                                LinearProgressIndicator(progress = { 0.78f }, modifier = Modifier.weight(1f).height(6.dp), color = PhysicsColor, trackColor = Color(0xFF1E293B))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("78%", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                            Spacer(modifier = Modifier.height(8.dp))

                            // Chemistry
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("Chemistry", color = ChemistryColor, fontSize = 12.sp, modifier = Modifier.width(75.dp))
                                LinearProgressIndicator(progress = { 0.86f }, modifier = Modifier.weight(1f).height(6.dp), color = ChemistryColor, trackColor = Color(0xFF1E293B))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("86%", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                            Spacer(modifier = Modifier.height(8.dp))

                            // Maths
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("Mathematics", color = MathColor, fontSize = 12.sp, modifier = Modifier.width(75.dp))
                                LinearProgressIndicator(progress = { 0.72f }, modifier = Modifier.weight(1f).height(6.dp), color = MathColor, trackColor = Color(0xFF1E293B))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("72%", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }

                // 4. Strategy Coach Insights
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = PortalSurfaceCard),
                        border = BorderStroke(1.dp, Color(0xFF10B981).copy(alpha = 0.4f))
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(imageVector = Icons.Default.School, contentDescription = null, tint = Color(0xFF34D399), modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Strategy Coach 3-Point Advice", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color(0xFF34D399))
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(text = "1. Mathematics accuracy is 72%: Reduce rough paper clutter and write step boundaries clearly.", fontSize = 11.sp, color = Color(0xFFCBD5E1))
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = "2. Chemistry Inorganic speed is optimal (+35 Qs/hr): Use freed up time for Physics numericals.", fontSize = 11.sp, color = Color(0xFFCBD5E1))
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = "3. Always verify 1-Page Summary one day before Part Test-1.", fontSize = 11.sp, color = Color(0xFFCBD5E1))
                        }
                    }
                }

                item { Spacer(modifier = Modifier.height(30.dp)) }
            }
        }
    }

    // Dialog to add new mistake note
    if (showAddMistakeDialog) {
        AlertDialog(
            onDismissRequest = { showAddMistakeDialog = false },
            title = { Text("Log Mistake in Mistake Notebook", fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Select Subject:", fontSize = 12.sp)
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        listOf("PHYSICS", "CHEMISTRY", "MATHEMATICS").forEach { sub ->
                            val sel = mistakeSubject == sub
                            Box(
                                modifier = Modifier
                                    .clickable { mistakeSubject = sub }
                                    .background(if (sel) Color(0xFF6366F1) else Color(0xFF1E293B), RoundedCornerShape(6.dp))
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text(sub.take(4), color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }

                    Text("Select Mistake Category:", fontSize = 12.sp)
                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        listOf("Silly Error", "Formula Forgot", "Conceptual Gap", "Time Shortage").forEach { cat ->
                            val sel = mistakeCategory == cat
                            Box(
                                modifier = Modifier
                                    .clickable { mistakeCategory = cat }
                                    .background(if (sel) Color(0xFF0284C7) else Color(0xFF1E293B), RoundedCornerShape(6.dp))
                                    .padding(horizontal = 6.dp, vertical = 3.dp)
                            ) {
                                Text(cat, color = Color.White, fontSize = 10.sp)
                            }
                        }
                    }

                    OutlinedTextField(
                        value = mistakeQuestionNote,
                        onValueChange = { mistakeQuestionNote = it },
                        label = { Text("What question/concept was wrong?") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = mistakeCorrectiveAction,
                        onValueChange = { mistakeCorrectiveAction = it },
                        label = { Text("Corrective action for next test") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (mistakeQuestionNote.isNotBlank()) {
                            viewModel.addMistakeNote("Part Test-1", mistakeSubject, mistakeCategory, mistakeQuestionNote, mistakeCorrectiveAction)
                            showAddMistakeDialog = false
                            mistakeQuestionNote = ""
                            mistakeCorrectiveAction = ""
                        }
                    }
                ) {
                    Text("Save to Notebook")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddMistakeDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    selectedTestToScore?.let { test ->
        LogTestScoreDialog(
            test = test,
            onDismiss = { selectedTestToScore = null },
            onSave = { targetTest, p, c, m, rank, remarks ->
                viewModel.logTestScore(targetTest, p, c, m, rank, remarks)
                selectedTestToScore = null
            }
        )
    }
}
