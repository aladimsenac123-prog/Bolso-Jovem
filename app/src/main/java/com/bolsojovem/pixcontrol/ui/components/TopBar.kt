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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.bolsojovem.pixcontrol.ui.theme.DarkSurfaceInput
import com.bolsojovem.pixcontrol.ui.theme.PixMint
import com.bolsojovem.pixcontrol.ui.theme.TextPrimaryDark
import com.bolsojovem.pixcontrol.ui.theme.TextSecondaryDark

@Composable
fun TopBar(
    userName: String = "Carlos Dev",
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // User profile and brand tag
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(DarkSurfaceInput)
                    .border(2.dp, PixMint, CircleShape)
            ) {
                Text(
                    text = "CD",
                    fontWeight = FontWeight.Bold,
                    color = PixMint,
                    fontSize = 15.sp
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Bolso Jovem",
                        style = MaterialTheme.typography.titleLarge,
                        color = TextPrimaryDark,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(PixMint.copy(alpha = 0.2f))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Bolt,
                                contentDescription = "Pix",
                                tint = PixMint,
                                modifier = Modifier.size(12.dp)
                            )
                            Spacer(modifier = Modifier.width(2.dp))
                            Text(
                                text = "PIX",
                                color = PixMint,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.ExtraBold
                            )
                        }
                    }
                }
                Text(
                    text = "Olá, $userName • Chave Pix Ativa",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondaryDark
                )
            }
        }

        // Action icon
        IconButton(
            onClick = { /* Notificações */ },
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(DarkSurfaceInput)
        ) {
            Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = "Notificações",
                tint = TextPrimaryDark,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}
