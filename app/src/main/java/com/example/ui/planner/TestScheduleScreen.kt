package com.example.ui.planner

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
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CheckCircleOutline
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.HistoryEdu
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import com.example.data.model.TestItem
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
fun TestScheduleScreen(
    viewModel: PlannerViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var showResetDialog by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = PortalBackground,
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "BSEB SUPER-50 PORTAL",
                                fontWeight = FontWeight.Black,
                                fontSize = 16.sp,
                                letterSpacing = 0.5.sp,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Box(
                                modifier = Modifier
                                    .background(Super50Gold.copy(alpha = 0.2f), RoundedCornerShape(4.dp))
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = "TEST SERIES",
                                    color = Super50Gold,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                        Text(
                            text = "19 High-Yield Tests • Part Tests 1-8 & Full Tests 1-11",
                            fontSize = 11.sp,
                            color = Color(0xFF94A3B8)
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { showResetDialog = true },
                        modifier = Modifier.testTag("reset_schedule_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.RestartAlt,
                            contentDescription = "Reset Super-50 Schedule",
                            tint = Color.White
                        )
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
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item { Spacer(modifier = Modifier.height(2.dp)) }

            // Filter Chips
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf(
                        "ALL" to "All Tests (${uiState.tests.size})",
                        "PART_TEST" to "Part Tests (8)",
                        "FULL_TEST" to "Full Tests (11)"
                    ).forEach { (code, label) ->
                        val isSelected = uiState.filterType == code
                        FilterChip(
                            selected = isSelected,
                            onClick = { viewModel.setFilter(code) },
                            label = {
                                Text(
                                    label,
                                    fontSize = 12.sp,
                                    color = if (isSelected) Color.Black else Color(0xFFCBD5E1),
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = Super50Gold,
                                containerColor = PortalSurfaceCard
                            ),
                            border = FilterChipDefaults.filterChipBorder(
                                enabled = true,
                                selected = isSelected,
                                borderColor = if (isSelected) Super50Gold else PortalBorder
                            )
                        )
                    }
                }
            }

            // Summary Card
            item {
                val completed = uiState.tests.count { it.isCompleted }
                val scoredTests = uiState.tests.filter { it.totalScore != null }
                val avgScore = if (scoredTests.isNotEmpty()) {
                    scoredTests.mapNotNull { it.totalScore }.average().toInt()
                } else null

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = PortalSurfaceCard),
                    border = BorderStroke(1.dp, PortalBorder),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "$completed / ${uiState.tests.size}",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Black,
                                color = Color(0xFF38BDF8)
                            )
                            Text(
                                text = "Completed",
                                fontSize = 11.sp,
                                color = Color(0xFF94A3B8)
                            )
                        }

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = if (avgScore != null) "$avgScore/300" else "N/A",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Black,
                                color = if (avgScore != null) Color(0xFF34D399) else Color(0xFF64748B)
                            )
                            Text(
                                text = "Avg Score",
                                fontSize = 11.sp,
                                color = Color(0xFF94A3B8)
                            )
                        }

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            val revDone = uiState.tests.count { it.previousTestRevisionDone }
                            Text(
                                text = "$revDone Tests",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Black,
                                color = Super50Gold
                            )
                            Text(
                                text = "1-Day Eve Revised",
                                fontSize = 11.sp,
                                color = Color(0xFF94A3B8)
                            )
                        }
                    }
                }
            }

            // Tests list
            items(uiState.tests, key = { it.id }) { test ->
                TestCardItem(
                    test = test,
                    onClick = { viewModel.setSelectedTest(test) },
                    onToggleCompleted = { viewModel.toggleTestCompleted(test) },
                    onOpenLogScore = { viewModel.setLoggingScoreTest(test) }
                )
            }

            item { Spacer(modifier = Modifier.height(80.dp)) }
        }
    }

    uiState.selectedTest?.let { test ->
        TestDetailDialog(
            test = test,
            onDismiss = { viewModel.setSelectedTest(null) },
            onTogglePrevTestRevision = { viewModel.togglePrevTestRevision(it) },
            onToggleCompleted = { viewModel.toggleTestCompleted(it) },
            onOpenLogScore = {
                viewModel.setSelectedTest(null)
                viewModel.setLoggingScoreTest(it)
            }
        )
    }

    uiState.loggingScoreTest?.let { test ->
        LogTestScoreDialog(
            test = test,
            onDismiss = { viewModel.setLoggingScoreTest(null) },
            onSave = { targetTest, p, c, m, rank, remarks ->
                viewModel.saveTestScores(targetTest, p, c, m, rank, remarks)
            }
        )
    }

    if (showResetDialog) {
        AlertDialog(
            onDismissRequest = { showResetDialog = false },
            title = { Text("Reset to Super-50 Schedule?") },
            text = { Text("This will restore the complete official BSEB Super-50 test schedule and chapter dataset from the planner.") },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.resetToSuper50Default()
                        showResetDialog = false
                    }
                ) {
                    Text("Reset")
                }
            },
            dismissButton = {
                TextButton(onClick = { showResetDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
private fun TestCardItem(
    test: TestItem,
    onClick: () -> Unit,
    onToggleCompleted: () -> Unit,
    onOpenLogScore: () -> Unit
) {
    val isAdv = test.pattern.contains("ADVANCED", ignoreCase = true)
    val patternColor = if (isAdv) Color(0xFFA855F7) else Color(0xFF38BDF8)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .testTag("test_card_${test.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (test.isCompleted) PortalSurfaceCard.copy(alpha = 0.8f) else PortalSurfaceCard
        ),
        border = BorderStroke(1.dp, if (test.isCompleted) Color(0xFF10B981).copy(alpha = 0.5f) else PortalBorder),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header row with Date and Mode
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .background(Super50Gold.copy(alpha = 0.15f), RoundedCornerShape(6.dp))
                            .border(1.dp, Super50Gold.copy(alpha = 0.4f), RoundedCornerShape(6.dp))
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = test.testDate,
                            color = Super50Gold,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Box(
                        modifier = Modifier
                            .background(patternColor.copy(alpha = 0.15f), RoundedCornerShape(6.dp))
                            .padding(horizontal = 7.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = test.pattern,
                            color = patternColor,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                IconButton(
                    onClick = onToggleCompleted,
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = if (test.isCompleted) Icons.Default.CheckCircle else Icons.Default.CheckCircleOutline,
                        contentDescription = "Toggle completed",
                        tint = if (test.isCompleted) Color(0xFF10B981) else Color(0xFF64748B)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Test Title & Mode
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = test.testName,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(top = 2.dp)
                    ) {
                        Icon(
                            imageVector = if (test.examMode.contains("CBT", ignoreCase = true)) Icons.Default.Computer else Icons.Default.HistoryEdu,
                            contentDescription = null,
                            tint = Color(0xFF94A3B8),
                            modifier = Modifier.size(13.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${test.examMode} • 10:00 AM - 01:00 PM",
                            fontSize = 11.sp,
                            color = Color(0xFF94A3B8)
                        )
                    }
                }

                if (test.totalScore != null) {
                    Box(
                        modifier = Modifier
                            .background(Color(0xFF064E3B), RoundedCornerShape(8.dp))
                            .border(1.dp, Color(0xFF059669), RoundedCornerShape(8.dp))
                            .padding(horizontal = 10.dp, vertical = 5.dp)
                    ) {
                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = "${test.totalScore} / ${test.maxScore}",
                                color = Color(0xFF34D399),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                            if (!test.testRank.isNullOrBlank()) {
                                Text(
                                    text = test.testRank,
                                    color = Color(0xFF6EE7B7),
                                    fontSize = 10.sp
                                )
                            }
                        }
                    }
                }
            }

            if (test.cumulativeNote.isNotBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF1E293B).copy(alpha = 0.5f), RoundedCornerShape(6.dp))
                        .padding(horizontal = 8.dp, vertical = 5.dp)
                ) {
                    Text(
                        text = "⚡ ${test.cumulativeNote}",
                        fontSize = 11.sp,
                        color = Color(0xFF38BDF8),
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            val chemText = listOf(test.pchemSyllabus, test.ichemSyllabus, test.ochemSyllabus)
                .filter { it.isNotBlank() }
                .joinToString(", ")
                .ifBlank { "Full Chemistry" }

            // Syllabus PCM Highlights
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .background(PhysicsColor.copy(alpha = 0.12f), RoundedCornerShape(6.dp))
                        .padding(horizontal = 6.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "P: ${test.physicsSyllabus.take(28)}...",
                        fontSize = 10.sp,
                        color = PhysicsColor,
                        maxLines = 1
                    )
                }
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .background(ChemistryColor.copy(alpha = 0.12f), RoundedCornerShape(6.dp))
                        .padding(horizontal = 6.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "C: ${chemText.take(28)}...",
                        fontSize = 10.sp,
                        color = ChemistryColor,
                        maxLines = 1
                    )
                }
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .background(MathColor.copy(alpha = 0.12f), RoundedCornerShape(6.dp))
                        .padding(horizontal = 6.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "M: ${test.mathSyllabus.take(28)}...",
                        fontSize = 10.sp,
                        color = MathColor,
                        maxLines = 1
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Action row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (test.previousTestRevisionDone) {
                    Box(
                        modifier = Modifier
                            .background(Color(0xFF312E81).copy(alpha = 0.5f), RoundedCornerShape(6.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "✓ 1-Day Eve Revised",
                            color = Color(0xFFA5B4FC),
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                }

                OutlinedButton(
                    onClick = onOpenLogScore,
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Super50Gold),
                    border = BorderStroke(1.dp, Super50Gold.copy(alpha = 0.6f)),
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.EditNote,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = if (test.totalScore != null) "Edit Scorecard" else "Log Score",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

