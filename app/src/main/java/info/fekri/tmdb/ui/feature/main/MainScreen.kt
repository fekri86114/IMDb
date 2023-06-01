package info.fekri.tmdb.ui.feature.main

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.SnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerSnapDistance
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LinearProgressIndicator
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dev.burnoo.cokoin.navigation.getNavController
import dev.burnoo.cokoin.navigation.getNavViewModel
import info.fekri.tmdb.R
import info.fekri.tmdb.model.data.Action
import info.fekri.tmdb.model.data.Fantasy
import info.fekri.tmdb.model.data.PopularResponse
import info.fekri.tmdb.ui.theme.Blue
import info.fekri.tmdb.ui.theme.CoverBlue
import info.fekri.tmdb.ui.theme.ItemBackground
import info.fekri.tmdb.ui.theme.Shapes
import info.fekri.tmdb.ui.theme.WhiteCover
import info.fekri.tmdb.util.CATEGORY_LIST
import info.fekri.tmdb.util.MyScreens
import info.fekri.tmdb.util.NetworkChecker
import info.fekri.tmdb.util.POSTER_BASE_URL
import info.fekri.tmdb.util.styleLimitedText
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen() {

    val context = LocalContext.current

    val navigation = getNavController()
    val uiController = rememberSystemUiController()
    SideEffect {
        uiController.setStatusBarColor(Blue)
    }

    val viewModel =
        getNavViewModel<MainScreenViewModel>(parameters = { parametersOf(NetworkChecker(context).isInternetConnected) })

    val pagerState = rememberPagerState()
    val fling = PagerDefaults.flingBehavior(
        state = pagerState,
        pagerSnapDistance = PagerSnapDistance.atMost(viewModel.dataPopulars.value.size)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 16.dp)
    ) {

        MainTopToolbar {
            navigation.navigate(MyScreens.SearchScreen.route)
        }
        if (viewModel.showProgress.value) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth(), color = WhiteCover)
        }

        CategoryBar(categoryList = CATEGORY_LIST) {
            // go to category screen
            navigation.navigate(MyScreens.CategoryScreen.route + "/$it")
        }

        val dataActionState = viewModel.dataActions
        ActionSubject(dataActionState.value) {
            navigation.navigate(MyScreens.DetailScreen.route + "/" + it)
        }

        val dataPopularState = viewModel.dataPopulars
        PopularMovieSlides(dataPopularState.value, pagerState, fling)

        val dataFantasyState = viewModel.dataFantasies
        FantasySubject(data = dataFantasyState.value) {
            navigation.navigate(MyScreens.DetailScreen.route + "/" + it)
        }

    }
}

// ---------------------------------------------------

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PopularMovieSlides(populars: List<PopularResponse.Popular>, pagerState: PagerState, fling: SnapFlingBehavior) {

    HorizontalPager(
        pageCount = populars.size,
        state = pagerState,
        modifier = Modifier.padding(top = 24.dp),
        flingBehavior = fling
    ) {
        PopularItem(populars[it])
    }

}

@Composable
fun PopularItem(pop: PopularResponse.Popular) {
    Card(
        modifier = Modifier
            .padding(2.dp)
            .size(200.dp, 300.dp)
            .clickable { },
        elevation = 4.dp,
        shape = Shapes.medium,
    ) {
        Column(
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_spider),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .size(200.dp, 80.dp)
                    .background(CoverBlue),
            ) {
                Column(
                    modifier = Modifier.padding(2.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = styleLimitedText(pop.title, 18),
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    )
                    Text(
                        text = pop.releaseDate,
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = WhiteCover
                        ),
                        modifier = Modifier.padding(top = 4.dp)
                    )
                    Row {
                        Text(
                            text = "vote average: ",
                            style = TextStyle(
                                fontSize = 14.sp,
                                color = Color.White
                            )
                        )
                        Text(
                            text = pop.voteAverage.toString(),
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
}


// -------------------------------------------------

@Composable
fun ActionSubject(data: List<Action>, onActionItemClicked: (Int) -> Unit) {

    Column(modifier = Modifier.padding(top = 32.dp)) {
        Text(
            text = "Action",
            modifier = Modifier.padding(start = 16.dp),
            style = MaterialTheme.typography.h6,
            color = WhiteCover
        )

        ActionBar(data, onActionItemClicked)
    }

}

@Composable
fun ActionBar(data: List<Action>, onActionItemClicked: (Int) -> Unit) {
    LazyRow(
        modifier = Modifier.padding(top = 16.dp),
        contentPadding = PaddingValues(end = 16.dp)
    ) {
        items(data.size) {
            ActionItem(data[it], onActionItemClicked)
        }
    }
}

@Composable
fun ActionItem(action: Action, onActionItemClicked: (Int) -> Unit) {

    Card(
        modifier = Modifier
            .padding(start = 16.dp)
            .clickable { onActionItemClicked.invoke(action.id) },
        elevation = 4.dp,
        shape = Shapes.medium,
        backgroundColor = CoverBlue
    ) {
        Column {
            AsyncImage(
                model = POSTER_BASE_URL + action.posterPath,
                contentDescription = null,
                modifier = Modifier
                    .size(200.dp)
                    .padding(2.dp),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier.padding(10.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = styleLimitedText(action.title, 12),
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
                Text(
                    text = action.releaseDate,
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = WhiteCover
                    ),
                    modifier = Modifier.padding(top = 4.dp)
                )
                Row {
                    Text(
                        text = "vote average: ",
                        style = TextStyle(
                            fontSize = 14.sp,
                            color = Color.White
                        )
                    )
                    Text(
                        text = action.voteAverage.toString(),
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

// ------------------------------------------------------

@Composable
fun FantasySubject(data: List<Fantasy>, onActionItemClicked: (Int) -> Unit) {

    Column(modifier = Modifier.padding(top = 24.dp)) {
        Text(
            text = "Fantasy",
            modifier = Modifier.padding(start = 16.dp),
            style = MaterialTheme.typography.h6,
            color = WhiteCover
        )

        FantasyBar(data, onActionItemClicked)
    }

}

@Composable
fun FantasyBar(data: List<Fantasy>, onActionItemClicked: (Int) -> Unit) {
    LazyRow(
        modifier = Modifier.padding(top = 16.dp),
        contentPadding = PaddingValues(end = 16.dp)
    ) {
        items(data.size) {
            FantasyItem(data[it], onActionItemClicked)
        }
    }
}

@Composable
fun FantasyItem(fantasy: Fantasy, onActionItemClicked: (Int) -> Unit) {

    Card(
        modifier = Modifier
            .padding(start = 16.dp)
            .clickable { onActionItemClicked.invoke(fantasy.id) },
        elevation = 4.dp,
        shape = Shapes.medium,
        backgroundColor = CoverBlue
    ) {
        Column {
            AsyncImage(
                model = POSTER_BASE_URL + fantasy.posterPath,
                contentDescription = null,
                modifier = Modifier.size(200.dp),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier.padding(10.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = styleLimitedText(fantasy.title, 12),
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
                Text(
                    text = fantasy.releaseDate,
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = WhiteCover
                    ),
                    modifier = Modifier.padding(top = 4.dp)
                )
                Row {
                    Text(
                        text = "vote average: ",
                        style = TextStyle(
                            fontSize = 14.sp,
                            color = Color.White
                        )
                    )
                    Text(
                        text = fantasy.voteAverage.toString(),
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
