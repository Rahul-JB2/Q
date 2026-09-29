package com.example.ui.chapters

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ChapterItem
import com.example.ui.components.CircularProgressBadge
import com.example.ui.components.SubjectBadge

@Composable
fun ChapterDetailDialog(
    chapter: ChapterItem,
    onDismiss: () -> Unit,
    onSave: (ChapterItem) -> Unit
) {
    var theory by remember { mutableStateOf(chapter.theoryCompleted) }
    var notes by remember { mutableStateOf(chapter.shortNotesCompleted) }
    var mathongo by remember { mutableStateOf(chapter.mathongoQuestionsCompleted) }
    var moduleEx2 by remember { mutableStateOf(chapter.moduleEx2Completed) }
    var eklavya by remember { mutableStateOf(chapter.eklavyaCompleted) }
    var prevTestRev by remember { mutableStateOf(chapter.previousTestRevisionCompleted) }

    var mathongoDoneStr by remember { mutableStateOf(chapter.mathongoDone.toString()) }
    var mathongoTotalStr by remember { mutableStateOf(chapter.mathongoTotal.toString()) }

    var ex2DoneStr by remember { mutableStateOf(chapter.moduleEx2Done.toString()) }
    var ex2TotalStr by remember { mutableStateOf(chapter.moduleEx2Total.toString()) }

    var eklavyaDoneStr by remember { mutableStateOf(chapter.eklavyaDone.toString()) }
    var eklavyaTotalStr by remember { mutableStateOf(chapter.eklavyaTotal.toString()) }

    var notesSummary by remember { mutableStateOf(chapter.notesSummary) }
    var doubts by remember { mutableStateOf(chapter.doubts) }
    var formulaSnippet by remember { mutableStateOf(chapter.formulaSheetSnippet) }
    var isWeak by remember { mutableStateOf(chapter.isWeakTopic) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SubjectBadge(subject = chapter.subject, subSubject = chapter.subSubject)
                    Box(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.primaryContainer, RoundedCornerShape(6.dp))
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = "Part Test-${chapter.partTestId}",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = chapter.chapterName,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "6-Pillar Preparation Checklist:",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )

                // 1. Theory
                PillarCheckRow(
                    label = "1. Theory Revision",
                    checked = theory,
                    onCheckedChange = { theory = it }
                )

                // 2. Short 1-Page Notes
                PillarCheckRow(
                    label = "2. Short 1-Page Conclusion Notes",
                    checked = notes,
                    onCheckedChange = { notes = it }
                )

                // 3. MathonGo Concept Builder
                PillarCheckRow(
                    label = "3. MathonGo Concept Builders",
                    checked = mathongo,
                    onCheckedChange = { mathongo = it }
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = mathongoDoneStr,
                        onValueChange = { mathongoDoneStr = it },
                        label = { Text("MathonGo Solved") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp)
                    )
                    OutlinedTextField(
                        value = mathongoTotalStr,
                        onValueChange = { mathongoTotalStr = it },
                        label = { Text("Total CBQ") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp)
                    )
                }

                // 4. Module Ex-2
                PillarCheckRow(
                    label = "4. Module Ex-2 (Advanced Level)",
                    checked = moduleEx2,
                    onCheckedChange = { moduleEx2 = it }
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = ex2DoneStr,
                        onValueChange = { ex2DoneStr = it },
                        label = { Text("Ex-2 Solved") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp)
                    )
                    OutlinedTextField(
                        value = ex2TotalStr,
                        onValueChange = { ex2TotalStr = it },
                        label = { Text("Total Ex-2") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp)
                    )
                }

                // 5. EKLAVYA
                PillarCheckRow(
                    label = "5. EKLAVYA High-Yield Questions",
                    checked = eklavya,
                    onCheckedChange = { eklavya = it }
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = eklavyaDoneStr,
                        onValueChange = { eklavyaDoneStr = it },
                        label = { Text("Eklavya Solved") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp)
                    )
                    OutlinedTextField(
                        value = eklavyaTotalStr,
                        onValueChange = { eklavyaTotalStr = it },
                        label = { Text("Total Eklavya") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp)
                    )
                }

                // 6. Previous Part Test Revision
                PillarCheckRow(
                    label = "6. Previous Part Test Revision (1 Day Before)",
                    checked = prevTestRev,
                    onCheckedChange = { prevTestRev = it }
                )

                HorizontalDivider()

                PillarCheckRow(
                    label = "⭐ Mark as Weak Chapter / High Priority Focus",
                    checked = isWeak,
                    onCheckedChange = { isWeak = it }
                )

                OutlinedTextField(
                    value = formulaSnippet,
                    onValueChange = { formulaSnippet = it },
                    label = { Text("1-Page Formula Sheet & Key Principles") },
                    placeholder = { Text("e.g. v^2 = u^2 + 2as, banking tan(theta) = v^2/rg...") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3,
                    shape = RoundedCornerShape(8.dp)
                )

                OutlinedTextField(
                    value = notesSummary,
                    onValueChange = { notesSummary = it },
                    label = { Text("Key Formulas & Quick Notes") },
                    placeholder = { Text("Record tricky formulas, exceptions, shortcuts") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 2,
                    shape = RoundedCornerShape(8.dp)
                )

                OutlinedTextField(
                    value = doubts,
                    onValueChange = { doubts = it },
                    label = { Text("Doubt Logbook & Weak Spots") },
                    placeholder = { Text("Write question numbers or concepts to clarify with teachers") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 2,
                    shape = RoundedCornerShape(8.dp)
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val updated = chapter.copy(
                        theoryCompleted = theory,
                        shortNotesCompleted = notes,
                        mathongoQuestionsCompleted = mathongo,
                        moduleEx2Completed = moduleEx2,
                        eklavyaCompleted = eklavya,
                        previousTestRevisionCompleted = prevTestRev,
                        mathongoDone = mathongoDoneStr.toIntOrNull() ?: chapter.mathongoDone,
                        mathongoTotal = mathongoTotalStr.toIntOrNull() ?: chapter.mathongoTotal,
                        moduleEx2Done = ex2DoneStr.toIntOrNull() ?: chapter.moduleEx2Done,
                        moduleEx2Total = ex2TotalStr.toIntOrNull() ?: chapter.moduleEx2Total,
                        eklavyaDone = eklavyaDoneStr.toIntOrNull() ?: chapter.eklavyaDone,
                        eklavyaTotal = eklavyaTotalStr.toIntOrNull() ?: chapter.eklavyaTotal,
                        notesSummary = notesSummary,
                        doubts = doubts,
                        formulaSheetSnippet = formulaSnippet,
                        isWeakTopic = isWeak
                    )
                    onSave(updated)
                }
            ) {
                Text("Save Chapter Progress")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
private fun PillarCheckRow(
    label: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontSize = 13.sp,
            fontWeight = if (checked) FontWeight.Bold else FontWeight.Normal,
            color = if (checked) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1f)
        )
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
}
