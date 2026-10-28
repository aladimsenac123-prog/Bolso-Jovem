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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.LocalAtm
import androidx.compose.material.icons.filled.PhoneAndroid
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bolsojovem.pixcontrol.data.model.ExpenseCategory
import com.bolsojovem.pixcontrol.data.model.Transaction
import com.bolsojovem.pixcontrol.ui.theme.AlertCrimson
import com.bolsojovem.pixcontrol.ui.theme.DarkBorderHairline
import com.bolsojovem.pixcontrol.ui.theme.DarkSurfaceCard
import com.bolsojovem.pixcontrol.ui.theme.DarkSurfaceInput
import com.bolsojovem.pixcontrol.ui.theme.PixMint
import com.bolsojovem.pixcontrol.ui.theme.TextPrimaryDark
import com.bolsojovem.pixcontrol.ui.theme.TextSecondaryDark

import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import java.time.format.DateTimeFormatter

@Composable
fun TransactionItemCard(
    transaction: Transaction,
    onDelete: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val haptic = LocalHapticFeedback.current
    val timeFormatter = remember { DateTimeFormatter.ofPattern("HH:mm") }
    val formattedTime = remember(transaction.timestamp) {
        transaction.timestamp.format(timeFormatter)
    }

    val categoryIcon: ImageVector = when (transaction.category) {
        ExpenseCategory.FOOD -> Icons.Default.Fastfood
        ExpenseCategory.TRANSPORT -> Icons.Default.LocalAtm
        ExpenseCategory.LEISURE -> Icons.Default.SportsEsports
        ExpenseCategory.STUDIES, ExpenseCategory.EDUCATION -> Icons.Default.School
        ExpenseCategory.SERVICES, ExpenseCategory.PIX_TRANSFER -> Icons.Default.PhoneAndroid
        else -> Icons.Default.LocalAtm
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(DarkSurfaceCard)
            .border(1.dp, DarkBorderHairline, RoundedCornerShape(16.dp))
            .padding(14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(DarkSurfaceInput),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = categoryIcon,
                    contentDescription = transaction.category.displayName,
                    tint = PixMint,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = transaction.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = TextPrimaryDark,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "${transaction.category.displayName} • $formattedTime",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondaryDark
                )
            }
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "- ${transaction.formattedAmount}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = AlertCrimson
            )

            Spacer(modifier = Modifier.width(4.dp))

            IconButton(
                onClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    onDelete(transaction.id)
                },
                modifier = Modifier.size(32.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.DeleteOutline,
                    contentDescription = "Excluir transação",
                    tint = TextSecondaryDark.copy(alpha = 0.7f),
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}
