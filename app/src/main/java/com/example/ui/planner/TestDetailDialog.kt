package com.example.ui.planner

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.TestItem
import com.example.ui.components.SubjectBadge
import com.example.ui.theme.Super50Gold

@Composable
fun TestDetailDialog(
    test: TestItem,
    onDismiss: () -> Unit,
    onTogglePrevTestRevision: (TestItem) -> Unit,
    onToggleCompleted: (TestItem) -> Unit,
    onOpenLogScore: (TestItem) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .background(Super50Gold, RoundedCornerShape(6.dp))
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = test.testDate,
                            color = Color.Black,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Box(
                        modifier = Modifier
                            .background(
                                if (test.isCompleted) Color(0xFFD1FAE5) else MaterialTheme.colorScheme.surfaceVariant,
                                RoundedCornerShape(6.dp)
                            )
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = if (test.isCompleted) "Completed" else "Upcoming",
                            color = if (test.isCompleted) Color(0xFF047857) else MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = test.testName,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${test.pattern} • ${test.examMode}",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                if (test.cumulativeNote.isNotBlank()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFFFEF3C7), RoundedCornerShape(8.dp))
                            .padding(10.dp)
                    ) {
                        Text(
                            text = "ℹ️ ${test.cumulativeNote}",
                            color = Color(0xFF92400E),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                // 1 Day Before Test Revision Toggle
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Previous Part Test Revision",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Revision completed 1 day before test",
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Switch(
                            checked = test.previousTestRevisionDone,
                            onCheckedChange = { onTogglePrevTestRevision(test) }
                        )
                    }
                }

                HorizontalDivider()

                Text(
                    text = "Syllabus Breakdown:",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )

                // Physics
                SyllabusSection(
                    title = "Physics",
                    content = test.physicsSyllabus
                )

                // Math
                SyllabusSection(
                    title = "Mathematics",
                    content = test.mathSyllabus
                )

                // Chemistry
                if (test.pchemSyllabus != "-") {
                    SyllabusSection(
                        title = "Physical Chemistry",
                        content = test.pchemSyllabus
                    )
                }
                if (test.ichemSyllabus != "-") {
                    SyllabusSection(
                        title = "Inorganic Chemistry",
                        content = test.ichemSyllabus
                    )
                }
                if (test.ochemSyllabus != "-") {
                    SyllabusSection(
                        title = "Organic Chemistry",
                        content = test.ochemSyllabus
                    )
                }

                // Test Scores Card if completed
                if (test.totalScore != null) {
                    HorizontalDivider()
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f))
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                text = "Recorded Score: ${test.totalScore} / ${test.maxScore}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Physics: ${test.scorePhysics ?: 0}", fontSize = 12.sp)
                                Text("Chemistry: ${test.scoreChemistry ?: 0}", fontSize = 12.sp)
                                Text("Maths: ${test.scoreMath ?: 0}", fontSize = 12.sp)
                            }
                            if (!test.testRank.isNullOrBlank()) {
                                Spacer(modifier = Modifier.height(4.dp))
                                Text("Rank / Target: ${test.testRank}", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                            }
                            if (!test.analysisRemarks.isNullOrBlank()) {
                                Spacer(modifier = Modifier.height(4.dp))
                                Text("Error Analysis: ${test.analysisRemarks}", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onOpenLogScore(test)
                }
            ) {
                Icon(imageVector = Icons.Default.Assessment, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text(if (test.totalScore != null) "Edit Score" else "Log Score & Analysis")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Close")
            }
        }
    )
}

@Composable
private fun SyllabusSection(title: String, content: String) {
    Column {
        Text(
            text = title,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = content,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            lineHeight = 16.sp
        )
        Spacer(modifier = Modifier.height(6.dp))
    }
}
