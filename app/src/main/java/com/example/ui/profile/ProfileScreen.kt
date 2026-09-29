package com.example.ui.profile

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Functions
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.theme.FlameOrange
import com.example.ui.theme.PortalBackground
import com.example.ui.theme.PortalBorder
import com.example.ui.theme.PortalSurface
import com.example.ui.theme.PortalSurfaceCard
import com.example.ui.theme.Super50Gold

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    onNavigateToChapters: () -> Unit,
    onNavigateToVault: () -> Unit,
    onNavigateToTimer: () -> Unit,
    onNavigateToRewardStore: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val studyLog by viewModel.studyLog.collectAsStateWithLifecycle()
    val context = LocalContext.current

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = PortalBackground,
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "STUDENT PROFILE & GUARD",
                                fontWeight = FontWeight.Black,
                                fontSize = 16.sp,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Box(
                                modifier = Modifier
                                    .background(Super50Gold.copy(alpha = 0.2f), RoundedCornerShape(4.dp))
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = "REWARDS",
                                    color = Super50Gold,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                        Text(
                            text = "JEE Mastery Points, Reward Passes & Distraction Blocker",
                            fontSize = 11.sp,
                            color = Color(0xFF94A3B8)
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
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // 1. Student Identity Card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = PortalSurfaceCard),
                    border = BorderStroke(1.dp, PortalBorder)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(48.dp)
                                    .background(Brush.horizontalGradient(listOf(Color(0xFF6366F1), Color(0xFFA855F7))), CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(imageVector = Icons.Default.Person, contentDescription = null, tint = Color.White, modifier = Modifier.size(26.dp))
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(text = uiState.studentName, fontWeight = FontWeight.Black, fontSize = 17.sp, color = Color.White)
                                Text(text = uiState.targetExam, fontSize = 12.sp, color = Color(0xFFA5B4FC), fontWeight = FontWeight.SemiBold)
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color(0xFF1E293B), RoundedCornerShape(10.dp))
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(text = "🔥 ${studyLog.streak} Days", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = FlameOrange)
                                Text(text = "Daily Streak", fontSize = 10.sp, color = Color(0xFF94A3B8))
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(text = "⏱️ ${(studyLog.physicsHours + studyLog.chemistryHours + studyLog.mathHours).toInt()}h", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF38BDF8))
                                Text(text = "Today's Hours", fontSize = 10.sp, color = Color(0xFF94A3B8))
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(text = "🎯 ${studyLog.questionsSolved} Qs", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF34D399))
                                Text(text = "Solved Today", fontSize = 10.sp, color = Color(0xFF94A3B8))
                            }
                        }
                    }
                }
            }

            // 2. JEE Mastery Rewards Wallet Card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1B1917)),
                    border = BorderStroke(1.dp, Super50Gold.copy(alpha = 0.5f))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(text = "JEE MASTERY REWARDS WALLET", fontSize = 12.sp, fontWeight = FontWeight.Black, color = Super50Gold)
                                Text(text = "Redeem points to unlock temporary app passes", fontSize = 11.sp, color = Color(0xFFD6D3D1))
                            }
                            Box(
                                modifier = Modifier
                                    .background(Super50Gold, RoundedCornerShape(10.dp))
                                    .padding(horizontal = 10.dp, vertical = 4.dp)
                            ) {
                                Text(text = "🏆 ${studyLog.masteryPoints} PTS", color = Color.Black, fontSize = 13.sp, fontWeight = FontWeight.Black)
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "Earn Points: Daily PCM (+30 Pts) • 100-Question Quota (+50 Pts) • 10h Study Mandate (+40 Pts)",
                            fontSize = 10.sp,
                            color = Color(0xFFA8A29E)
                        )
                    }
                }
            }

            // 3. Redeemable Reward Passes (Store)
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Reward Store: Redeem App Passes",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = Color.White
                    )
                    TextButton(onClick = onNavigateToRewardStore) {
                        Text("View Full Store →", color = Super50Gold, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            items(uiState.availablePasses) { pass ->
                val canAfford = studyLog.masteryPoints >= pass.pointsCost
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = PortalSurfaceCard),
                    border = BorderStroke(1.dp, if (canAfford) Super50Gold.copy(alpha = 0.35f) else PortalBorder)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
                            Text(text = pass.icon, fontSize = 24.sp)
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(text = pass.title, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color.White)
                                Text(text = "${pass.durationMinutes} mins • ${pass.description}", fontSize = 11.sp, color = Color(0xFF94A3B8))
                            }
                        }

                        Button(
                            onClick = {
                                viewModel.redeemPass(pass) { success, msg ->
                                    Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
                                }
                            },
                            enabled = canAfford,
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Super50Gold),
                            contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Text(text = "${pass.pointsCost} Pts", color = Color.Black, fontSize = 11.sp, fontWeight = FontWeight.Black)
                        }
                    }
                }
            }

            // 4. Android App Blocker & Distraction Guard Panel
            item {
                Spacer(modifier = Modifier.height(6.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = PortalSurfaceCard),
                    border = BorderStroke(1.dp, Color(0xFFEF4444).copy(alpha = 0.4f))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.Security, contentDescription = null, tint = Color(0xFFF87171), modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = "Android App Blocker & Study Guard", fontWeight = FontWeight.Black, fontSize = 14.sp, color = Color.White)
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Monitors and locks distracting apps during study hours unless a Reward Pass is active.",
                            fontSize = 11.sp,
                            color = Color(0xFF94A3B8)
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        // Blocked catalog badges
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            listOf("📺 YouTube", "🌐 Chrome", "🎧 Pocket FM", "🎮 Games").forEach { appName ->
                                Box(
                                    modifier = Modifier
                                        .background(Color(0xFF450A0A), RoundedCornerShape(6.dp))
                                        .border(1.dp, Color(0xFF991B1B), RoundedCornerShape(6.dp))
                                        .padding(horizontal = 7.dp, vertical = 3.dp)
                                ) {
                                    Text(text = appName, color = Color(0xFFFCA5A5), fontSize = 10.sp, fontWeight = FontWeight.SemiBold)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        Text(text = "Configure Native Android Permissions:", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        Spacer(modifier = Modifier.height(6.dp))

                        // 1. Usage Access
                        OutlinedButton(
                            onClick = {
                                try {
                                    context.startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS))
                                } catch (_: Exception) {
                                    Toast.makeText(context, "Opening Android Settings", Toast.LENGTH_SHORT).show()
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Icon(imageVector = Icons.Default.OpenInNew, contentDescription = null, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("1. Grant Package Usage Access", fontSize = 11.sp)
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        // 2. Overlay Permission
                        OutlinedButton(
                            onClick = {
                                try {
                                    val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:${context.packageName}"))
                                    context.startActivity(intent)
                                } catch (_: Exception) {
                                    context.startActivity(Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION))
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Icon(imageVector = Icons.Default.OpenInNew, contentDescription = null, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("2. Grant Display Over Other Apps", fontSize = 11.sp)
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        // 3. Accessibility Service
                        OutlinedButton(
                            onClick = {
                                try {
                                    context.startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
                                } catch (_: Exception) {
                                    Toast.makeText(context, "Opening Accessibility Settings", Toast.LENGTH_SHORT).show()
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Icon(imageVector = Icons.Default.OpenInNew, contentDescription = null, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("3. Grant Accessibility Service", fontSize = 11.sp)
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Test Blocker Overlay Button
                        Button(
                            onClick = { viewModel.triggerBlockOverlay("YouTube") },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFDC2626)),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(imageVector = Icons.Default.Block, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Test Study Guard Blocker Overlay", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }
                    }
                }
            }

            // 5. Core Modules Launchers
            item {
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Core JEE Preparation Tools", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color.White)
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = PortalSurfaceCard),
                    border = BorderStroke(1.dp, PortalBorder)
                ) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onNavigateToChapters() }
                                .padding(10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(imageVector = Icons.Default.MenuBook, contentDescription = null, tint = Color(0xFF818CF8))
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text("6-Pillar Chapter Matrix", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color.White)
                                    Text("Theory, Notes, MathonGo CBQ, Ex-2, Eklavya", fontSize = 10.sp, color = Color(0xFF94A3B8))
                                }
                            }
                            Icon(imageVector = Icons.Default.ChevronRight, contentDescription = null, tint = Color(0xFF94A3B8))
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onNavigateToVault() }
                                .padding(10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(imageVector = Icons.Default.Functions, contentDescription = null, tint = Color(0xFF38BDF8))
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text("1-Page Formula & Conclusion Vault", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color.White)
                                    Text("Summary sheets, formulas, derivation checklists", fontSize = 10.sp, color = Color(0xFF94A3B8))
                                }
                            }
                            Icon(imageVector = Icons.Default.ChevronRight, contentDescription = null, tint = Color(0xFF94A3B8))
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onNavigateToTimer() }
                                .padding(10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(imageVector = Icons.Default.Timer, contentDescription = null, tint = Color(0xFF34D399))
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text("PCM Focus Room & Pomodoro Timer", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color.White)
                                    Text("25m, 45m, 60m & 180m 3-hr mock simulator", fontSize = 10.sp, color = Color(0xFF94A3B8))
                                }
                            }
                            Icon(imageVector = Icons.Default.ChevronRight, contentDescription = null, tint = Color(0xFF94A3B8))
                        }
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(30.dp)) }
        }
    }

    // Android Guard Blocker Popup Overlay Simulator
    if (uiState.showBlockOverlay) {
        AlertDialog(
            onDismissRequest = { viewModel.dismissBlockOverlay() },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.Lock, contentDescription = null, tint = Color(0xFFEF4444), modifier = Modifier.size(24.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "ACCESS DENIED: STUDY LOCKDOWN ACTIVE",
                        fontWeight = FontWeight.Black,
                        fontSize = 14.sp,
                        color = Color(0xFFF87171)
                    )
                }
            },
            text = {
                Column {
                    Text(
                        text = "You attempted to open ${uiState.blockedAppName} during mandatory study hours!",
                        fontSize = 13.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "\"IITian is not someone who never gets tired; it's someone who never stops before achieving their dream AIR.\" - BSEB Super-50 Mandate",
                        fontSize = 11.sp,
                        color = Color(0xFFCBD5E1),
                        fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Target Chapter Pending: Rotational Motion (35 Qs Quota)",
                        fontSize = 11.sp,
                        color = Color(0xFFFBBF24),
                        fontWeight = FontWeight.SemiBold
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val pass = uiState.availablePasses.firstOrNull { it.packageName.contains(uiState.blockedAppName, ignoreCase = true) }
                            ?: uiState.availablePasses.first()
                        viewModel.redeemPass(pass) { success, msg ->
                            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                            if (success) viewModel.dismissBlockOverlay()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Super50Gold)
                ) {
                    Text("Redeem Pass (120 Pts)", color = Color.Black, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { viewModel.dismissBlockOverlay() }) {
                    Text("Return to Study", color = Color(0xFF94A3B8))
                }
            }
        )
    }
}
