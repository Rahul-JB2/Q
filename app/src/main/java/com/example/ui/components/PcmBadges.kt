package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.ChemistryColor
import com.example.ui.theme.ChemistryContainer
import com.example.ui.theme.MathColor
import com.example.ui.theme.MathContainer
import com.example.ui.theme.PhysicsColor
import com.example.ui.theme.PhysicsContainer

@Composable
fun SubjectBadge(
    subject: String,
    subSubject: String = "",
    modifier: Modifier = Modifier
) {
    val (bgColor, textColor, label) = when {
        subject.contains("PHYSIC", ignoreCase = true) -> Triple(PhysicsContainer, PhysicsColor, "Physics")
        subSubject.contains("P_CHEM", ignoreCase = true) || (subject.contains("CHEM", ignoreCase = true) && subSubject.contains("Physical", ignoreCase = true)) ->
            Triple(ChemistryContainer, ChemistryColor, "P-Chem")
        subSubject.contains("I_CHEM", ignoreCase = true) || (subject.contains("CHEM", ignoreCase = true) && subSubject.contains("Inorganic", ignoreCase = true)) ->
            Triple(Color(0xFFFEF3C7), Color(0xFFD97706), "I-Chem")
        subSubject.contains("O_CHEM", ignoreCase = true) || (subject.contains("CHEM", ignoreCase = true) && subSubject.contains("Organic", ignoreCase = true)) ->
            Triple(Color(0xFFFFE4E6), Color(0xFFE11D48), "O-Chem")
        subject.contains("CHEM", ignoreCase = true) -> Triple(ChemistryContainer, ChemistryColor, "Chemistry")
        subject.contains("MATH", ignoreCase = true) -> Triple(MathContainer, MathColor, "Mathematics")
        else -> Triple(MaterialTheme.colorScheme.surfaceVariant, MaterialTheme.colorScheme.onSurfaceVariant, subject)
    }

    Box(
        modifier = modifier
            .background(bgColor, RoundedCornerShape(6.dp))
            .padding(horizontal = 8.dp, vertical = 3.dp)
    ) {
        Text(
            text = label,
            color = textColor,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun PillarBadge(
    taskType: String,
    modifier: Modifier = Modifier
) {
    val (bgColor, textColor, label) = when (taskType) {
        "THEORY" -> Triple(Color(0xFFDBEAFE), Color(0xFF1D4ED8), "Theory")
        "SHORT_NOTES" -> Triple(Color(0xFFD1FAE5), Color(0xFF047857), "1-Page Notes")
        "MATHONGO_CBQ" -> Triple(Color(0xFFFEF3C7), Color(0xFFB45309), "MathonGo CBQ")
        "MODULE_EX2" -> Triple(Color(0xFFFCE7F3), Color(0xFFBE185D), "Module Ex-2")
        "EKLAVYA" -> Triple(Color(0xFFEDE9FE), Color(0xFF6D28D9), "EKLAVYA")
        "PREV_TEST_REV" -> Triple(Color(0xFFFEE2E2), Color(0xFFB91C1C), "Prev Test Rev")
        else -> Triple(Color(0xFFF1F5F9), Color(0xFF475569), "Practice")
    }

    Box(
        modifier = modifier
            .background(bgColor, RoundedCornerShape(4.dp))
            .padding(horizontal = 6.dp, vertical = 2.dp)
    ) {
        Text(
            text = label,
            color = textColor,
            fontSize = 10.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}
