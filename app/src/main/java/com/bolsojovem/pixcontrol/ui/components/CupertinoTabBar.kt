package com.bolsojovem.pixcontrol.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.TrackChanges
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bolsojovem.pixcontrol.ui.theme.StitchPrimary
import com.bolsojovem.pixcontrol.ui.theme.StitchPrimaryContainer

sealed class NavItem(val title: String, val icon: ImageVector) {
    object Home : NavItem("Início", Icons.Default.Home)
    object Statement : NavItem("Extrato", Icons.Default.ReceiptLong)
    object Goals : NavItem("Metas", Icons.Default.TrackChanges)
    object Profile : NavItem("Perfil", Icons.Default.Person)
}

@Composable
fun CupertinoTabBar(
    selectedItem: NavItem = NavItem.Home,
    onItemSelected: (NavItem) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val items = listOf(NavItem.Home, NavItem.Statement, NavItem.Goals, NavItem.Profile)
    val isDark = MaterialTheme.colorScheme.background.red < 0.5f

    val navBg = if (isDark) Color(0xFF161F30).copy(alpha = 0.95f) else Color(0xFFFFFFFF).copy(alpha = 0.95f)
    val navBorder = if (isDark) Color.White.copy(alpha = 0.08f) else Color(0xFFBACAC1).copy(alpha = 0.35f)

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)),
        color = navBg,
        shadowElevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .height(64.dp)
                .padding(horizontal = 12.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEach { item ->
                val isSelected = item == selectedItem
                val activeBg = if (isSelected) {
                    StitchPrimaryContainer.copy(alpha = 0.22f)
                } else {
                    Color.Transparent
                }
                val activeColor = if (isSelected) {
                    if (isDark) StitchPrimaryContainer else StitchPrimary
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(14.dp))
                        .background(activeBg)
                        .clickable { onItemSelected(item) }
                        .padding(horizontal = 16.dp, vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.title,
                            tint = activeColor,
                            modifier = Modifier.size(22.dp)
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = item.title,
                            color = activeColor,
                            fontSize = 11.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}
