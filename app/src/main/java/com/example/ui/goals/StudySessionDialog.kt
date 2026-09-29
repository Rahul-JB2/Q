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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.DailyStudyLog

@Composable
fun StudySessionDialog(
    initialLog: DailyStudyLog?,
    dateString: String,
    onDismiss: () -> Unit,
    onSave: (
        physicsHours: Float,
        chemHours: Float,
        mathHours: Float,
        questionsSolved: Int,
        notes: String
    ) -> Unit
) {
    var pStr by remember { mutableStateOf(initialLog?.physicsHours?.toString() ?: "2.5") }
    var cStr by remember { mutableStateOf(initialLog?.chemistryHours?.toString() ?: "2.0") }
    var mStr by remember { mutableStateOf(initialLog?.mathHours?.toString() ?: "2.5") }
    var qStr by remember { mutableStateOf(initialLog?.questionsSolved?.toString() ?: "45") }
    var notes by remember { mutableStateOf(initialLog?.notes ?: "") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Log Study Hours ($dateString)") },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text("Record hours spent on each subject today:", fontSize = 13.sp)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = pStr,
                        onValueChange = { pStr = it },
                        label = { Text("Physics (h)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp)
                    )
                    OutlinedTextField(
                        value = cStr,
                        onValueChange = { cStr = it },
                        label = { Text("Chem (h)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp)
                    )
                    OutlinedTextField(
                        value = mStr,
                        onValueChange = { mStr = it },
                        label = { Text("Maths (h)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp)
                    )
                }

                OutlinedTextField(
                    value = qStr,
                    onValueChange = { qStr = it },
                    label = { Text("Total Questions Solved Today") },
                    placeholder = { Text("e.g. 50") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp)
                )

                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    label = { Text("Daily Reflection / Doubts to ask") },
                    placeholder = { Text("e.g. Need to revise Moments of Inertia theorem again") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 2,
                    shape = RoundedCornerShape(8.dp)
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val p = pStr.toFloatOrNull() ?: 0f
                    val c = cStr.toFloatOrNull() ?: 0f
                    val m = mStr.toFloatOrNull() ?: 0f
                    val q = qStr.toIntOrNull() ?: 0
                    onSave(p, c, m, q, notes)
                    onDismiss()
                }
            ) {
                Text("Save Log")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
