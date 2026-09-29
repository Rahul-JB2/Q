package com.example.ui.chapters

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import com.example.data.model.ChapterItem
import com.example.ui.components.CircularProgressBadge
import com.example.ui.components.SubjectBadge
import com.example.ui.theme.EklavyaColor
import com.example.ui.theme.MathonGoColor
import com.example.ui.theme.ModuleEx2Color
import com.example.ui.theme.NotesColor
import com.example.ui.theme.PortalBackground
import com.example.ui.theme.PortalBorder
import com.example.ui.theme.PortalSurface
import com.example.ui.theme.PortalSurfaceCard
import com.example.ui.theme.PrevTestColor
import com.example.ui.theme.Super50Gold
import com.example.ui.theme.TheoryColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChapterListScreen(
    viewModel: ChapterViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var onlyWeakTopics by remember { mutableStateOf(false) }

    val displayedChapters = uiState.chapters.filter { ch ->
        if (onlyWeakTopics) ch.isWeakTopic else true
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
                                text = "6-PILLAR CHAPTER MATRIX",
                                fontWeight = FontWeight.Black,
                                fontSize = 16.sp,
                                letterSpacing = 0.5.sp,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Box(
                                modifier = Modifier
                                    .background(Color(0xFF38BDF8).copy(alpha = 0.2f), RoundedCornerShape(4.dp))
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = "${displayedChapters.size} CHAPTERS",
                                    color = Color(0xFF38BDF8),
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                        Text(
                            text = "Theory • 1-Page Notes • MathonGo CBQ • Ex-2 • Eklavya • Prev Tests",
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
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item { Spacer(modifier = Modifier.height(2.dp)) }

            // Search Bar
            item {
                OutlinedTextField(
                    value = uiState.searchQuery,
                    onValueChange = { viewModel.updateSearchQuery(it) },
                    placeholder = { Text("Search chapter or topic...") },
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
                    },
                    trailingIcon = {
                        if (uiState.searchQuery.isNotEmpty()) {
                            IconButton(onClick = { viewModel.updateSearchQuery("") }) {
                                Icon(imageVector = Icons.Default.Clear, contentDescription = "Clear")
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("chapter_search_input"),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )
            }

            // Subject & Weak Focus Filters
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FilterChip(
                        selected = onlyWeakTopics,
                        onClick = { onlyWeakTopics = !onlyWeakTopics },
                        label = { Text(if (onlyWeakTopics) "⭐ Weak Focus ON" else "⭐ Weak Focus", fontSize = 11.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Super50Gold.copy(alpha = 0.25f),
                            selectedLabelColor = Super50Gold
                        )
                    )

                    listOf(
                        "ALL" to "All Subjects",
                        "PHYSICS" to "Physics",
                        "CHEMISTRY" to "Chemistry",
                        "MATHEMATICS" to "Mathematics"
                    ).forEach { (code, label) ->
                        FilterChip(
                            selected = uiState.selectedSubject == code,
                            onClick = { viewModel.selectSubject(code) },
                            label = { Text(label, fontSize = 12.sp) },
                            modifier = Modifier.testTag("filter_subject_$code")
                        )
                    }
                }
            }

            // Part Test Filter Row
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    FilterChip(
                        selected = uiState.selectedTestFilter == 0,
                        onClick = { viewModel.selectTestFilter(0) },
                        label = { Text("All Tests", fontSize = 11.sp) }
                    )
                    (1..8).forEach { testNum ->
                        FilterChip(
                            selected = uiState.selectedTestFilter == testNum,
                            onClick = { viewModel.selectTestFilter(testNum) },
                            label = { Text("PT-$testNum", fontSize = 11.sp) }
                        )
                    }
                }
            }

            // Chapter list
            if (displayedChapters.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 40.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No chapters found matching filter criteria.",
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            } else {
                items(displayedChapters, key = { it.id }) { chapter ->
                    WebChapterCard(
                        chapter = chapter,
                        onCardClicked = { viewModel.setEditingChapter(chapter) },
                        onTogglePillar = { pillar -> viewModel.togglePillar(chapter, pillar) },
                        onToggleWeak = { viewModel.toggleWeakTopic(chapter) }
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(80.dp)) }
        }
    }

    uiState.editingChapter?.let { chapter ->
        ChapterDetailDialog(
            chapter = chapter,
            onDismiss = { viewModel.setEditingChapter(null) },
            onSave = { updated -> viewModel.saveChapterDetails(updated) }
        )
    }
}

@Composable
private fun WebChapterCard(
    chapter: ChapterItem,
    onCardClicked: () -> Unit,
    onTogglePillar: (String) -> Unit,
    onToggleWeak: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onCardClicked)
            .testTag("chapter_card_${chapter.id}"),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = PortalSurfaceCard),
        border = BorderStroke(1.dp, if (chapter.isWeakTopic) Super50Gold.copy(alpha = 0.5f) else PortalBorder),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            // Top Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SubjectBadge(subject = chapter.subject, subSubject = chapter.subSubject)
                    Box(
                        modifier = Modifier
                            .background(
                                Color.White.copy(alpha = 0.08f),
                                RoundedCornerShape(4.dp)
                            )
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "PT-${chapter.partTestId}",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF94A3B8)
                        )
                    }

                    if (chapter.isWeakTopic) {
                        Box(
                            modifier = Modifier
                                .background(Super50Gold.copy(alpha = 0.2f), RoundedCornerShape(4.dp))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "⭐ Weak Focus",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = Super50Gold
                            )
                        }
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = onToggleWeak,
                        modifier = Modifier.size(28.dp)
                    ) {
                        Icon(
                            imageVector = if (chapter.isWeakTopic) Icons.Default.Star else Icons.Outlined.StarBorder,
                            contentDescription = "Star Chapter",
                            tint = if (chapter.isWeakTopic) Super50Gold else Color.Gray,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    CircularProgressBadge(
                        progressPercent = chapter.completionPercent,
                        size = 38.dp,
                        strokeWidth = 4.dp
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = chapter.chapterName,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            if (chapter.formulaSheetSnippet.isNotBlank()) {
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = "Formula: ${chapter.formulaSheetSnippet}",
                    fontSize = 11.sp,
                    color = Color(0xFF38BDF8),
                    maxLines = 1
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // 6-Pillar Interactive Web Matrix Toggle Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                WebPillarPill(
                    label = "1. Theory",
                    isDone = chapter.theoryCompleted,
                    activeColor = TheoryColor,
                    onClick = { onTogglePillar("THEORY") }
                )
                WebPillarPill(
                    label = "2. 1-Page Notes",
                    isDone = chapter.shortNotesCompleted,
                    activeColor = NotesColor,
                    onClick = { onTogglePillar("SHORT_NOTES") }
                )
                WebPillarPill(
                    label = "3. MathonGo (${chapter.mathongoDone}/${chapter.mathongoTotal})",
                    isDone = chapter.mathongoQuestionsCompleted,
                    activeColor = MathonGoColor,
                    onClick = { onTogglePillar("MATHONGO_CBQ") }
                )
                WebPillarPill(
                    label = "4. Ex-2 (${chapter.moduleEx2Done}/${chapter.moduleEx2Total})",
                    isDone = chapter.moduleEx2Completed,
                    activeColor = ModuleEx2Color,
                    onClick = { onTogglePillar("MODULE_EX2") }
                )
                WebPillarPill(
                    label = "5. EKLAVYA (${chapter.eklavyaDone}/${chapter.eklavyaTotal})",
                    isDone = chapter.eklavyaCompleted,
                    activeColor = EklavyaColor,
                    onClick = { onTogglePillar("EKLAVYA") }
                )
                WebPillarPill(
                    label = "6. Prev Test Rev",
                    isDone = chapter.previousTestRevisionCompleted,
                    activeColor = PrevTestColor,
                    onClick = { onTogglePillar("PREV_TEST_REV") }
                )
            }
        }
    }
}

@Composable
private fun WebPillarPill(
    label: String,
    isDone: Boolean,
    activeColor: Color,
    onClick: () -> Unit
) {
    val bg = if (isDone) activeColor.copy(alpha = 0.2f) else Color(0xFF1E293B).copy(alpha = 0.6f)
    val text = if (isDone) activeColor else Color(0xFF94A3B8)
    val border = if (isDone) activeColor.copy(alpha = 0.6f) else Color(0xFF334155).copy(alpha = 0.4f)

    Box(
        modifier = Modifier
            .clickable(onClick = onClick)
            .background(bg, RoundedCornerShape(8.dp))
            .border(1.dp, border, RoundedCornerShape(8.dp))
            .padding(horizontal = 8.dp, vertical = 6.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(6.dp)
                    .background(if (isDone) activeColor else Color(0xFF64748B), RoundedCornerShape(3.dp))
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = label,
                fontSize = 11.sp,
                fontWeight = if (isDone) FontWeight.Bold else FontWeight.Medium,
                color = text
            )
        }
    }
}
