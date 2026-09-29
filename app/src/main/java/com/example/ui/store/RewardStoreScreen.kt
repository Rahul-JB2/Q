package com.example.ui.store

import android.content.Intent
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.service.StudyGuardManager
import com.example.ui.theme.PortalBackground
import com.example.ui.theme.PortalBorder
import com.example.ui.theme.PortalSurface
import com.example.ui.theme.PortalSurfaceCard
import com.example.ui.theme.Super50Gold

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RewardStoreScreen(
    viewModel: RewardStoreViewModel,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val studyLog by viewModel.studyLog.collectAsStateWithLifecycle()
    val activePass by viewModel.activePass.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.startCountdownLoop(context)
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = PortalBackground,
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.CardGiftcard,
                            contentDescription = null,
                            tint = Super50Gold,
                            modifier = Modifier.size(22.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "REWARD STORE",
                                    fontWeight = FontWeight.Black,
                                    fontSize = 17.sp,
                                    color = Color.White
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Box(
                                    modifier = Modifier
                                        .background(Super50Gold.copy(alpha = 0.2f), RoundedCornerShape(4.dp))
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = "PASSES",
                                        color = Super50Gold,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                            Text(
                                text = "Redeem JEE Mastery Points for Study Breaks",
                                fontSize = 11.sp,
                                color = Color(0xFF94A3B8)
                            )
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
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
                .padding(horizontal = 14.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item { Spacer(modifier = Modifier.height(2.dp)) }

            // 1. HERO POINT-BALANCE DISPLAY CARD
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("reward_points_hero_card"),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1C1917)),
                    border = BorderStroke(1.dp, Super50Gold.copy(alpha = 0.6f))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "AVAILABLE JEE MASTERY POINTS",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Black,
                                    letterSpacing = 0.5.sp,
                                    color = Super50Gold
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = "🏆 ${studyLog.masteryPoints} PTS",
                                    fontSize = 28.sp,
                                    fontWeight = FontWeight.Black,
                                    color = Color.White
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .background(Super50Gold.copy(alpha = 0.2f), RoundedCornerShape(10.dp))
                                    .border(1.dp, Super50Gold.copy(alpha = 0.5f), RoundedCornerShape(10.dp))
                                    .padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = "ACTIVE",
                                    color = Super50Gold,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // How to earn points info strip
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color(0xFF292524), RoundedCornerShape(10.dp))
                                .padding(10.dp)
                        ) {
                            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                Text(
                                    text = "How to Earn More Points:",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFE7E5E4)
                                )
                                Text(text = "• +2 Pts for every solved question (+5 / +10 steppers)", fontSize = 10.sp, color = Color(0xFFA8A29E))
                                Text(text = "• +50 Pts for completing the 100-Question Daily Quota", fontSize = 10.sp, color = Color(0xFFA8A29E))
                                Text(text = "• +40 Pts for hitting the 10-Hour Study Mandate", fontSize = 10.sp, color = Color(0xFFA8A29E))
                                Text(text = "• +30 Pts for completing daily PCM chapter goals", fontSize = 10.sp, color = Color(0xFFA8A29E))
                            }
                        }
                    }
                }
            }

            // 2. ACTIVE PASS LIVE COUNTDOWN TIMER WIDGET
            activePass?.let { passInfo ->
                item {
                    val minutes = passInfo.remainingSeconds / 60
                    val seconds = passInfo.remainingSeconds % 60
                    val formattedTime = String.format("%02d:%02d", minutes, seconds)

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("active_pass_countdown_card"),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF064E3B)),
                        border = BorderStroke(1.5.dp, Color(0xFF10B981))
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(
                                        modifier = Modifier
                                            .size(10.dp)
                                            .background(Color(0xFF34D399), CircleShape)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "PASS ACTIVATED & RUNNING",
                                        color = Color(0xFF34D399),
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Black
                                    )
                                }
                                Text(
                                    text = "Study Guard Unlocked",
                                    color = Color(0xFF6EE7B7),
                                    fontSize = 10.sp
                                )
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                text = passInfo.passName,
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            // Big Monospace Countdown Display
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(
                                        text = formattedTime,
                                        fontSize = 36.sp,
                                        fontWeight = FontWeight.Black,
                                        fontFamily = FontFamily.Monospace,
                                        color = Color(0xFF34D399)
                                    )
                                    Text(
                                        text = "Time remaining before re-lock",
                                        fontSize = 11.sp,
                                        color = Color(0xFFA7F3D0)
                                    )
                                }

                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    // Launch App button
                                    Button(
                                        onClick = {
                                            try {
                                                val launchIntent = context.packageManager.getLaunchIntentForPackage(passInfo.packageName)
                                                if (launchIntent != null) {
                                                    context.startActivity(launchIntent)
                                                } else {
                                                    Toast.makeText(context, "App not installed on device", Toast.LENGTH_SHORT).show()
                                                }
                                            } catch (e: Exception) {
                                                Toast.makeText(context, "Could not launch: ${e.message}", Toast.LENGTH_SHORT).show()
                                            }
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981)),
                                        shape = RoundedCornerShape(10.dp)
                                    ) {
                                        Icon(imageVector = Icons.Default.OpenInNew, contentDescription = null, modifier = Modifier.size(16.dp))
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("Open App", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            // End pass early button
                            OutlinedButton(
                                onClick = {
                                    viewModel.endPassEarly(context)
                                    Toast.makeText(context, "Pass ended early. Study Guard re-locked.", Toast.LENGTH_SHORT).show()
                                },
                                shape = RoundedCornerShape(10.dp),
                                border = BorderStroke(1.dp, Color(0xFF059669)),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Icon(imageVector = Icons.Default.Lock, contentDescription = null, tint = Color(0xFFA7F3D0), modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("End Pass Early & Re-Lock Distractions", color = Color(0xFFA7F3D0), fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }

            // 3. REDEEMABLE PASSES TITLE
            item {
                Text(
                    text = "Available Reward Passes",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            // 4. LIST OF REDEEMABLE CARDS
            items(uiState.passes) { pass ->
                val canAfford = studyLog.masteryPoints >= pass.pointsCost
                val isCurrentlyActive = activePass?.passId == pass.id

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("reward_pass_card_${pass.id}"),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = PortalSurfaceCard),
                    border = BorderStroke(1.dp, if (canAfford) Super50Gold.copy(alpha = 0.4f) else PortalBorder)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(44.dp)
                                        .background(Color(0xFF1E293B), CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(text = pass.icon, fontSize = 22.sp)
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text(
                                        text = pass.title,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp,
                                        color = Color.White
                                    )
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(imageVector = Icons.Default.Timer, contentDescription = null, tint = Super50Gold, modifier = Modifier.size(12.dp))
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = "${pass.durationMinutes} Minutes Allowance",
                                            fontSize = 11.sp,
                                            color = Super50Gold,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    }
                                }
                            }

                            // Cost Pill
                            Box(
                                modifier = Modifier
                                    .background(
                                        if (canAfford) Super50Gold.copy(alpha = 0.15f) else Color(0xFF1E293B),
                                        RoundedCornerShape(8.dp)
                                    )
                                    .padding(horizontal = 10.dp, vertical = 5.dp)
                            ) {
                                Text(
                                    text = "${pass.pointsCost} Pts",
                                    color = if (canAfford) Super50Gold else Color(0xFF94A3B8),
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Black
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = pass.description,
                            fontSize = 12.sp,
                            color = Color(0xFFCBD5E1),
                            lineHeight = 16.sp
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        // Action Row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "App: ${StudyGuardManager.getAppName(pass.packageName)}",
                                fontSize = 11.sp,
                                color = Color(0xFF94A3B8)
                            )

                            Button(
                                onClick = {
                                    viewModel.activatePass(context, pass) { success, msg ->
                                        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
                                    }
                                },
                                enabled = canAfford && !isCurrentlyActive,
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Super50Gold),
                                contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 16.dp, vertical = 6.dp),
                                modifier = Modifier.testTag("activate_pass_btn_${pass.id}")
                            ) {
                                Text(
                                    text = if (isCurrentlyActive) "Active" else if (canAfford) "Activate Pass" else "Need ${pass.pointsCost - studyLog.masteryPoints} Pts",
                                    color = if (isCurrentlyActive || !canAfford) Color.White else Color.Black,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Black
                                )
                            }
                        }
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(30.dp)) }
        }
    }
}
