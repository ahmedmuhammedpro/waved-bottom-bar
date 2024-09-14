package com.ahmedpro.wavedbottombar.ui.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ahmedpro.wavedbottombar.BottomBarItem

@Composable
fun AnimatedBottomBar(
    modifier: Modifier = Modifier,
    items: List<BottomBarItem>,
    selectedTabIndex: Int,
    onTabClicked: (index: Int) -> Unit
) {

    val waveHeightInDp by remember { mutableStateOf(20.dp) }
    var screenWidthInPx by remember { mutableFloatStateOf(0F) }
    val waveOffsetX = remember { Animatable(0F) }

    LaunchedEffect(screenWidthInPx, selectedTabIndex) {
        if (screenWidthInPx == 0F) return@LaunchedEffect

        val tabWidth = screenWidthInPx / items.size
        val waveWidth = screenWidthInPx * 0.28F
        val targetOffset = selectedTabIndex * tabWidth - (waveWidth - tabWidth) / 2


        waveOffsetX.animateTo(
            targetValue = targetOffset,
            animationSpec = tween(durationMillis = 500)
        )
    }
    Column(modifier = modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .drawBehind {
                    drawShadow(waveStartX = waveOffsetX.value)
                }
        ) {
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(waveHeightInDp)
            ) {
                screenWidthInPx = size.width
                val waveWidth = size.width * 0.28F
                val waveHeight = size.height
                val startX = waveOffsetX.value

                val wavePath = Path().apply {
                    moveTo(startX, waveHeight)

                    cubicTo(
                        x1 = startX + waveWidth * 0.25f, y1 = waveHeight,
                        x2 = startX + waveWidth * 0.25f, y2 = 0f,
                        x3 = startX + waveWidth * 0.5f, y3 = 0f
                    )

                    cubicTo(
                        x1 = startX + waveWidth * 0.75f, y1 = 0f,
                        x2 = startX + waveWidth * 0.75f, y2 = waveHeight,
                        x3 = startX + waveWidth, y3 = waveHeight
                    )
                    close()
                }

                drawPath(
                    path = wavePath,
                    color = Color.White
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.White),
        ) {
            items.forEachIndexed { index, item ->
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onTabClicked(index) }
                        .padding(vertical = 16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painter = painterResource(id = item.icon),
                        tint = if (selectedTabIndex == index) Color(0xFFC56648) else Color.Unspecified,
                        contentDescription = item.title
                    )
                    Text(
                        modifier = Modifier.padding(top = 8.dp),
                        text = item.title,
                        color = if (selectedTabIndex == index) Color(0xFFC56648) else MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}

fun DrawScope.drawShadow(waveStartX: Float) {
    val waveWidth = size.width * 0.28F
    val waveHeight = size.height
    val shadowColor = Color.Black.copy(alpha = 0.04f)
    val shadowWidth = 8.dp.toPx()

    // Draw shadow on top of the rectangle before the wave
    drawLine(
        color = shadowColor,
        start = Offset(0f, waveHeight),
        end = Offset(waveStartX, waveHeight),
        strokeWidth = shadowWidth
    )

    // Draw shadow around the wave
    val wavePath = Path().apply {
        moveTo(waveStartX, waveHeight)
        cubicTo(
            x1 = waveStartX + waveWidth * 0.25f, y1 = waveHeight,
            x2 = waveStartX + waveWidth * 0.25f, y2 = 0f,
            x3 = waveStartX + waveWidth * 0.5f, y3 = 0f
        )
        cubicTo(
            x1 = waveStartX + waveWidth * 0.75f, y1 = 0f,
            x2 = waveStartX + waveWidth * 0.75f, y2 = waveHeight,
            x3 = waveStartX + waveWidth, y3 = waveHeight
        )
    }
    drawPath(
        path = wavePath,
        color = shadowColor,
        style = Stroke(width = shadowWidth)
    )

    // Draw shadow on top of the rectangle after the wave
    drawLine(
        color = shadowColor,
        start = Offset(waveStartX + waveWidth, waveHeight),
        end = Offset(size.width, waveHeight),
        strokeWidth = shadowWidth
    )
}