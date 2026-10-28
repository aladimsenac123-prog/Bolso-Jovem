package com.bolsojovem.pixcontrol.ui.screens

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bolsojovem.pixcontrol.data.model.SavingGoal
import com.bolsojovem.pixcontrol.ui.components.CupertinoTabBar
import com.bolsojovem.pixcontrol.ui.components.NavItem
import com.bolsojovem.pixcontrol.ui.components.TopBar
import com.bolsojovem.pixcontrol.ui.theme.DarkBackground
import com.bolsojovem.pixcontrol.ui.theme.DarkBorderHairline
import com.bolsojovem.pixcontrol.ui.theme.DarkSurfaceCard
import com.bolsojovem.pixcontrol.ui.theme.PixMint
import com.bolsojovem.pixcontrol.ui.theme.TextPrimaryDark
import com.bolsojovem.pixcontrol.ui.theme.TextSecondaryDark

@Composable
fun GoalsScreen(
    goals: List<SavingGoal> = sampleGoals(),
    onContribute: (String, Double) -> Unit = { _, _ -> },
    onNavigateTab: (NavItem) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val haptic = LocalHapticFeedback.current

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = DarkBackground,
        topBar = { TopBar() },
        bottomBar = {
            CupertinoTabBar(
                selectedItem = NavItem.Goals,
                onItemSelected = onNavigateTab
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                GoalsHeader(totalSaved = goals.sumOf { it.currentAmount })
            }

            items(goals, key = { it.id }) { goal ->
                GoalSquircleCard(
                    goal = goal,
                    onContribute = { amount ->
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        onContribute(goal.id, amount)
                    }
                )
            }

            item {
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
private fun GoalsHeader(totalSaved: Double) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        PixMint.copy(alpha = 0.22f),
                        DarkSurfaceCard
                    )
                )
            )
            .border(1.dp, PixMint.copy(alpha = 0.35f), RoundedCornerShape(24.dp))
            .padding(20.dp)
    ) {
        Column {
            Text(
                text = "COFRINHOS & METAS JOVEM",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = PixMint,
                letterSpacing = 1.2.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Total Economizado",
                fontSize = 14.sp,
                color = TextSecondaryDark
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "R$ " + String.format("%.2f", totalSaved),
                fontSize = 30.sp,
                fontWeight = FontWeight.ExtraBold,
                color = TextPrimaryDark,
                letterSpacing = (-0.5).sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "💡 Inspirado no Nexa: aportes inteligentes calculados para não estourar seu teto diário de R$ 50,00.",
                fontSize = 12.sp,
                color = TextSecondaryDark,
                lineHeight = 16.sp
            )
        }
    }
}

@Composable
private fun GoalSquircleCard(
    goal: SavingGoal,
    onContribute: (Double) -> Unit
) {
    val animatedProgress by animateFloatAsState(
        targetValue = goal.progress,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessMediumLow
        ),
        label = "GoalProgressAnimation"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(DarkSurfaceCard)
            .border(1.dp, DarkBorderHairline, RoundedCornerShape(20.dp))
            .padding(18.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(DarkBackground),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = goal.emoji, fontSize = 22.sp)
                    }
                    Column(modifier = Modifier.padding(start = 12.dp)) {
                        Text(
                            text = goal.name,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimaryDark
                        )
                        Text(
                            text = "Prazo: ${goal.deadlineMonth}",
                            fontSize = 12.sp,
                            color = TextSecondaryDark
                        )
                    }
                }

                if (goal.progress >= 1f) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Concluído",
                        tint = PixMint,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Column {
                    Text(
                        text = "R$ " + String.format("%.2f", goal.currentAmount),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = PixMint
                    )
                    Text(
                        text = "Meta: R$ " + String.format("%.2f", goal.targetAmount),
                        fontSize = 12.sp,
                        color = TextSecondaryDark
                    )
                }

                Text(
                    text = "${(goal.progress * 100).toInt()}%",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimaryDark
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            LinearProgressIndicator(
                progress = { animatedProgress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp)),
                color = PixMint,
                trackColor = DarkBackground
            )

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf(10.0, 25.0, 50.0).forEach { amount ->
                    Button(
                        onClick = { onContribute(amount) },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = DarkBackground,
                            contentColor = PixMint
                        ),
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = PaddingValues(vertical = 8.dp)
                    ) {
                        Text(
                            text = "+ R$ ${amount.toInt()}",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

private fun sampleGoals() = listOf(
    SavingGoal(
        name = "Setup Dev / Teclado Mecânico",
        targetAmount = 450.0,
        currentAmount = 280.0,
        emoji = "⌨️",
        deadlineMonth = "Nov 2026"
    ),
    SavingGoal(
        name = "Curso Certificação Android",
        targetAmount = 300.0,
        currentAmount = 210.0,
        emoji = "📱",
        deadlineMonth = "Dez 2026"
    ),
    SavingGoal(
        name = "Reserva de Emergência",
        targetAmount = 1000.0,
        currentAmount = 420.0,
        emoji = "🛡️",
        deadlineMonth = "Jan 2027"
    )
)

@Preview
@Composable
fun GoalsScreenPreview() {
    GoalsScreen()
}
