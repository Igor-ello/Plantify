package com.example.myplants

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.example.myplants.db.AppDatabase
import com.example.myplants.plants.PlantsViewModel
import com.example.myplants.plants.PlantsViewModelFactory
import com.example.myplants.ui.plant_card.PlantCard
import com.example.myplants.ui.theme.MyPlantsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dao = AppDatabase.getInstance(this.application).plantDao
        val viewModelFactory = PlantsViewModelFactory(dao)
        val viewModel = ViewModelProvider(this, viewModelFactory)[PlantsViewModel::class.java]

        enableEdgeToEdge()
        setContent {
            MyPlantsTheme {
                NavigationDrawer { innerPadding ->
                    PlantScreen(viewModel = viewModel, modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun PlantScreen(viewModel: PlantsViewModel, modifier: Modifier = Modifier) {
    val plants by viewModel.plants.observeAsState(emptyList())

    LazyColumn(modifier = modifier.padding(16.dp)) {
        items(plants) { plant ->
            PlantCard(
                plant = plant,
                editable = false,
                onValueChange = {}
            )
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
