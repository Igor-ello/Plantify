package com.example.myplants.ui.componets.drawer

import androidx.annotation.StringRes
import androidx.compose.material3.DrawerState
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.myplants.core.domain.common.Routes
import com.example.myplants.core.ui.theme.OnPrimaryWhite
import com.example.myplants.core.ui.theme.PrimaryGreen
import com.example.myplants.ui.componets.base.BodyLarge
import com.example.myplants.ui.navigation.navigateSingleTop
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


data class DrawerItemData(
    @StringRes val labelRes: Int,
    val route: String,
    val icon: (@Composable (() -> Unit))? = null
)

@Composable
fun DrawerItem(
    item: DrawerItemData,
    currentRoute: String,
    navController: NavController,
    drawerState: DrawerState,
    scope: CoroutineScope
) {
    val selected = currentRoute.startsWith(item.route)
    NavigationDrawerItem(
        label = { BodyLarge(text = stringResource(item.labelRes), color = OnPrimaryWhite) },
        icon = item.icon,
        selected = selected,
        onClick = {
            scope.launch { drawerState.close() }
            navController.navigateSingleTop(item.route, Routes.AllPlants.route)
        },
        colors = NavigationDrawerItemDefaults.colors(
            unselectedContainerColor = PrimaryGreen,
            selectedContainerColor = OnPrimaryWhite.copy(alpha = 0.2f),
            unselectedIconColor = OnPrimaryWhite,
            selectedIconColor = OnPrimaryWhite,
            unselectedTextColor = OnPrimaryWhite,
            selectedTextColor = OnPrimaryWhite
        )
    )
}
