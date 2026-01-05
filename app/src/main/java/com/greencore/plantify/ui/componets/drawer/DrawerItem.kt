package com.greencore.plantify.ui.componets.drawer

import androidx.annotation.StringRes
import androidx.compose.material3.DrawerState
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.greencore.plantify.core.domain.Routes
import com.greencore.plantify.core.ui.theme.OnPrimaryWhite
import com.greencore.plantify.core.ui.theme.PrimaryGreen
import com.greencore.plantify.ui.componets.base.BodyLarge
import com.greencore.plantify.ui.navigation.navigateSingleTop
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


data class DrawerItemData(
    @param:StringRes val labelRes: Int,
    val route: String,
    val icon: (@Composable () -> Unit)? = null
)

@Composable
fun DrawerItem(
    item: DrawerItemData,
    currentRoute: String,
    navController: NavController,
    drawerState: DrawerState,
    scope: CoroutineScope
) {
    NavigationDrawerItem(
        label = { BodyLarge(text = stringResource(item.labelRes), color = OnPrimaryWhite) },
        icon = item.icon,
        selected = currentRoute.equals(item.route),
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
