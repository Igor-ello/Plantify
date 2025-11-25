package com.example.myplants

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.myplants.core.ui.system.configureSystemBars
import com.example.myplants.core.ui.theme.MyPlantsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configureSystemBars()

        setContent {
            MyPlantsTheme {
                AppRoot()
            }
        }
    }
}
