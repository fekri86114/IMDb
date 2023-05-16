package info.fekri.tmdb.ui.feature.main

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.Surface
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dev.burnoo.cokoin.navigation.getNavController
import info.fekri.tmdb.R
import info.fekri.tmdb.ui.theme.BackgroundMain
import info.fekri.tmdb.ui.theme.Blue
import info.fekri.tmdb.ui.theme.CoverBlue
import info.fekri.tmdb.ui.theme.MainAppTheme
import info.fekri.tmdb.ui.theme.Shapes
import info.fekri.tmdb.ui.theme.WhiteCover
import info.fekri.tmdb.util.MyScreens
import info.fekri.tmdb.util.NetworkChecker

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainScreenPreview() {
    MainAppTheme {
        Surface(color = BackgroundMain, modifier = Modifier.fillMaxSize()) {
            MovieActionItem()
        }
    }
}

@Composable
fun MainScreen() {
    val context = LocalContext.current
    val navigation = getNavController()
    val uiController = rememberSystemUiController()
    SideEffect { uiController.setStatusBarColor(Blue) }

    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 16.dp)
        ) {
            MainTopToolbar {
                navigation.navigate(MyScreens.SearchScreen.route)
            }

            Column(
                modifier = Modifier.padding(top = 16.dp, start = 8.dp)
            ) {
                Text(
                    text = "Actions",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = WhiteCover
                )
                MovieActionItemBar()
            }

            Column(
                modifier = Modifier.padding(top = 24.dp, start = 8.dp)
            ) {
                Text(
                    text = "Animations",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = WhiteCover
                )
                MovieActionItemBar()
            }

            Column(
                modifier = Modifier.padding(top = 24.dp, start = 8.dp)
            ) {
                Text(
                    text = "Romantic",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = WhiteCover,
                )
                MovieActionItemBar()
            }

        }
    }

}

// -------------------------------------------------

@Composable
fun MainTopToolbar(onSearchClicked: () -> Unit) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current

    val paddingTop =
        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) 320.dp else 110.dp
    TopAppBar(
        elevation = 0.dp,
        backgroundColor = Blue,
        title = { Text(text = "IMDb Movies", modifier = Modifier.padding(start = paddingTop)) },
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

// -------------------------------------------------

@Composable
fun MovieActionItemBar() {

    LazyRow(modifier = Modifier.padding(top = 16.dp), contentPadding = PaddingValues(end = 14.dp)) {

        items(5) {
            MovieActionItem()
        }

    }

}

@Composable
fun MovieActionItem() {

    Card(
        modifier = Modifier
            .padding(start = 9.dp)
            .clickable { },
        elevation = 4.dp,
        shape = Shapes.medium,
        backgroundColor = CoverBlue
    ) {

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            AsyncImage(
                model = R.drawable.img_jumanji,
                contentDescription = null,
                modifier = Modifier.size(180.dp),
                contentScale = ContentScale.Crop,
                alignment = Alignment.TopCenter
            )
            Column(modifier = Modifier.padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Jumanji",
                    style = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.Medium, color = Color.White),
                    maxLines = 1
                )
                Text(
                    text = "2016",
                    style = TextStyle(fontSize = 14.sp, color = WhiteCover),
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }

    }

}
