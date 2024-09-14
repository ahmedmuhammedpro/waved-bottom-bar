package com.ahmedpro.wavedbottombar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.ahmedpro.wavedbottombar.ui.component.AnimatedBottomBar
import com.ahmedpro.wavedbottombar.ui.theme.WavedBottomBarTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(
                Color.Transparent.toArgb(),
                Color.Transparent.toArgb(),
            ) { false },
            navigationBarStyle = SystemBarStyle.auto(
                Color.Transparent.toArgb(),
                Color.Transparent.toArgb(),
            ) { false }
        )
        setContent {
            WavedBottomBarTheme {
                val bottomBarItems = remember {
                    mutableListOf(
                        BottomBarItem(icon = R.drawable.ic_profile, title = "profile"),
                        BottomBarItem(icon = R.drawable.ic_cloud, title = "cloud"),
                        BottomBarItem(icon = R.drawable.ic_add, title = "add"),
                        BottomBarItem(icon = R.drawable.ic_notifications, title = "notifications"),
                        BottomBarItem(icon = R.drawable.ic_settings, title = "settings")
                    )
                }
                var selectedTabIndex by remember { mutableIntStateOf(2) }
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        AnimatedBottomBar(
                            modifier = Modifier
                                .background(
                                    Brush.verticalGradient(
                                        colors = listOf(
                                            Color.Transparent,
                                            Color.Transparent,
                                            Color.White,
                                            Color.White
                                        ),
                                        startY = 0f
                                    )
                                )
                                .windowInsetsPadding(WindowInsets.safeDrawing.only(WindowInsetsSides.Bottom)),
                            items = bottomBarItems,
                            selectedTabIndex = selectedTabIndex,
                            onTabClicked = { selectedTabIndex = it }
                        )
                    },
                    containerColor = Color.White
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                    ) {

                    }
                }
            }
        }
    }
}