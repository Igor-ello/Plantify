package com.example.myplants.ui.navigation.navigators

data class MainScreenNavigator(
    val toPlantDetail: (Long) -> Unit,
    val toGenusDetail: (Long) -> Unit,
    val toAddPlant: () -> Unit
)
