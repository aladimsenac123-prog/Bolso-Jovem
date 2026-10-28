package com.bolsojovem.pixcontrol.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bolsojovem.pixcontrol.ui.theme.DarkBorderHairline
import com.bolsojovem.pixcontrol.ui.theme.DarkSurfaceCard
import com.bolsojovem.pixcontrol.ui.theme.PixMint
import com.bolsojovem.pixcontrol.ui.theme.TextSecondaryDark

sealed class NavItem(val title: String, val icon: ImageVector) {
    object Home : NavItem("Início", Icons.Default.Home)
    object Statement : NavItem("Extrato", Icons.Default.AccountBalanceWallet)
    object Goals : NavItem("Metas", Icons.Default.Flag)
    object Profile : NavItem("Perfil", Icons.Default.Person)
}

@Composable
fun CupertinoTabBar(
    selectedItem: NavItem = NavItem.Home,
    onItemSelected: (NavItem) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val items = listOf(NavItem.Home, NavItem.Statement, NavItem.Goals, NavItem.Profile)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
            .background(DarkSurfaceCard.copy(alpha = 0.95f))
            .border(
                width = 1.dp,
                color = DarkBorderHairline,
                shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
            )
            .height(64.dp)
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEach { item ->
                val isSelected = item == selectedItem
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .clickable { onItemSelected(item) }
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title,
                        tint = if (isSelected) PixMint else TextSecondaryDark,
                        modifier = Modifier.size(22.dp)
                    )
                    Text(
                        text = item.title,
                        color = if (isSelected) PixMint else TextSecondaryDark,
                        fontSize = 11.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
                }
            }
        }
    }
}
