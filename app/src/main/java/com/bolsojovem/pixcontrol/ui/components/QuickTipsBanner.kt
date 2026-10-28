package com.bolsojovem.pixcontrol.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.TipsAndUpdates
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bolsojovem.pixcontrol.ui.theme.StitchPrimary
import com.bolsojovem.pixcontrol.ui.theme.StitchPrimaryContainer

@Composable
fun QuickTipsBanner(
    modifier: Modifier = Modifier
) {
    val isDark = MaterialTheme.colorScheme.background.red < 0.5f

    val bg = if (isDark) Color(0xFF161F30) else Color(0xFFEAEDFF).copy(alpha = 0.6f)
    val border = if (isDark) Color.White.copy(alpha = 0.08f) else Color(0xFFBACAC1).copy(alpha = 0.3f)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(bg)
            .border(1.dp, border, RoundedCornerShape(16.dp))
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(34.dp)
                .clip(CircleShape)
                .background(StitchPrimaryContainer.copy(alpha = 0.22f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.TipsAndUpdates,
                contentDescription = null,
                tint = if (isDark) StitchPrimaryContainer else StitchPrimary,
                modifier = Modifier.size(18.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = "Dica Pix: Pagando direto no QR Code do cardápio você acumula pontos de meta diária!",
            fontSize = 12.sp,
            lineHeight = 16.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
