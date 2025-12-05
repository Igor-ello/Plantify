package com.example.myplants.ui.componets.drawer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.myplants.R
import com.example.myplants.core.domain.common.Routes
import com.example.myplants.core.ui.theme.OnPrimaryWhite
import com.example.myplants.core.ui.theme.PrimaryGreen
import com.example.myplants.ui.componets.base.TitleLarge
import com.example.myplants.ui.componets.base.TitleMedium
import com.example.myplants.ui.componets.icons.AppIcon
import com.example.myplants.ui.componets.icons.AppIcons
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationDrawerContent(
    navController: NavController,
    drawerState: DrawerState,
    scope: CoroutineScope
) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route ?: ""

    val drawerItems = listOf(
        DrawerItemData(
            R.string.screen_all_plants, Routes.AllPlants.route,
            icon = { AppIcon(icon = AppIcons.AllPlantsNav) }
        ),
        DrawerItemData(
            R.string.screen_favorites, Routes.Favorites.route,
            icon = { AppIcon(icon = AppIcons.FavouriteNav) }
        ),
        DrawerItemData(
            R.string.screen_wishlist, Routes.Wishlist.route,
            icon = { AppIcon(icon = AppIcons.WishlistNav) }
        ),
        DrawerItemData(
            R.string.screen_settings, Routes.Settings.route,
            icon = { AppIcon(icon = AppIcons.SettingsNav) }
        ),
        DrawerItemData(
            R.string.screen_help_feedback, Routes.Help.route,
            icon = { AppIcon(icon = AppIcons.InfoNav) }
        )
    )

    ModalDrawerSheet(
        modifier = Modifier.fillMaxHeight(),
        drawerContainerColor = PrimaryGreen,
        drawerContentColor = OnPrimaryWhite
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 12.dp)) {

            TitleLarge(
                text = "MyPlants",
                color = OnPrimaryWhite,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )

            HorizontalDivider(thickness = 1.dp, color = OnPrimaryWhite.copy(alpha = 0.3f))

            // Раздел "Растения"
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                item {
                    TitleMedium(
                        text = stringResource(R.string.plants_section),
                        color = OnPrimaryWhite,
                        modifier = Modifier.padding(16.dp)
                    )
                }
                items(drawerItems.filter {
                    it.route in listOf(Routes.AllPlants.route, Routes.Favorites.route, Routes.Wishlist.route)
                }) { item ->
                    DrawerItem(item, currentRoute, navController, drawerState, scope)
                }
            }

            HorizontalDivider(thickness = 1.dp, color = OnPrimaryWhite.copy(alpha = 0.3f))

            // Раздел "Прочее"
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                item {
                    TitleMedium(
                        text = stringResource(R.string.other_section),
                        color = OnPrimaryWhite,
                        modifier = Modifier.padding(16.dp)
                    )
                }
                items(drawerItems.filter {
                    it.route in listOf(Routes.Settings.route, Routes.Help.route)
                }) { item ->
                    DrawerItem(item, currentRoute, navController, drawerState, scope)
                }
            }
        }
    }
}
