package com.example.ui.goals

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ChapterItem

@Composable
fun AddGoalDialog(
    chapters: List<ChapterItem>,
    onDismiss: () -> Unit,
    onAddGoal: (
        subject: String,
        subSubject: String,
        chapterId: Int,
        chapterName: String,
        taskType: String,
        title: String,
        description: String
    ) -> Unit
) {
    var selectedSubject by remember { mutableStateOf("PHYSICS") }
    var selectedPillar by remember { mutableStateOf("THEORY") }
    var selectedChapter by remember { mutableStateOf<ChapterItem?>(chapters.firstOrNull { it.subject == "PHYSICS" }) }
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    val filteredChapters = chapters.filter { it.subject == selectedSubject }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Custom Daily Goal") },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text("Select Subject:", fontSize = 13.sp)
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    listOf("PHYSICS" to "Physics", "CHEMISTRY" to "Chemistry", "MATHEMATICS" to "Maths").forEach { (code, label) ->
                        FilterChip(
                            selected = selectedSubject == code,
                            onClick = {
                                selectedSubject = code
                                selectedChapter = chapters.firstOrNull { it.subject == code }
                            },
                            label = { Text(label, fontSize = 12.sp) }
                        )
                    }
                }

                Text("Select Preparation Pillar:", fontSize = 13.sp)
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        listOf(
                            "THEORY" to "Theory",
                            "SHORT_NOTES" to "1-Page Summary",
                            "MATHONGO_CBQ" to "MathonGo"
                        ).forEach { (code, label) ->
                            FilterChip(
                                selected = selectedPillar == code,
                                onClick = { selectedPillar = code },
                                label = { Text(label, fontSize = 11.sp) }
                            )
                        }
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        listOf(
                            "MODULE_EX2" to "Module Ex-2",
                            "EKLAVYA" to "EKLAVYA",
                            "PREV_TEST_REV" to "Prev Test Rev"
                        ).forEach { (code, label) ->
                            FilterChip(
                                selected = selectedPillar == code,
                                onClick = { selectedPillar = code },
                                label = { Text(label, fontSize = 11.sp) }
                            )
                        }
                    }
                }

                if (filteredChapters.isNotEmpty()) {
                    Text("Select Chapter:", fontSize = 13.sp)
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        filteredChapters.take(6).forEach { ch ->
                            FilterChip(
                                selected = selectedChapter?.id == ch.id,
                                onClick = {
                                    selectedChapter = ch
                                    if (title.isEmpty()) {
                                        title = "[${ch.chapterName}] $selectedPillar Practice"
                                    }
                                },
                                label = { Text(ch.chapterName, maxLines = 1, fontSize = 11.sp) },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }

                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Task Title") },
                    placeholder = { Text("e.g. Solve 20 questions in Ex-2") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp)
                )

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Target & Notes (Optional)") },
                    placeholder = { Text("e.g. Focus on rotation & torque problems") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp)
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (title.isNotBlank()) {
                        val ch = selectedChapter
                        onAddGoal(
                            selectedSubject,
                            ch?.subSubject ?: selectedSubject,
                            ch?.id ?: 0,
                            ch?.chapterName ?: "General $selectedSubject",
                            selectedPillar,
                            title,
                            description
                        )
                        onDismiss()
                    }
                },
                enabled = title.isNotBlank()
            ) {
                Text("Add Goal")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
