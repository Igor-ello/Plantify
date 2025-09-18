package com.example.myplants.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.myplants.ui.theme.MyPlantsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            MyPlantsTheme {
                PlantsApp()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlantScreenPreview() {
    MyPlantsTheme {
        // Предварительный просмотр без ViewModel
        //PlantScreen(viewModel = PlantsViewModel(dao = /* передайте здесь dao для превью */))
    }
}
