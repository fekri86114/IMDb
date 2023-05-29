package info.fekri.tmdb.util

sealed class MyScreens(val route: String) {
    object StartScreen : MyScreens("startScreen")
    object MainScreen : MyScreens("mainScreen")
    object DetailScreen : MyScreens("detailScreen")
    object SearchScreen : MyScreens("searchScreen")
    object CategoryScreen : MyScreens("categoryScreen")
}
