package info.fekri.tmdb.ui

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dev.burnoo.cokoin.Koin
import dev.burnoo.cokoin.navigation.KoinNavHost
import info.fekri.tmdb.di.myModules
import info.fekri.tmdb.ui.feature.category.CategoryScreen
import info.fekri.tmdb.ui.feature.detail.DetailScreen
import info.fekri.tmdb.ui.feature.main.MainScreen
import info.fekri.tmdb.ui.feature.search.SearchScreen
import info.fekri.tmdb.ui.feature.start.StartScreen
import info.fekri.tmdb.ui.theme.BackgroundMain
import info.fekri.tmdb.ui.theme.MainAppTheme
import info.fekri.tmdb.util.KEY_MOVIE_ARG
import info.fekri.tmdb.util.KEY_MOVIE_CATEGORY
import info.fekri.tmdb.util.MyScreens
import org.koin.android.ext.koin.androidContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.layoutDirection = View.LAYOUT_DIRECTION_LTR
        setContent {

            Koin(appDeclaration = {
                androidContext(this@MainActivity)
                modules(myModules)
            }) {
                MainAppTheme {
                    Surface(color = BackgroundMain, modifier = Modifier.fillMaxSize()) {
                        IMDbUi()
                    }
                }
            }

        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainPreview() {
    MainAppTheme {
        Surface(color = BackgroundMain, modifier = Modifier.fillMaxSize()) {
            IMDbUi()
        }
    }
}

@Composable
fun IMDbUi() {
    val navController = rememberNavController()
    val context = LocalContext.current
    KoinNavHost(navController = navController, startDestination = MyScreens.StartScreen.route) {
        composable(route = MyScreens.StartScreen.route) {
            StartScreen()
        }
        composable(route = MyScreens.MainScreen.route) {
            MainScreen()
        }
        composable(
            route = MyScreens.CategoryScreen.route + "/{$KEY_MOVIE_CATEGORY}",
            arguments = listOf(navArgument(KEY_MOVIE_CATEGORY) {
                type = NavType.StringType
            })
        ) {
            CategoryScreen(it.arguments!!.getString(KEY_MOVIE_CATEGORY, "null"))
        }
        composable(
            route = MyScreens.DetailScreen.route + "/{$KEY_MOVIE_ARG}",
            arguments = listOf(navArgument(KEY_MOVIE_ARG) {
                type = NavType.IntType
            })
        ) {
            DetailScreen(it.arguments!!.getInt(KEY_MOVIE_ARG, -1))
        }
        composable(route = MyScreens.SearchScreen.route) {
            SearchScreen()
        }
    }
}
