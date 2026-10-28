package com.bolsojovem.pixcontrol.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.LocalAtm
import androidx.compose.material.icons.filled.PhoneIphone
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bolsojovem.pixcontrol.data.model.ExpenseCategory
import com.bolsojovem.pixcontrol.data.model.Transaction
import com.bolsojovem.pixcontrol.ui.theme.AlertCrimson
import java.time.format.DateTimeFormatter

@Composable
fun TransactionItemCard(
    transaction: Transaction,
    onDelete: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val haptic = LocalHapticFeedback.current
    val isDark = MaterialTheme.colorScheme.background.red < 0.5f

    val timeFormatter = remember { DateTimeFormatter.ofPattern("HH:mm") }
    val formattedTime = remember(transaction.timestamp) {
        transaction.timestamp.format(timeFormatter)
    }

    val (categoryIcon, iconBg, iconColor) = when (transaction.category) {
        ExpenseCategory.FOOD -> Triple(
            Icons.Default.Restaurant,
            if (isDark) Color(0xFF332025) else Color(0xFFFFE8D6),
            if (isDark) Color(0xFFFFB3AD) else Color(0xFFD97706)
        )
        ExpenseCategory.TRANSPORT -> Triple(
            Icons.Default.LocalAtm,
            if (isDark) Color(0xFF1E2F40) else Color(0xFFCCE5FF),
            if (isDark) Color(0xFF93CCFF) else Color(0xFF006398)
        )
        ExpenseCategory.LEISURE -> Triple(
            Icons.Default.SportsEsports,
            if (isDark) Color(0xFF2C1E3C) else Color(0xFFEDE9FE),
            if (isDark) Color(0xFFD8B4FE) else Color(0xFF7C3AED)
        )
        ExpenseCategory.SERVICES, ExpenseCategory.PIX_TRANSFER -> Triple(
            Icons.Default.PhoneIphone,
            if (isDark) Color(0xFF132B25) else Color(0xFFD1FAE5),
            if (isDark) Color(0xFF2FE0AC) else Color(0xFF006C50)
        )
        ExpenseCategory.EDUCATION, ExpenseCategory.STUDIES -> Triple(
            Icons.Default.School,
            if (isDark) Color(0xFF332A1E) else Color(0xFFFEF3C7),
            if (isDark) Color(0xFFFDE68A) else Color(0xFFD97706)
        )
        else -> Triple(
            Icons.Default.Fastfood,
            if (isDark) Color(0xFF1E293B) else Color(0xFFF1F5F9),
            if (isDark) Color(0xFF94A3B8) else Color(0xFF64748B)
        )
    }

    val cardBg = if (isDark) Color(0xFF161F30) else Color(0xFFFFFFFF)
    val cardBorder = if (isDark) Color.White.copy(alpha = 0.08f) else Color(0xFFBACAC1).copy(alpha = 0.3f)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(cardBg)
            .border(1.dp, cardBorder, RoundedCornerShape(18.dp))
            .padding(14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            // Category Icon in Squircle
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(iconBg),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = categoryIcon,
                    contentDescription = transaction.category.displayName,
                    tint = iconColor,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = transaction.title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(2.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = formattedTime,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = " • ",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(if (isDark) Color(0xFF1E293B) else Color(0xFFF2F3FF))
                            .padding(horizontal = 6.dp, vertical = 1.dp)
                    ) {
                        Text(
                            text = transaction.category.displayName,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "- ${transaction.formattedAmount}",
                fontSize = 14.sp,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.width(6.dp))

            // Delete button matching Stitch `w-8 h-8 rounded-lg bg-error-container/40 text-error`
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(AlertCrimson.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                IconButton(
                    onClick = {
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        onDelete(transaction.id)
                    },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Excluir gasto",
                        tint = AlertCrimson,
                        modifier = Modifier.size(17.dp)
                    )
                }
            }
        }
    }
}
