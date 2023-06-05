package info.fekri.tmdb.ui.feature.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dev.burnoo.cokoin.navigation.getNavController
import info.fekri.tmdb.ui.theme.Blue
import info.fekri.tmdb.util.MyScreens

@Composable
fun SearchScreen() {
    val context = LocalContext.current
    val navigation = getNavController()
    val uiController = rememberSystemUiController()
    SideEffect { uiController.setStatusBarColor(Blue) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 16.dp)
    ) {

        SearchTopToolbar {
            navigation.navigate(MyScreens.MainScreen.route) {
                popUpTo(MyScreens.MainScreen.route) {
                    inclusive = true
                }
            }
        }

        // will complete later

    }

}

@Composable
fun SearchTopToolbar(onHomeIconPressed: () -> Unit) {
    TopAppBar(
        elevation = 0.dp,
        backgroundColor = Blue,
        actions = {
                  IconButton(onClick = onHomeIconPressed) {
                      Icon(imageVector = Icons.Default.Home, contentDescription = null)
                  }
        },
        title = {
            Text(text = "IMDb Search")
        }
    )
}