package info.fekri.tmdb.ui.feature.main

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
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
import androidx.compose.material.TextButton
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dev.burnoo.cokoin.navigation.getNavController
import dev.burnoo.cokoin.navigation.getNavViewModel
import info.fekri.tmdb.model.data.Popular
import info.fekri.tmdb.model.data.movie.Action
import info.fekri.tmdb.model.data.movie.Adventure
import info.fekri.tmdb.model.data.movie.Comedy
import info.fekri.tmdb.model.data.movie.Drama
import info.fekri.tmdb.model.data.movie.Fantasy
import info.fekri.tmdb.model.data.movie.Horror
import info.fekri.tmdb.model.data.movie.Mystery
import info.fekri.tmdb.model.data.movie.Scientific
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
        getNavViewModel<MainScreenViewModel>(parameters = {
            parametersOf(NetworkChecker(context).isInternetConnected)
        })

    val pagerState = rememberPagerState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 16.dp)
    ) {

        MainTopToolbar {
            navigation.navigate(MyScreens.SearchScreen.route)
        }
        if (viewModel.showProgress.value || viewModel.showMoreProgress.value) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth(), color = WhiteCover)
        }

        CategoryBar(categoryList = CATEGORY_LIST) {
            // go to category screen
            if (NetworkChecker(context).isInternetConnected)
                navigation.navigate(MyScreens.CategoryScreen.route + "/" + it)
        }

        // ---
        val dataActionState = viewModel.dataActions
        val dataFantasyState = viewModel.dataFantasies
        val dataPopularState = viewModel.dataPopulars

        val dataComedyState = viewModel.dataComedies
        val dataDramaState = viewModel.dataDramas
        val dataHorrorState = viewModel.dataHorrors

        val dataMysteryState = viewModel.dataMysteries
        val dataAdventureState = viewModel.dataAdventures
        val dataScientificState = viewModel.dataScientific

        if (dataActionState.value.isNotEmpty()) {


            ActionSubject(dataActionState.value) {
                navigation.navigate(MyScreens.DetailScreen.route + "/" + it)
            }

            FantasySubject(data = dataFantasyState.value) {
                navigation.navigate(MyScreens.DetailScreen.route + "/" + it)
            }

            // popular slides
            PopularMovieSlides(dataPopularState.value, pagerState) {
                navigation.navigate(MyScreens.DetailScreen.route + "/" + it)
            }

            // ---

            ComedySubject(data = dataComedyState.value) {
                navigation.navigate(MyScreens.DetailScreen.route + "/" + it)
            }

            DramaSubject(data = dataDramaState.value) {
                navigation.navigate(MyScreens.DetailScreen.route + "/" + it)
            }

            HorrorMovieSlides(
                horrors = dataHorrorState.value,
                pagerState = pagerState
            ) {
                navigation.navigate(MyScreens.DetailScreen.route + "/" + it)
            }

            // ---
            if (viewModel.showLoadMoreButton.value) {
                TextButton(
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 24.dp),
                    onClick = { viewModel.loadMoreData(NetworkChecker(context).isInternetConnected) }
                ) {
                    Text(
                        text = "Load More...", textAlign = TextAlign.Center
                    )
                }
            }

            if (!viewModel.showLoadMoreButton.value) {
                MysterySubject(dataMysteryState.value) {
                    navigation.navigate(MyScreens.DetailScreen.route + "/" + it)
                }

                AdventureSubject(dataAdventureState.value) {
                    navigation.navigate(MyScreens.DetailScreen.route + "/" + it)
                }

                ScientificSlides(dataScientificState.value, pagerState) {
                    navigation.navigate(MyScreens.DetailScreen.route + "/" + it)
                }
            }

        }

    }
}

// ---------------------------------------------------

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PopularMovieSlides(
    populars: List<Popular>,
    pagerState: PagerState,
    onPopularItemClicked: (Int) -> Unit
) {

    Column(
        modifier = Modifier.padding(top = 24.dp)
    ) {

        Text(
            text = "Popular",
            modifier = Modifier.padding(start = 16.dp),
            style = MaterialTheme.typography.h6,
            color = WhiteCover
        )

        HorizontalPager(
            pageCount = populars.size,
            state = pagerState,
            modifier = Modifier.padding(top = 16.dp)
        ) {
            PopularItem(populars[it], onPopularItemClicked)
        }
    }

}

