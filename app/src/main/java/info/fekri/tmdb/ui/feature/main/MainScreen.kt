package info.fekri.tmdb.ui.feature.main

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dev.burnoo.cokoin.navigation.getNavController
import dev.burnoo.cokoin.navigation.getNavViewModel
import info.fekri.tmdb.R
import info.fekri.tmdb.model.data.QueryResult
import info.fekri.tmdb.ui.theme.Blue
import info.fekri.tmdb.ui.theme.CoverBlue
import info.fekri.tmdb.ui.theme.Shapes
import info.fekri.tmdb.ui.theme.WhiteCover
import info.fekri.tmdb.util.MyScreens
import info.fekri.tmdb.util.NetworkChecker
import info.fekri.tmdb.util.POSTER_BASE_URL
import info.fekri.tmdb.util.styleLimitText
import org.koin.core.parameter.parametersOf

@Composable
fun MainScreen() {
    val context = LocalContext.current
    val navigation = getNavController()
    val uiController = rememberSystemUiController()
    SideEffect { uiController.setStatusBarColor(Blue) }
    val viewModel =
        getNavViewModel<MainViewModel>(parameters = { parametersOf(NetworkChecker(context).isInternetConnected) })

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 16.dp)
    ) {
        if (viewModel.showProgress.value) LinearProgressIndicator(
            modifier = Modifier.fillMaxWidth(), color = Blue
        )

        MainTopToolbar {
            navigation.navigate(MyScreens.SearchScreen.route)
        }

        Column(
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(
                text = "Actions",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = WhiteCover,
                modifier = Modifier.padding(start = 8.dp)
            )
            MovieActionItemBar(viewModel.actionMovies.value) { id ->
                if (NetworkChecker(context).isInternetConnected) {

                    navigation.navigate(MyScreens.DetailScreen.route + "/" + id)

                } else {
                    Toast.makeText(
                        context,
                        "Please, check your Internet Connection!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

    }

}

@Composable
fun MovieActionItemBar(data: List<QueryResult>, onMovieItemClicked: (Int) -> Unit) {

    LazyRow(
        modifier = Modifier.padding(top = 16.dp, start = 0.dp),
        contentPadding = PaddingValues(horizontal = 8.dp)
    ) {

        items(data.size) {
            MovieActionItem(data[it], onMovieItemClicked)
        }

    }

}

@Composable
fun MovieActionItem(data: QueryResult, onMovieItemClicked: (Int) -> Unit) {

    Card(
        modifier = Modifier
            .padding(start = 8.dp)
            .clickable {
                onMovieItemClicked.invoke(data.id)
            },
        elevation = 4.dp,
        shape = Shapes.medium,
        backgroundColor = CoverBlue
    ) {

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            AsyncImage(
                model = POSTER_BASE_URL + data.posterPath,
                contentDescription = null,
                modifier = Modifier.size(180.dp),
                contentScale = ContentScale.Crop,
                alignment = Alignment.TopCenter
            )
            Column(
                modifier = Modifier.padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = styleLimitText(data.title, 10),
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White
                    ),
                    maxLines = 1
                )
                Text(
                    text = data.releaseDate,
                    style = TextStyle(fontSize = 14.sp, color = WhiteCover),
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }

    }

}

// -------------------------------------------------

@Composable
fun MainTopToolbar(onSearchClicked: () -> Unit) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current

    TopAppBar(
        elevation = 0.dp,
        backgroundColor = Blue,
        navigationIcon = {
            Icon(
                painter = painterResource(
                    id = R.drawable.ic_launcher_foreground
                ),
                contentDescription = null
            )
        },
        title = {
            Text(
                text = "IMDb Movies"
            )
        },
        actions = {

            IconButton(onClick = {

                if (NetworkChecker(context).isInternetConnected) {
                    onSearchClicked.invoke()
                } else {
                    Toast.makeText(
                        context,
                        "Please, check your Internet Connection!",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }) {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search movie")
            }

        }
    )
}

