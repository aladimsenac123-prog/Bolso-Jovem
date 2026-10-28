package com.bolsojovem.pixcontrol.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bolsojovem.pixcontrol.data.model.AccountSummary
import com.bolsojovem.pixcontrol.ui.theme.AlertCrimson
import com.bolsojovem.pixcontrol.ui.theme.SafeEmerald
import com.bolsojovem.pixcontrol.ui.theme.StitchPrimary
import com.bolsojovem.pixcontrol.ui.theme.StitchPrimaryContainer

@Composable
fun HeroSummaryCard(
    summary: AccountSummary,
    onAdjustTargetClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val isOver = summary.isOverBudget
    val isDark = MaterialTheme.colorScheme.background.red < 0.5f

    val accentColor by animateColorAsState(
        targetValue = if (isOver) AlertCrimson else SafeEmerald,
        animationSpec = tween(durationMillis = 400),
        label = "HeroAccentColor"
    )

    val cardBackground = if (isDark) {
        Brush.linearGradient(
            colors = listOf(
                Color(0xFF161F30),
                Color(0xFF0F172A)
            )
        )
    } else {
        Brush.linearGradient(
            colors = listOf(
                Color(0xFFFFFFFF),
                Color(0xFFF2F3FF)
            )
        )
    }

    val cardBorderColor = if (isDark) {
        Color.White.copy(alpha = 0.1f)
    } else {
        Color(0xFFBACAC1).copy(alpha = 0.4f)
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(cardBackground)
            .border(1.dp, cardBorderColor, RoundedCornerShape(24.dp))
            .padding(20.dp)
    ) {
        Column {
            // 1. Top Row: "GASTO HOJE" + Big Amount + Status Pill Badge
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column {
                    Text(
                        text = "GASTO HOJE",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        letterSpacing = 1.2.sp
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Row(verticalAlignment = Alignment.Bottom) {
                        Text(
                            text = "R$ ",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                        )
                        Text(
                            text = String.format("%.2f", summary.totalSpentToday).replace(".", ","),
                            fontSize = 36.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.onSurface,
                            letterSpacing = (-0.5).sp
                        )
                    }
                }

                // Status Pill Badge (Stitch Style)
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(accentColor.copy(alpha = 0.18f))
                        .border(1.dp, accentColor.copy(alpha = 0.35f), CircleShape)
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = if (isOver) Icons.Default.Warning else Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = accentColor,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = if (isOver) "Atenção: Limite Excedido" else "Dentro da Meta",
                            color = accentColor,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 2. Middle Row: Progress labels + Gradient Progress Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val percentage = (summary.budgetPercentage * 100).toInt()
                Text(
                    text = "Utilizado: $percentage% (R$ ${String.format("%.2f", summary.totalSpentToday).replace(".", ",")} / R$ ${String.format("%.2f", summary.dailyBudgetLimit).replace(".", ",")})",
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = if (isOver) {
                        "+ R$ ${String.format("%.2f", summary.totalSpentToday - summary.dailyBudgetLimit).replace(".", ",")} extra"
                    } else {
                        "R$ ${summary.formattedRemaining} restam"
                    },
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = accentColor
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            LinearProgressIndicator(
                progress = { summary.budgetPercentage },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(CircleShape),
                color = accentColor,
                trackColor = if (isDark) Color(0xFF1E293B) else Color(0xFFEAEDFF)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 3. Bottom Row: Micro Stats + "Ajustar meta" Button (Stitch Style)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(cardBorderColor.copy(alpha = 0.5f))
            )
            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.ReceiptLong,
                        contentDescription = null,
                        tint = if (isDark) StitchPrimaryContainer else StitchPrimary,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "${summary.transactionCount} transações hoje",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (isDark) Color(0xFF243047) else Color(0xFFE2E7FF))
                        .clickable { onAdjustTargetClick() }
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Tune,
                            contentDescription = null,
                            tint = if (isDark) StitchPrimaryContainer else StitchPrimary,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Ajustar meta",
                            color = if (isDark) StitchPrimaryContainer else StitchPrimary,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}
