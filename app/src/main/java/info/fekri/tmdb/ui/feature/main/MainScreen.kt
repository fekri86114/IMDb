package info.fekri.tmdb.ui.feature.main

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dev.burnoo.cokoin.navigation.getNavController
import info.fekri.tmdb.R
import info.fekri.tmdb.ui.theme.BackgroundMain
import info.fekri.tmdb.ui.theme.Blue
import info.fekri.tmdb.ui.theme.CoverBlue
import info.fekri.tmdb.ui.theme.ItemBackground
import info.fekri.tmdb.ui.theme.MainAppTheme
import info.fekri.tmdb.ui.theme.Shapes
import info.fekri.tmdb.ui.theme.WhiteCover
import info.fekri.tmdb.util.MOVIE_CATEGORY_LIST
import info.fekri.tmdb.util.MyScreens
import info.fekri.tmdb.util.NetworkChecker
import info.fekri.tmdb.util.styleLimitedText

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainScreenPreview() {
    MainAppTheme {
        Surface(color = BackgroundMain, modifier = Modifier.fillMaxSize()) {
            MainScreen()
        }
    }
}

@Composable
fun MainScreen() {
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

        MainTopToolbar {
            navigation.navigate(MyScreens.SearchScreen.route)
        }

        CategoryBar(categoryList = MOVIE_CATEGORY_LIST) {
            // go to category screen
            navigation.navigate(MyScreens.CategoryScreen.route + "/$it")
        }

        ProductSubject()

        ProductSubject()

        ProductSubject()

    }

}

// -------------------------------------------------

@Composable
fun ProductSubject() {
    
    Column(modifier = Modifier.padding(top = 32.dp)) {
        Text(
            text = "Popular",
            modifier = Modifier.padding(start = 16.dp),
            style = MaterialTheme.typography.h6,
            color = WhiteCover
        )

        ProductBar()
    }
    
}

@Composable
fun ProductBar() {
    LazyRow(modifier = Modifier.padding(top = 16.dp),
    contentPadding = PaddingValues(end = 16.dp)
    ) {
        items(10) {
            ProductItem()
        }
    }
}

@Composable
fun ProductItem() {
    
    Card(
        modifier = Modifier
            .padding(start = 16.dp)
            .clickable { },
        elevation = 4.dp,
        shape = Shapes.medium,
        backgroundColor = CoverBlue
    ) {
        Column {
            Image(
                painter = painterResource(id = R.drawable.img_spider),
                contentDescription = null,
                modifier = Modifier.size(200.dp),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier.padding(10.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = styleLimitedText("Spider Man", 12),
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
                Text(
                    text = "2016-05-25",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = WhiteCover
                    ),
                    modifier = Modifier.padding(top = 4.dp)
                )
                Row {
                    Text(
                        text = "Rating: ",
                        style = TextStyle(
                            fontSize = 14.sp,
                            color = Color.White
                        )
                    )
                    Text(
                        text = "8.4",
                        style = TextStyle(
                            fontSize = 13.sp,
                            color = WhiteCover
                        )
                    )
                }
            }
        }
    }
    
}

// --------------------------------------------------

@Composable
fun CategoryBar(categoryList: List<Pair<String, Int>>, onCategoryClicked: (String) -> Unit) {

    LazyRow(
        modifier = Modifier.padding(top = 16.dp),
        contentPadding = PaddingValues(end = 8.dp)
    ) {
        items(categoryList.size) {
            CategoryItem(categoryList[it], onCategoryClicked)
        }
    }

}

@Composable
fun CategoryItem(subject: Pair<String, Int>, onCategoryClicked: (String) -> Unit) {
    Column(
        modifier = Modifier
            .padding(start = 8.dp)
            .clickable { onCategoryClicked.invoke(subject.first) },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            shape = Shapes.medium,
            color = ItemBackground
        ) {
            Image(
                painter = painterResource(id = subject.second),
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .padding(16.dp)
            )
        }
    }
}

// -------------------------------------------------

@Composable
fun MainTopToolbar(onSearchClicked: () -> Unit) {
    val context = LocalContext.current

    TopAppBar(
        elevation = 0.dp,
        backgroundColor = Blue,
        title = {
            Text(text = "IMDb Movies")
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
                Icon(imageVector = Icons.Default.Search, contentDescription = null)
            }

        }
    )
}
