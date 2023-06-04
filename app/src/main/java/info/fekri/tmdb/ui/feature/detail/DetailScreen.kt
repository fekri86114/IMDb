package info.fekri.tmdb.ui.feature.detail

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import dev.burnoo.cokoin.navigation.getNavController
import dev.burnoo.cokoin.navigation.getNavViewModel
import info.fekri.tmdb.model.data.MovieId
import info.fekri.tmdb.ui.theme.Blue
import info.fekri.tmdb.ui.theme.CoverBlue
import info.fekri.tmdb.ui.theme.Shapes
import info.fekri.tmdb.ui.theme.WhiteCover
import info.fekri.tmdb.util.MyScreens
import info.fekri.tmdb.util.POSTER_BASE_URL
import info.fekri.tmdb.util.stylePrice

@Composable
fun DetailScreen(movieId: Int) {

    val navigation = getNavController()

    val viewModel = getNavViewModel<DetailViewModel>()
    viewModel.loadData(movieId)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
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
        if (viewModel.showProgress.value) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth(), color = WhiteCover)
        }

        val movieIdState = viewModel.movieById
        MovieDetailShow(movieIdState.value)

    }

}

@Composable
fun MovieDetailShow(data: MovieId) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .padding(top = 8.dp, bottom = 16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        BigPictureMovieId(data.posterPath) {
            // download
        }

        NameSurface(data.title)

        MovieDetailsCard(data)

        MoreDetailButton(
            onHomepageClicked = {
                if (data.homepage.isNullOrEmpty()) {
                    Toast.makeText(context, "No page found!", Toast.LENGTH_SHORT).show()
                } else {
                    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(data.homepage)))
                }
            }
        )

    }

}

@Composable
fun MoreDetailButton(onHomepageClicked: () -> Unit) {

        Button(
            onClick = onHomepageClicked,
            modifier = Modifier.fillMaxWidth(0.8f),
            shape = RoundedCornerShape(bottomEnd = 16.dp, bottomStart = 16.dp)
        ) {
            Text(text = "Homepage")
        }

}

@Composable
fun MovieDetailsCard(data: MovieId) {

    Card(
        modifier = Modifier
            .padding(vertical = 4.dp)
            .fillMaxWidth(0.8f),
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        backgroundColor = CoverBlue
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            MovieDataFirstFloor(data)
        }
    }

}

@Composable
fun MovieDataFirstFloor(data: MovieId) {
    Column {
        Text(
            text = "MovieInfo",
            color = WhiteCover,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Column(modifier = Modifier.padding(start = 8.dp, top = 6.dp)) {
            RowTextItemStyle("Release Date", data.releaseDate)
            RowTextItemStyle("language", data.originalLanguage)
            RowTextItemStyle("Vote Average", "${data.voteAverage} (total: ${data.voteCount})")
            RowTextItemStyle("Runtime", "${data.runtime} minutes")
            RowTextItemStyle("Budget", stylePrice(data.budget.toString()))
            RowTextItemStyle("Revenue", stylePrice(data.revenue.toString()))
        }

        Text(
            text = "Overview",
            color = WhiteCover,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 16.dp)
        )
        Column(
            modifier = Modifier
                .padding(start = 8.dp, top = 8.dp, bottom = 2.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = data.overview,
                fontSize = 14.sp,
                color = Color.White,
                textAlign = TextAlign.Start
            )
        }
    }
}


@Composable
fun RowTextItemStyle(first: String, second: String) {
    Row(
        modifier = Modifier.padding(vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$first: ",
            fontSize = 16.sp,
            color = Color.White,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = second,
            color = WhiteCover,
            fontSize = 15.sp
        )
    }
}

@Composable
fun NameSurface(title: String) {
    Surface(
        modifier = Modifier
            .padding(vertical = 4.dp)
            .fillMaxWidth(0.7f),
        color = WhiteCover,
        shape = Shapes.medium
    ) {
        Text(
            text = title,
            modifier = Modifier.padding(8.dp),
            style = TextStyle(
                fontSize = 24.sp,
                color = CoverBlue
            ),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun BigPictureMovieId(posterPath: String, onImageClicked: (String) -> Unit) {
    Card(
        shape = Shapes.medium,
        modifier = Modifier
            .size(300.dp, 300.dp)
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .clickable {
                onImageClicked.invoke(POSTER_BASE_URL + posterPath)
            }
    ) {
        AsyncImage(
            model = POSTER_BASE_URL + posterPath,
            contentDescription = null,
            modifier = Modifier.size(300.dp, 300.dp),
            contentScale = ContentScale.Crop
        )
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
            Text(text = "Detail")
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
