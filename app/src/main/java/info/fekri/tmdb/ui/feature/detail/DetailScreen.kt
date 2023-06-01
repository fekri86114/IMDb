package info.fekri.tmdb.ui.feature.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.burnoo.cokoin.navigation.getNavController
import info.fekri.tmdb.ui.theme.Blue
import info.fekri.tmdb.util.MyScreens

@Composable
fun DetailScreen(movieId: Int) {

    val navigation = getNavController()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 16.dp)
    ) {

        DetailTopToolbar(
            onBackIconPressed = {
                navigation.popBackStack()
            },
            onSearchIconPressed = {
                navigation.navigate(MyScreens.SearchScreen.route) {
                    popUpTo(MyScreens.DetailScreen.route)
                }
            }
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = "Movie ID: $movieId",
                style= TextStyle(
                    fontWeight = FontWeight.Medium,
                    color = Color.White,
                    fontSize = 26.sp
                )
            )
        }

    }

}


// ------------------------------------------------------------

@Composable
fun DetailTopToolbar(onBackIconPressed: () -> Unit, onSearchIconPressed: () -> Unit) {
    TopAppBar(
        elevation = 0.dp,
        backgroundColor = Blue,
        navigationIcon = {
            IconButton(onClick = {
                onBackIconPressed.invoke()
            }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null
                )
            }
        },
        title = {
            Text(text = "IMDb Movies")
        },
        actions = {
            IconButton(onClick = {
                onSearchIconPressed.invoke()
            }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null
                )
            }
        }
    )
}
