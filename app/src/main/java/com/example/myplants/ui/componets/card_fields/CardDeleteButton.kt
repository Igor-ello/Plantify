package com.example.myplants.ui.componets.card_fields

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun CardDeleteButton(
    onDeleteConfirmed: () -> Unit,
    modifier: Modifier = Modifier
) {
    var pressProgress by remember { mutableStateOf(0f) }
    var showDeleteConfirm by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.Red.copy(alpha = 0.2f))
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        pressProgress = 0f
                        showDeleteConfirm = true
                        val job = scope.launch {
                            val steps = 50
                            repeat(steps) { i ->
                                pressProgress = (i + 1) / steps.toFloat()
                                delay(100) // 50 * 100ms = 5 секунд
                            }
                            onDeleteConfirmed()
                            showDeleteConfirm = false
                        }
                        tryAwaitRelease() // ожидаем отпускания
                        job.cancel()
                        pressProgress = 0f
                        showDeleteConfirm = false
                    }
                )
            },
        contentAlignment = Alignment.Center
    ) {
        if (showDeleteConfirm) {
            LinearProgressIndicator(
            progress = { pressProgress },
            modifier = Modifier
                                .fillMaxWidth()
                                .height(4.dp)
                                .align(Alignment.TopCenter),
            color = Color.Red,
            trackColor = ProgressIndicatorDefaults.linearTrackColor,
            strokeCap = ProgressIndicatorDefaults.LinearStrokeCap,
            )
        }

        Text(
            text = if (showDeleteConfirm) "Hold 5s to delete" else "Delete Plant",
            color = Color.Red,
            fontWeight = FontWeight.Bold
        )
    }
}
