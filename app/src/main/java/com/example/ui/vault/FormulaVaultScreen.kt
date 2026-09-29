package com.example.ui.vault

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
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
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Functions
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.model.ChapterItem
import com.example.ui.chapters.ChapterDetailDialog
import com.example.ui.chapters.ChapterViewModel
import com.example.ui.components.SubjectBadge
import com.example.ui.theme.PortalBackground
import com.example.ui.theme.PortalBorder
import com.example.ui.theme.PortalSurface
import com.example.ui.theme.PortalSurfaceCard
import com.example.ui.theme.Super50Gold

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormulaVaultScreen(
    viewModel: ChapterViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
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
                                text = "1-PAGE CONCLUSION VAULT",
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
                                    text = "FORMULAS",
                                    color = Super50Gold,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                        Text(
                            text = "Instant 1-page formula summaries & rapid revision sheets",
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item { Spacer(modifier = Modifier.height(2.dp)) }

            // Search Bar
            item {
                OutlinedTextField(
                    value = uiState.searchQuery,
                    onValueChange = { viewModel.updateSearchQuery(it) },
                    placeholder = { Text("Search formulas (e.g. Range, Bayes, Carnot, Rydberg)...") },
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
                        .testTag("vault_search_input"),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )
            }

            // Filter Pills
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
                            label = { Text(label, fontSize = 11.sp) }
                        )
                    }
                }
            }

            // Chapters Formula List
            if (displayedChapters.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 40.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No formulas found matching criteria.",
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            } else {
                items(displayedChapters, key = { it.id }) { chapter ->
                    FormulaCardItem(
                        chapter = chapter,
                        onCopyClicked = { snippet ->
                            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                            val clip = ClipData.newPlainText("JEE Formula", "${chapter.chapterName}\n$snippet")
                            clipboard.setPrimaryClip(clip)
                            Toast.makeText(context, "Copied formula to clipboard!", Toast.LENGTH_SHORT).show()
                        },
                        onToggleWeak = { viewModel.toggleWeakTopic(chapter) },
                        onEditClicked = { viewModel.setEditingChapter(chapter) }
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
private fun FormulaCardItem(
    chapter: ChapterItem,
    onCopyClicked: (String) -> Unit,
    onToggleWeak: () -> Unit,
    onEditClicked: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("formula_card_${chapter.id}"),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = PortalSurfaceCard),
        border = androidx.compose.foundation.BorderStroke(1.dp, if (chapter.isWeakTopic) Super50Gold.copy(alpha = 0.5f) else PortalBorder),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            // Header Row
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
                            .background(Color.White.copy(alpha = 0.08f), RoundedCornerShape(4.dp))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "Part Test-${chapter.partTestId}",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF94A3B8)
                        )
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = onToggleWeak,
                        modifier = Modifier.size(28.dp)
                    ) {
                        Icon(
                            imageVector = if (chapter.isWeakTopic) Icons.Default.Star else Icons.Outlined.StarBorder,
                            contentDescription = "Toggle Weak Chapter",
                            tint = if (chapter.isWeakTopic) Super50Gold else Color.Gray,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    IconButton(
                        onClick = onEditClicked,
                        modifier = Modifier.size(28.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit Formula",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(17.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = chapter.chapterName,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            if (chapter.notesSummary.isNotBlank()) {
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = "Key Focus: ${chapter.notesSummary}",
                    fontSize = 12.sp,
                    color = Color(0xFF94A3B8)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Web-style Code / Formula Block
            val snippet = if (chapter.formulaSheetSnippet.isNotBlank()) {
                chapter.formulaSheetSnippet
            } else {
                "• 1-Page Summary: Review core lecture formulas, boundary conditions, and typical JEE traps for this chapter.\n• Tap Edit to record custom formulas."
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF090D16), RoundedCornerShape(8.dp))
                    .border(1.dp, Color(0xFF1E293B), RoundedCornerShape(8.dp))
                    .padding(12.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Functions,
                                contentDescription = null,
                                tint = Color(0xFF38BDF8),
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Formula Sheet",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF38BDF8)
                            )
                        }

                        IconButton(
                            onClick = { onCopyClicked(snippet) },
                            modifier = Modifier.size(24.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ContentCopy,
                                contentDescription = "Copy Formula",
                                tint = Color(0xFF94A3B8),
                                modifier = Modifier.size(14.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = snippet,
                        fontFamily = FontFamily.Monospace,
                        fontSize = 12.sp,
                        color = Color(0xFFE2E8F0),
                        lineHeight = 18.sp
                    )
                }
            }
        }
    }
}
