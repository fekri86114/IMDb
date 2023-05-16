package info.fekri.tmdb.ui

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
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import info.fekri.tmdb.ui.theme.MainAppTheme
import dev.burnoo.cokoin.Koin
import dev.burnoo.cokoin.navigation.KoinNavHost
import info.fekri.tmdb.di.myModules
import info.fekri.tmdb.ui.features.StartScreen
import info.fekri.tmdb.ui.theme.BackgroundMain
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
    val context= LocalContext.current
    KoinNavHost(navController = navController, startDestination = MyScreens.StartScreen.route) {
        composable(route = MyScreens.StartScreen.route) {
            StartScreen()
        }
        composable(route = MyScreens.MainScreen.route) {
            MainScreen()
        }
        composable(route = MyScreens.DetailScreen.route) {
            DetailScreen()
        }
        composable(route = MyScreens.SearchScreen.route) {
            SearchScreen()
        }
    }
}

@Composable
fun SearchScreen() {

}

@Composable
fun MainScreen() {

}


@Composable
fun DetailScreen() {

}