@Composable
fun PopularItem(pop: Popular, onPopularItemClicked: (Int) -> Unit) {
    Card(
        modifier = Modifier
            .padding(2.dp)
            .size(360.dp, 300.dp)
            .clickable { onPopularItemClicked.invoke(pop.id) },
        elevation = 4.dp,
        shape = Shapes.medium,
        backgroundColor = CoverBlue
    ) {
        Column(
            verticalArrangement = Arrangement.Center
        ) {
            Box {
                AsyncImage(
                    model = POSTER_BASE_URL + pop.posterPath,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .background(CoverBlue)
                        .align(Alignment.BottomCenter),
                ) {
                    Column(
                        modifier = Modifier.padding(8.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = styleLimitedText(pop.title, 22),
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
                    .size(200.dp),
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
            CategoryItemsData(categoryList[it], onCategoryClicked)
        }
    }

}

@Composable
fun CategoryItemsData(subject: Pair<String, Int>, onCategoryClicked: (String) -> Unit) {
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
fun ComedySubject(data: List<Comedy>, onComedyItemClicked: (Int) -> Unit) {

    Column(modifier = Modifier.padding(top = 32.dp)) {
        Text(
            text = "Comedy",
            modifier = Modifier.padding(start = 16.dp),
            style = MaterialTheme.typography.h6,
            color = WhiteCover
        )

        ComedyBar(data, onComedyItemClicked)
    }

}

@Composable
fun ComedyBar(data: List<Comedy>, onComedyItemClicked: (Int) -> Unit) {
    LazyRow(
        modifier = Modifier.padding(top = 16.dp),
        contentPadding = PaddingValues(end = 16.dp)
    ) {
        items(data.size) {
            ComedyItem(data[it], onComedyItemClicked)
        }
    }
}

@Composable
fun ComedyItem(comedy: Comedy, onComedyItemClicked: (Int) -> Unit) {

    Card(
        modifier = Modifier
            .padding(start = 16.dp)
            .clickable { onComedyItemClicked.invoke(comedy.id) },
        elevation = 4.dp,
        shape = Shapes.medium,
        backgroundColor = CoverBlue
    ) {
        Column {
            AsyncImage(
                model = POSTER_BASE_URL + comedy.posterPath,
                contentDescription = null,
                modifier = Modifier
                    .size(200.dp),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier.padding(10.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = styleLimitedText(comedy.title, 12),
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
                Text(
                    text = comedy.releaseDate,
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
                        text = comedy.voteAverage.toString(),
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

// -------------------------------------------------

@Composable
fun DramaSubject(data: List<Drama>, onDramaItemClicked: (Int) -> Unit) {

    Column(modifier = Modifier.padding(top = 32.dp)) {
        Text(
            text = "Drama",
            modifier = Modifier.padding(start = 16.dp),
            style = MaterialTheme.typography.h6,
            color = WhiteCover
        )

        DramaBar(data, onDramaItemClicked)
    }

}

@Composable
fun DramaBar(data: List<Drama>, onDramaItemClicked: (Int) -> Unit) {
    LazyRow(
        modifier = Modifier.padding(top = 16.dp),
        contentPadding = PaddingValues(end = 16.dp)
    ) {
        items(data.size) {
            DramaItem(data[it], onDramaItemClicked)
        }
    }
}

@Composable
fun DramaItem(drama: Drama, onDramaItemClicked: (Int) -> Unit) {

    Card(
        modifier = Modifier
            .padding(start = 16.dp)
            .clickable { onDramaItemClicked.invoke(drama.id) },
        elevation = 4.dp,
        shape = Shapes.medium,
        backgroundColor = CoverBlue
    ) {
        Column {
            AsyncImage(
                model = POSTER_BASE_URL + drama.posterPath,
                contentDescription = null,
                modifier = Modifier
                    .size(200.dp),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier.padding(10.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = styleLimitedText(drama.title, 12),
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
                Text(
                    text = drama.releaseDate,
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
                        text = drama.voteAverage.toString(),
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

// -------------------------------------------------

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HorrorMovieSlides(
    horrors: List<Horror>,
    pagerState: PagerState,
    onHorrorItemClicked: (Int) -> Unit
) {

    Column(
        modifier = Modifier.padding(top = 24.dp)
    ) {

        Text(
            text = "Horror",
            modifier = Modifier.padding(start = 16.dp),
            style = MaterialTheme.typography.h6,
            color = WhiteCover
        )

        HorizontalPager(
            pageCount = horrors.size,
            state = pagerState,
            modifier = Modifier.padding(top = 16.dp)
        ) {
            HorrorItem(horrors[it], onHorrorItemClicked)
        }
    }

}

@Composable
fun HorrorItem(horror: Horror, onHorrorItemClicked: (Int) -> Unit) {
    Card(
        modifier = Modifier
            .padding(2.dp)
            .size(360.dp, 300.dp)
            .clickable { onHorrorItemClicked.invoke(horror.id) },
        elevation = 4.dp,
        shape = Shapes.medium,
        backgroundColor = CoverBlue
    ) {
        Column(
            verticalArrangement = Arrangement.Center
        ) {
            Box {
                AsyncImage(
                    model = POSTER_BASE_URL + horror.posterPath,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .background(CoverBlue)
                        .align(Alignment.BottomCenter),
                ) {
                    Column(
                        modifier = Modifier.padding(8.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = styleLimitedText(horror.title, 22),
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        )
                        Text(
                            text = horror.releaseDate,
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
                                text = horror.voteAverage.toString(),
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
}

// -------------------------------------------------

@Composable
fun MysterySubject(data: List<Mystery>, onMysteryItemClicked: (Int) -> Unit) {

    Column(modifier = Modifier.padding(top = 32.dp)) {
        Text(
            text = "Mystery",
            modifier = Modifier.padding(start = 16.dp),
            style = MaterialTheme.typography.h6,
            color = WhiteCover
        )

        MysteryBar(data, onMysteryItemClicked)
    }

}

@Composable
fun MysteryBar(data: List<Mystery>, onMysteryItemClicked: (Int) -> Unit) {
    LazyRow(
        modifier = Modifier.padding(top = 16.dp),
        contentPadding = PaddingValues(end = 16.dp)
    ) {
        items(data.size) {
            MysteryItem(data[it], onMysteryItemClicked)
        }
    }
}

@Composable
fun MysteryItem(mystery: Mystery, onMysteryItemClicked: (Int) -> Unit) {

    Card(
        modifier = Modifier
            .padding(start = 16.dp)
            .clickable { onMysteryItemClicked.invoke(mystery.id) },
        elevation = 4.dp,
        shape = Shapes.medium,
        backgroundColor = CoverBlue
    ) {
        Column {
            AsyncImage(
                model = POSTER_BASE_URL + mystery.posterPath,
                contentDescription = null,
                modifier = Modifier
                    .size(200.dp),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier.padding(10.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = styleLimitedText(mystery.title, 12),
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
                Text(
                    text = mystery.releaseDate,
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
                        text = mystery.voteAverage.toString(),
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

// -------------------------------------------------

@Composable
fun AdventureSubject(data: List<Adventure>, onAdventureItemClicked: (Int) -> Unit) {

    Column(modifier = Modifier.padding(top = 32.dp)) {
        Text(
            text = "Adventure",
            modifier = Modifier.padding(start = 16.dp),
            style = MaterialTheme.typography.h6,
            color = WhiteCover
        )

        AdventureBar(data, onAdventureItemClicked)
    }

}

@Composable
fun AdventureBar(data: List<Adventure>, onAdventureItemClicked: (Int) -> Unit) {
    LazyRow(
        modifier = Modifier.padding(top = 16.dp),
        contentPadding = PaddingValues(end = 16.dp)
    ) {
        items(data.size) {
            AdventureItem(data[it], onAdventureItemClicked)
        }
    }
}

@Composable
fun AdventureItem(adventure: Adventure, onAdventureItemClicked: (Int) -> Unit) {

    Card(
        modifier = Modifier
            .padding(start = 16.dp)
            .clickable { onAdventureItemClicked.invoke(adventure.id) },
        elevation = 4.dp,
        shape = Shapes.medium,
        backgroundColor = CoverBlue
    ) {
        Column {
            AsyncImage(
                model = POSTER_BASE_URL + adventure.posterPath,
                contentDescription = null,
                modifier = Modifier
                    .size(200.dp),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier.padding(10.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = styleLimitedText(adventure.title, 12),
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
                Text(
                    text = adventure.releaseDate,
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
                        text = adventure.voteAverage.toString(),
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

// -------------------------------------------------

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ScientificSlides(
    scientific: List<Scientific>,
    pagerState: PagerState,
    onScientificItemClicked: (Int) -> Unit
) {

    Column(
        modifier = Modifier.padding(top = 24.dp)
    ) {

        Text(
            text = "Science Fiction",
            modifier = Modifier.padding(start = 16.dp),
            style = MaterialTheme.typography.h6,
            color = WhiteCover
        )

        HorizontalPager(
            pageCount = scientific.size,
            state = pagerState,
            modifier = Modifier.padding(top = 16.dp)
        ) {
            ScientificItem(scientific[it], onScientificItemClicked)
        }
    }

}

@Composable
fun ScientificItem(scientific: Scientific, onScientificItemClicked: (Int) -> Unit) {
    Card(
        modifier = Modifier
            .padding(2.dp)
            .size(360.dp, 300.dp)
            .clickable { onScientificItemClicked.invoke(scientific.id) },
        elevation = 4.dp,
        shape = Shapes.medium,
        backgroundColor = CoverBlue
    ) {
        Column(
            verticalArrangement = Arrangement.Center
        ) {
            Box {
                AsyncImage(
                    model = POSTER_BASE_URL + scientific.posterPath,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .background(CoverBlue)
                        .align(Alignment.BottomCenter),
                ) {
                    Column(
                        modifier = Modifier.padding(8.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = styleLimitedText(scientific.title, 22),
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        )
                        Text(
                            text = scientific.releaseDate,
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
                                text = scientific.voteAverage.toString(),
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
