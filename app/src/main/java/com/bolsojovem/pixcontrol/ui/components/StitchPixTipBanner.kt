package com.bolsojovem.pixcontrol.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bolsojovem.pixcontrol.ui.theme.DarkBorderHairline
import com.bolsojovem.pixcontrol.ui.theme.DarkSurfaceCard
import com.bolsojovem.pixcontrol.ui.theme.StitchPrimaryContainer
import com.bolsojovem.pixcontrol.ui.theme.TextPrimaryDark
import com.bolsojovem.pixcontrol.ui.theme.TextSecondaryDark

/**
 * Banner de Dica Pix e Gamificação fiel ao Google Stitch
 * (Screen ID: 6e063e69b5844fd6a2615c80868530f7)
 */
@Composable
fun StitchPixTipBanner(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(DarkSurfaceCard)
            .border(1.dp, DarkBorderHairline, RoundedCornerShape(18.dp))
            .padding(14.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Icon Badge
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(38.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(StitchPrimaryContainer.copy(alpha = 0.22f))
                    .border(1.dp, StitchPrimaryContainer.copy(alpha = 0.4f), RoundedCornerShape(12.dp))
            ) {
                Icon(
                    imageVector = Icons.Default.Bolt,
                    contentDescription = null,
                    tint = StitchPrimaryContainer,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "DICA PIX & GAMIFICAÇÃO",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = StitchPrimaryContainer,
                        letterSpacing = 0.8.sp,
                        modifier = Modifier.weight(1f)
                    )
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(StitchPrimaryContainer.copy(alpha = 0.25f))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "+50 pts",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = StitchPrimaryContainer
                        )
                    }
                }

                Spacer(modifier = Modifier.height(3.dp))

                val tipText = buildAnnotatedString {
                    append("Pagando via QR Code ou Chave Pix direta você acumula ")
                    withStyle(SpanStyle(fontWeight = FontWeight.Bold, color = TextPrimaryDark)) {
                        append("pontos de meta")
                    }
                    append(" diária para desbloquear novos limites!")
                }

                Text(
                    text = tipText,
                    fontSize = 12.sp,
                    color = TextSecondaryDark,
                    lineHeight = 16.sp
                )
            }
        }
    }
}
