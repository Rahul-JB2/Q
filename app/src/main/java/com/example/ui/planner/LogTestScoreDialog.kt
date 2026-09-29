package com.example.ui.planner

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.TestItem

@Composable
fun LogTestScoreDialog(
    test: TestItem,
    onDismiss: () -> Unit,
    onSave: (
        test: TestItem,
        pScore: Int,
        cScore: Int,
        mScore: Int,
        rank: String,
        remarks: String
    ) -> Unit
) {
    var pStr by remember { mutableStateOf(test.scorePhysics?.toString() ?: "72") }
    var cStr by remember { mutableStateOf(test.scoreChemistry?.toString() ?: "68") }
    var mStr by remember { mutableStateOf(test.scoreMath?.toString() ?: "55") }
    var rank by remember { mutableStateOf(test.testRank ?: "AIR 240 / Batch Top 5") }
    var remarks by remember { mutableStateOf(test.analysisRemarks ?: "") }

    val pVal = pStr.toIntOrNull() ?: 0
    val cVal = cStr.toIntOrNull() ?: 0
    val mVal = mStr.toIntOrNull() ?: 0
    val total = pVal + cVal + mVal

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Column {
                Text("Log Test Result", fontWeight = FontWeight.Bold)
                Text(
                    text = "${test.testName} (${test.testDate})",
                    fontSize = 12.sp,
                    color = androidx.compose.material3.MaterialTheme.colorScheme.onSurfaceVariant
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
                Text(
                    text = "Total Score: $total / ${test.maxScore} (${if (test.maxScore > 0) (total * 100) / test.maxScore else 0}%)",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = androidx.compose.material3.MaterialTheme.colorScheme.primary
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = pStr,
                        onValueChange = { pStr = it },
                        label = { Text("Physics") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp)
                    )
                    OutlinedTextField(
                        value = cStr,
                        onValueChange = { cStr = it },
                        label = { Text("Chem") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp)
                    )
                    OutlinedTextField(
                        value = mStr,
                        onValueChange = { mStr = it },
                        label = { Text("Maths") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp)
                    )
                }

                OutlinedTextField(
                    value = rank,
                    onValueChange = { rank = it },
                    label = { Text("Rank / Batch Rank / Percentile") },
                    placeholder = { Text("e.g. Rank 14, 99.2%ile") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp)
                )

                OutlinedTextField(
                    value = remarks,
                    onValueChange = { remarks = it },
                    label = { Text("Error Analysis & Mistakes Log") },
                    placeholder = { Text("e.g. Lost 12 marks in calculation in Rotational, missed 2 questions in Periodic table") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3,
                    shape = RoundedCornerShape(8.dp)
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onSave(test, pVal, cVal, mVal, rank, remarks)
                }
            ) {
                Text("Save Score & Analysis")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
