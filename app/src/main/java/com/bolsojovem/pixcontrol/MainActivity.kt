package com.bolsojovem.pixcontrol

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.bolsojovem.pixcontrol.ui.screens.BolsoJovemDashboardScreen
import com.bolsojovem.pixcontrol.ui.theme.BolsoJovemTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BolsoJovemTheme(darkTheme = true) {
                BolsoJovemDashboardScreen()
            }
        }
    }
}
