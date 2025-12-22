package com.greencore.plantify

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.greencore.plantify.core.ui.system.configureSystemBars
import com.greencore.plantify.core.ui.theme.AppTheme
import com.greencore.plantify.ui.app.App
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configureSystemBars()

        setContent {
            AppTheme {
                App()
            }
        }
    }
}
