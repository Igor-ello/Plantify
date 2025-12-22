package com.greencore.plantify.ui.app


import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.greencore.plantify.ui.componets.scaffold.AppScaffold
import com.greencore.plantify.ui.navigation.AppNavHost

@Composable
fun App() {
    val navController = rememberNavController()

    AppScaffold(
        navController = navController
    ) { innerPadding ->
        AppNavHost(
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}