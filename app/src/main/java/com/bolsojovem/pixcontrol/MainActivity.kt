package com.bolsojovem.pixcontrol

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Crossfade
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bolsojovem.pixcontrol.ui.components.NavItem
import com.bolsojovem.pixcontrol.ui.screens.BolsoJovemDashboardScreen
import com.bolsojovem.pixcontrol.ui.screens.GoalsScreen
import com.bolsojovem.pixcontrol.ui.theme.BolsoJovemTheme
import com.bolsojovem.pixcontrol.viewmodel.PixControlViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // Fiel ao design oficial do Google Stitch (Light Theme Neobank)
            BolsoJovemTheme(darkTheme = false) {
                var currentTab by remember { mutableStateOf<NavItem>(NavItem.Home) }
                val viewModel: PixControlViewModel = viewModel()

                Crossfade(targetState = currentTab, label = "TabCrossfade") { tab ->
                    when (tab) {
                        NavItem.Home -> BolsoJovemDashboardScreen(
                            viewModel = viewModel,
                            onNavigateTab = { currentTab = it }
                        )
                        NavItem.Goals -> GoalsScreen(
                            onNavigateTab = { currentTab = it }
                        )
                        else -> BolsoJovemDashboardScreen(
                            viewModel = viewModel,
                            onNavigateTab = { currentTab = it }
                        )
                    }
                }
            }
        }
    }
}
