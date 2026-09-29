package com.example.ui.timer

import android.widget.Toast
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
fun FocusTimerScreen(
    viewModel: FocusTimerViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val studyLog by viewModel.studyLog.collectAsStateWithLifecycle()
    val context = LocalContext.current

    var showSaveDialog by remember { mutableStateOf(false) }
    var solvedCountInput by remember { mutableStateOf("15") }

    val activeColor = when (uiState.selectedSubject) {
        "PHYSICS" -> PhysicsColor
        "CHEMISTRY" -> ChemistryColor
        "MATHEMATICS" -> MathColor
        else -> MaterialTheme.colorScheme.primary
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = PortalBackground,
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "PCM FOCUS TIMER",
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
                                    text = "POMODORO",
                                    color = Super50Gold,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                        Text(
                            text = "Timed deep work for JEE Physics, Chemistry & Maths",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = PortalSurface
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(4.dp))

            // 1. Select Subject Pills
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = PortalSurfaceCard),
                border = androidx.compose.foundation.BorderStroke(1.dp, PortalBorder)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = "Current Study Subject:",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF94A3B8)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        listOf(
                            "PHYSICS" to "Physics",
                            "CHEMISTRY" to "Chemistry",
                            "MATHEMATICS" to "Mathematics"
                        ).forEach { (code, label) ->
                            FilterChip(
                                selected = uiState.selectedSubject == code,
                                onClick = { viewModel.selectSubject(code) },
                                label = { Text(label, fontSize = 12.sp, fontWeight = FontWeight.Bold) },
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }

            // 2. Preset Modes
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                listOf(
                    25 to "25m Sprint",
                    50 to "50m Deep",
                    90 to "90m Test Mock"
                ).forEach { (mins, label) ->
                    FilterChip(
                        selected = !uiState.isStopwatchMode && uiState.totalDurationSeconds == mins * 60,
                        onClick = { viewModel.setPreset(mins) },
                        label = { Text(label, fontSize = 11.sp) },
                        modifier = Modifier.weight(1f)
                    )
                }
                FilterChip(
                    selected = uiState.isStopwatchMode,
                    onClick = { viewModel.setStopwatchMode() },
                    label = { Text("Stopwatch", fontSize = 11.sp) },
                    modifier = Modifier.weight(1f)
                )
            }

            // 3. Central Web-style Circular Stopwatch / Timer Display
            val minutes = uiState.remainingSeconds / 60
            val seconds = uiState.remainingSeconds % 60
            val timeText = "%02d:%02d".format(minutes, seconds)

            val progressFraction = if (uiState.isStopwatchMode) {
                ((uiState.remainingSeconds % 60) / 60f)
            } else {
                if (uiState.totalDurationSeconds > 0) {
                    (uiState.remainingSeconds.toFloat() / uiState.totalDurationSeconds)
                } else 1f
            }

            val animatedProgress by animateFloatAsState(targetValue = progressFraction, label = "timerProgress")

            Box(
                modifier = Modifier
                    .size(240.dp)
                    .padding(12.dp),
                contentAlignment = Alignment.Center
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val stroke = Stroke(width = 12.dp.toPx(), cap = StrokeCap.Round)
                    // Background track
                    drawCircle(
                        color = Color(0xFF1E293B),
                        radius = (size.minDimension - 12.dp.toPx()) / 2f,
                        style = stroke
                    )
                    // Active arc
                    drawArc(
                        color = activeColor,
                        startAngle = -90f,
                        sweepAngle = 360f * animatedProgress,
                        useCenter = false,
                        style = stroke
                    )
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = timeText,
                        fontFamily = FontFamily.Monospace,
                        fontSize = 44.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = if (uiState.isRunning) "FOCUSING • ${uiState.selectedSubject}" else "PAUSED",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (uiState.isRunning) activeColor else Color.Gray,
                        letterSpacing = 1.sp
                    )
                }
            }

            // 4. Timer Action Buttons
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { viewModel.reset() },
                    modifier = Modifier
                        .size(48.dp)
                        .background(Color(0xFF1E293B), CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Reset Timer",
                        tint = Color.White
                    )
                }

                Button(
                    onClick = {
                        if (uiState.isRunning) viewModel.pause() else viewModel.start()
                    },
                    modifier = Modifier
                        .height(56.dp)
                        .width(140.dp)
                        .testTag("timer_toggle_button"),
                    colors = ButtonDefaults.buttonColors(containerColor = activeColor),
                    shape = RoundedCornerShape(28.dp)
                ) {
                    Icon(
                        imageVector = if (uiState.isRunning) Icons.Default.Pause else Icons.Default.PlayArrow,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (uiState.isRunning) "PAUSE" else "START",
                        fontWeight = FontWeight.Black,
                        fontSize = 15.sp
                    )
                }

                IconButton(
                    onClick = { showSaveDialog = true },
                    modifier = Modifier
                        .size(48.dp)
                        .background(Color(0xFF1E293B), CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.Save,
                        contentDescription = "Save Session to Log",
                        tint = Color(0xFF10B981)
                    )
                }
            }

            // 5. Today's Cumulative Study Time
            studyLog?.let { log ->
                val totalHours = log.physicsHours + log.chemistryHours + log.mathHours
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = PortalSurfaceCard),
                    border = androidx.compose.foundation.BorderStroke(1.dp, PortalBorder)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Today's PCM Total Logged",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = Color.White
                            )
                            Text(
                                text = "%.1fh logged".format(totalHours),
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = Super50Gold
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Physics: %.1fh".format(log.physicsHours), fontSize = 12.sp, color = PhysicsColor, fontWeight = FontWeight.SemiBold)
                            Text("Chem: %.1fh".format(log.chemistryHours), fontSize = 12.sp, color = ChemistryColor, fontWeight = FontWeight.SemiBold)
                            Text("Maths: %.1fh".format(log.mathHours), fontSize = 12.sp, color = MathColor, fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(60.dp))
        }
    }

    if (showSaveDialog) {
        AlertDialog(
            onDismissRequest = { showSaveDialog = false },
            title = { Text("Log Focus Session into Today's PCM Log") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = "Subject: ${uiState.selectedSubject}",
                        fontWeight = FontWeight.Bold,
                        color = activeColor
                    )
                    Text("Enter number of problems solved in this session:")
                    OutlinedTextField(
                        value = solvedCountInput,
                        onValueChange = { solvedCountInput = it },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        label = { Text("Questions Solved") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val q = solvedCountInput.toIntOrNull() ?: 0
                        viewModel.logCurrentSessionToDatabase(q)
                        showSaveDialog = false
                        Toast.makeText(context, "Session recorded to today's PCM plan!", Toast.LENGTH_SHORT).show()
                    }
                ) {
                    Text("Save to Log")
                }
            },
            dismissButton = {
                TextButton(onClick = { showSaveDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}
