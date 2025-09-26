package com.example.myplants.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.myplants.ui.theme.MyPlantsTheme

class MainActivity : ComponentActivity() {

    private lateinit var appContainer: AppContainer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        appContainer = AppContainer(applicationContext)

        enableEdgeToEdge()
        setContent {
            MyPlantsTheme {
                PlantsApp(appContainer)
            }
        }
    }
}
