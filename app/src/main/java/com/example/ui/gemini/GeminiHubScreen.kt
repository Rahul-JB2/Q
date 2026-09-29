package com.example.ui.gemini

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.theme.PortalBackground
import com.example.ui.theme.PortalBorder
import com.example.ui.theme.PortalSurface
import com.example.ui.theme.PortalSurfaceCard
import com.example.ui.theme.Super50Gold

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GeminiHubScreen(
    viewModel: GeminiHubViewModel,
    initialPrompt: String? = null,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val tests by viewModel.tests.collectAsStateWithLifecycle()
    var inputQuery by remember { mutableStateOf("") }
    val listState = rememberLazyListState()

    LaunchedEffect(initialPrompt) {
        if (!initialPrompt.isNullOrBlank()) {
            inputQuery = initialPrompt
            viewModel.askQuestion(initialPrompt)
        }
    }

    LaunchedEffect(uiState.messages.size) {
        if (uiState.messages.isNotEmpty()) {
            listState.animateScrollToItem(uiState.messages.size - 1)
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = PortalBackground,
        topBar = {
            Column(modifier = Modifier.background(PortalSurface)) {
                TopAppBar(
                    title = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.AutoAwesome,
                                contentDescription = null,
                                tint = Color(0xFFA855F7),
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = "GEMINI AI HUB",
                                        fontWeight = FontWeight.Black,
                                        fontSize = 16.sp,
                                        color = Color.White
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Box(
                                        modifier = Modifier
                                            .background(
                                                Brush.horizontalGradient(listOf(Color(0xFF6366F1), Color(0xFFA855F7))),
                                                RoundedCornerShape(4.dp)
                                            )
                                            .padding(horizontal = 6.dp, vertical = 2.dp)
                                    ) {
                                        Text(
                                            text = "IITian MENTOR",
                                            color = Color.White,
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                                Text(
                                    text = "Doubt Resolution & Forensic Mock Summaries",
                                    fontSize = 11.sp,
                                    color = Color(0xFF94A3B8)
                                )
                            }
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = PortalSurface
                    )
                )

                // Sub-Tab Switcher
                TabRow(
                    selectedTabIndex = uiState.selectedMode,
                    containerColor = PortalSurface,
                    contentColor = Color.White,
                    indicator = { tabPositions ->
                        TabRowDefaults.SecondaryIndicator(
                            Modifier.tabIndicatorOffset(tabPositions[uiState.selectedMode]),
                            color = Color(0xFFA855F7)
                        )
                    }
                ) {
                    Tab(
                        selected = uiState.selectedMode == 0,
                        onClick = { viewModel.selectMode(0) },
                        text = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(imageVector = Icons.Default.Psychology, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("24/7 IITian Mentor", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    )
                    Tab(
                        selected = uiState.selectedMode == 1,
                        onClick = { viewModel.selectMode(1) },
                        text = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(imageVector = Icons.Default.Shield, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Forensic Deep-Dive", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        if (uiState.selectedMode == 0) {
            // MODE 0: 24/7 JEE IITIAN MENTOR
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                // Quick prompt chips
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf(
                        "Resolve Rotational Motion Doubt",
                        "Physical Chemistry Shortcut",
                        "Calculus King's Rule Trick",
                        "Organic Reaction Mechanism"
                    ).forEach { chipText ->
                        Box(
                            modifier = Modifier
                                .clickable {
                                    inputQuery = chipText
                                    viewModel.askQuestion(chipText)
                                }
                                .background(Color(0xFF1E293B), RoundedCornerShape(16.dp))
                                .border(1.dp, Color(0xFF334155), RoundedCornerShape(16.dp))
                                .padding(horizontal = 10.dp, vertical = 5.dp)
                        ) {
                            Text(text = chipText, color = Color(0xFFCBD5E1), fontSize = 11.sp)
                        }
                    }
                }

                // Chat Messages List
                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(uiState.messages) { msg ->
                        val isUser = msg.sender == "USER"
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(0.88f)
                                    .background(
                                        if (isUser) Color(0xFF4338CA) else PortalSurfaceCard,
                                        RoundedCornerShape(14.dp)
                                    )
                                    .border(
                                        1.dp,
                                        if (isUser) Color(0xFF6366F1) else PortalBorder,
                                        RoundedCornerShape(14.dp)
                                    )
                                    .padding(12.dp)
                            ) {
                                Column {
                                    Text(
                                        text = if (isUser) "You (JEE Aspirant)" else "Gemini IITian Mentor",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (isUser) Color(0xFFA5B4FC) else Color(0xFFA855F7)
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = msg.text,
                                        fontSize = 13.sp,
                                        color = Color.White,
                                        lineHeight = 18.sp
                                    )
                                }
                            }
                        }
                    }

                    if (uiState.isLoading) {
                        item {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Start
                            ) {
                                Box(
                                    modifier = Modifier
                                        .background(PortalSurfaceCard, RoundedCornerShape(12.dp))
                                        .padding(12.dp)
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        CircularProgressIndicator(
                                            modifier = Modifier.size(16.dp),
                                            color = Color(0xFFA855F7),
                                            strokeWidth = 2.dp
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text("AI Mentor formulating derivation & tricks...", fontSize = 12.sp, color = Color(0xFF94A3B8))
                                    }
                                }
                            }
                        }
                    }
                }

                // Chat Input Bar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(PortalSurface)
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = inputQuery,
                        onValueChange = { inputQuery = it },
                        placeholder = { Text("Ask doubt in Physics, Chem, or Math...", fontSize = 12.sp) },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("gemini_chat_input"),
                        shape = RoundedCornerShape(20.dp),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFFA855F7),
                            unfocusedBorderColor = PortalBorder
                        )
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(
                        onClick = {
                            if (inputQuery.isNotBlank()) {
                                viewModel.askQuestion(inputQuery)
                                inputQuery = ""
                            }
                        },
                        modifier = Modifier
                            .size(42.dp)
                            .background(Brush.horizontalGradient(listOf(Color(0xFF6366F1), Color(0xFFA855F7))), CircleShape)
                            .testTag("gemini_chat_send_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Send,
                            contentDescription = "Send",
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        } else {
            // MODE 1: FORENSIC MOCK TEST DEEP-DIVE SUMMARIES
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = PortalSurfaceCard),
                        border = BorderStroke(1.dp, Color(0xFFA855F7).copy(alpha = 0.4f))
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(imageVector = Icons.Default.AutoAwesome, contentDescription = null, tint = Color(0xFFA855F7), modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Forensic Mock Test Deep-Dive",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp,
                                    color = Color.White
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Select any completed Part Test to analyze silly errors, negative marks, deadly traps, and generate an exact 48-hour marks recovery roadmap.",
                                fontSize = 12.sp,
                                color = Color(0xFF94A3B8)
                            )
                        }
                    }
                }

                item {
                    Text(
                        text = "Choose Test for Forensic Analysis:",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                items(tests.take(4)) { test ->
                    val isSelected = uiState.selectedTestForForensic?.id == test.id
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { viewModel.generateForensicReport(test) },
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isSelected) Color(0xFF2E1065) else PortalSurfaceCard
                        ),
                        border = BorderStroke(1.dp, if (isSelected) Color(0xFFA855F7) else PortalBorder)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(text = test.testName, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color.White)
                                Text(text = "${test.pattern} • ${test.testDate}", fontSize = 11.sp, color = Color(0xFF94A3B8))
                            }
                            Button(
                                onClick = { viewModel.generateForensicReport(test) },
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFA855F7))
                            ) {
                                Text("Analyze", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }

                // Forensic Report Output
                uiState.forensicReport?.let { report ->
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFF131D31)),
                            border = BorderStroke(1.dp, Color(0xFF10B981).copy(alpha = 0.5f))
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = "FORENSIC ANALYSIS: ${report.testName.uppercase()}",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Black,
                                    color = Color(0xFF34D399)
                                )
                                Spacer(modifier = Modifier.height(10.dp))

                                // 1. Deadly Traps
                                Text(
                                    text = "⚠️ Deadly Traps Identified:",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFF43F5E)
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                report.deadlyTraps.forEach { trap ->
                                    Text(text = "• $trap", fontSize = 12.sp, color = Color(0xFFCBD5E1), modifier = Modifier.padding(vertical = 2.dp))
                                }

                                Spacer(modifier = Modifier.height(12.dp))

                                // 2. 3 High-Yield Fixes
                                Text(
                                    text = "🎯 3 Immediate High-Yield Fixes (Next 48 Hours):",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Super50Gold
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                report.highYieldFixes.forEach { fix ->
                                    Text(text = "• $fix", fontSize = 12.sp, color = Color(0xFFCBD5E1), modifier = Modifier.padding(vertical = 2.dp))
                                }

                                Spacer(modifier = Modifier.height(12.dp))

                                // 3. Score Projection
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(Color(0xFF064E3B), RoundedCornerShape(10.dp))
                                        .padding(10.dp)
                                ) {
                                    Text(
                                        text = report.targetNextScoreProjection,
                                        fontSize = 12.sp,
                                        color = Color(0xFF6EE7B7),
                                        fontWeight = FontWeight.SemiBold
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
}
