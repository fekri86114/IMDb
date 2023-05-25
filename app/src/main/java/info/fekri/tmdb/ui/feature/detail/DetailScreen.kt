package info.fekri.tmdb.ui.feature.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import dev.burnoo.cokoin.navigation.getNavController
import dev.burnoo.cokoin.navigation.getNavViewModel
import info.fekri.tmdb.R
import info.fekri.tmdb.model.data.MovieById
import info.fekri.tmdb.ui.theme.Blue
import info.fekri.tmdb.ui.theme.CoverBlue
import info.fekri.tmdb.ui.theme.Shapes
import info.fekri.tmdb.ui.theme.WhiteCover
import info.fekri.tmdb.util.MyScreens
import info.fekri.tmdb.util.POSTER_BASE_URL

@Composable
fun DetailScreen(movieId: Int) {
    val viewModel = getNavViewModel<DetailViewModel>()
    viewModel.loadData(movieId)

    val navigation = getNavController()

    Column(
        modifier = Modifier.fillMaxSize()
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

        if (viewModel.showProgress.value)
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth(), color = WhiteCover)
        else DetailScreenBar(viewModel.thisMovie.value)

    }

}

@Composable
fun DetailScreenBar(data: MovieById) {

    Column(
        modifier = Modifier
            .padding(top = 8.dp, bottom = 8.dp, end = 8.dp, start = 8.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Card(
            shape = Shapes.medium,

            ) {
            AsyncImage(
                model = POSTER_BASE_URL + data.backdropPath,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(200.dp, 300.dp)
            )
        }

        Surface(
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth(0.7f),
            shape = Shapes.medium,
            color = WhiteCover,
        ) {
            Text(
                text = data.title,
                style = TextStyle(
                    fontSize = 22.sp,
                    color = CoverBlue,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(16.dp),
                textAlign = TextAlign.Center
            )
        }

        Surface(
            color = Blue,
            modifier = Modifier.padding(top = 8.dp),
            shape = Shapes.medium
        ) {

            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = "MovieInfo",
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = WhiteCover
                    )
                )
                Column(
                    modifier = Modifier.padding(start = 12.dp, top = 4.dp, bottom = 8.dp)
                ) {

                    Row {
                        Text(
                            text = "Release Date: ",
                            style = TextStyle(
                                fontWeight = FontWeight.Medium,
                                color = Color.White,
                                fontSize = 15.sp
                            )
                        )
                        Text(
                            text = data.releaseDate,
                            style = TextStyle(
                                color = WhiteCover,
                                fontSize = 14.sp
                            )
                        )
                    }

                    Row(
                        modifier = Modifier.padding(top = 4.dp)
                    ) {
                        Text(
                            text = "Runtime: ",
                            style = TextStyle(
                                fontWeight = FontWeight.Medium,
                                color = Color.White,
                                fontSize = 15.sp
                            )
                        )
                        Text(
                            text = "${data.runtime} minutes",
                            style = TextStyle(
                                color = WhiteCover,
                                fontSize = 14.sp
                            )
                        )
                    }

                    Row(
                        modifier = Modifier.padding(top = 4.dp)
                    ) {
                        Text(
                            text = "Popularity: ",
                            style = TextStyle(
                                fontWeight = FontWeight.Medium,
                                color = Color.White,
                                fontSize = 15.sp
                            )
                        )
                        Text(
                            text = "${data.popularity}",
                            style = TextStyle(
                                color = WhiteCover,
                                fontSize = 14.sp
                            )
                        )
                    }

                    Row(
                        modifier = Modifier.padding(top = 4.dp)
                    ) {
                        Text(
                            text = "Budget: ",
                            style = TextStyle(
                                fontWeight = FontWeight.Medium,
                                color = Color.White,
                                fontSize = 15.sp
                            )
                        )
                        Text(
                            text = data.budget.toString(),
                            style = TextStyle(
                                color = WhiteCover,
                                fontSize = 14.sp
                            )
                        )
                    }

                    Row(
                        modifier = Modifier.padding(top = 4.dp)
                    ) {
                        Text(
                            text = "Revenue: ",
                            style = TextStyle(
                                fontWeight = FontWeight.Medium,
                                color = Color.White,
                                fontSize = 15.sp
                            )
                        )
                        Text(
                            text = data.revenue.toString(),
                            style = TextStyle(
                                color = WhiteCover,
                                fontSize = 14.sp
                            )
                        )
                    }

                }

                Text(
                    text = "Overview",
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = WhiteCover
                    )
                )
                Box(
                    modifier = Modifier.padding(start = 12.dp, top = 4.dp, bottom = 8.dp)
                ) {

                    Text(
                        text = data.overview,
                        style = TextStyle(
                            fontWeight = FontWeight.Medium,
                            color = Color.White,
                            fontSize = 15.sp,
                            textAlign = TextAlign.Justify
                        )
                    )

                }

            }

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
