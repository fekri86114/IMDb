package info.fekri.tmdb.ui.feature.search

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dev.burnoo.cokoin.navigation.getNavController
import dev.burnoo.cokoin.navigation.getNavViewModel
import info.fekri.tmdb.model.data.QueryResult
import info.fekri.tmdb.ui.feature.detail.RowTextItemStyle
import info.fekri.tmdb.ui.theme.BackgroundMain
import info.fekri.tmdb.ui.theme.Blue
import info.fekri.tmdb.ui.theme.CoverBlue
import info.fekri.tmdb.ui.theme.ItemBackground
import info.fekri.tmdb.ui.theme.MainAppTheme
import info.fekri.tmdb.ui.theme.Shapes
import info.fekri.tmdb.ui.theme.WhiteCover
import info.fekri.tmdb.util.MyScreens
import info.fekri.tmdb.util.POSTER_BASE_URL

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SearchScreenPreview() {
    MainAppTheme {
        Surface(color = BackgroundMain, modifier = Modifier.fillMaxSize()) {
            SearchScreen()
        }
    }
}

@Composable
fun SearchScreen() {
    val context = LocalContext.current
    val navigation = getNavController()

    val uiController = rememberSystemUiController()
    SideEffect { uiController.setStatusBarColor(Blue) }

    val viewModel = getNavViewModel<SearchViewModel>()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 16.dp)
    ) {

        SearchTopToolbar {
            navigation.navigate(MyScreens.MainScreen.route)
        }
        if (viewModel.showProgress.value) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth(), color = Blue)
        }

        MovieSearchBar(viewModel)

        /* show data after getting false the progressbar */
        if (viewModel.showContent.value) {
            ShowDataSearch(viewModel.dataSearch.value)
        }

        BackHandler() {
            if (
                viewModel.dataSearch.value.isNotEmpty() &&
                viewModel.search.value.toString().isNotEmpty() ||
                viewModel.search.value.toString().isNotBlank()
            ) {
                Toast.makeText(
                    context,
                    "Cleared data before going ...",
                    Toast.LENGTH_SHORT
                ).show()
                clearInput(viewModel) /* clears data when you come again and lets you to
     start a new search :-) */
                Toast.makeText(
                    context,
                    "You can press-again to go :-)",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                navigation.popBackStack()
            }
        }

    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ShowDataSearch(data: List<QueryResult>) {
    val pageCount = data.size
    val startIndex = Int.MAX_VALUE / 2
    val pagerState = rememberPagerState(initialPage = startIndex)

    HorizontalPager(
        pageCount = pageCount,
        state = pagerState,
        // Add 32.dp horizontal padding to 'center' the pages
        contentPadding = PaddingValues(horizontal = 32.dp),
        // Add some horizontal spacing between items
        pageSpacing = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        ShowDataSearchItem(data = data[it])
    }

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ShowDataSearchItem(data: QueryResult) {
    var showContent by remember { mutableStateOf(false) }
    val transition = updateTransition(showContent, label = "")

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,

        ) {
        Card(
            modifier = Modifier
                .width(300.dp)
                .height(240.dp)
                .padding(top = 10.dp),
            backgroundColor = CoverBlue,
            shape = RoundedCornerShape(topEnd = 16.dp, topStart = 16.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = POSTER_BASE_URL + data.posterPath,
                    contentDescription = null,
                    modifier = Modifier.size(width = 340.dp, height = 340.dp),
                    contentScale = ContentScale.Crop
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier
                .width(300.dp)
                .height(280.dp),
            backgroundColor = ItemBackground,
            shape = RoundedCornerShape(8.dp),
            elevation = 0.dp,
            onClick = { showContent = !showContent }
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = data.title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = WhiteCover,
                    modifier = Modifier.padding(16.dp)
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp, end = 8.dp, start = 16.dp),
                ) {
                    RowTextItemStyle(first = "Language", second = data.originalLanguage)
                    RowTextItemStyle(first = "Release date", second = data.releaseDate)
                    RowTextItemStyle(
                        first = "Vote info",
                        second = "${data.voteAverage} (total: ${data.voteCount})"
                    )
                    RowTextItemStyle(first = "Popularity", second = data.popularity.toString())
                }

                if (showContent) {
                    val visibleState = transition.animateDp(
                        transitionSpec = {
                            if (false isTransitioningTo true) {
                                tween(durationMillis = 300)
                            } else {
                                spring()
                            }
                        }, label = ""
                    ) { if (it) 200.dp else 0.dp }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(visibleState.value)
                            .verticalScroll(rememberScrollState())
                    ) {
                        Text(
                            text = "Overview",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = Color.White
                        )

                        Column(
                            modifier = Modifier.padding(
                                top = 16.dp,
                                end = 8.dp,
                                start = 8.dp,
                                bottom = 8.dp
                            )
                        ) {
                            Text(
                                text = data.overview,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Medium,
                                color = WhiteCover
                            )
                        }

                    }
                }
            }

        }
    }

}

@Composable
fun MovieSearchBar(viewModel: SearchViewModel) {
    val search = viewModel.search.observeAsState(initial = "")

    Card(
        elevation = 0.dp,
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = WhiteCover,
        shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
    ) {

        Row(
            modifier = Modifier.padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            MainSearchTextField(
                search.value,
                android.R.drawable.ic_menu_search,
                "Search Movie..."
            ) { text ->
                viewModel.search.value = text
            }
            SearchMovieButton(viewModel) {

                viewModel.loadDataSearch(it)

            }
        }

    }

}

@Composable
fun SearchMovieButton(viewModel: SearchViewModel, onSearchButtonClicked: (String) -> Unit) {
    TextButton(
        onClick = { onSearchButtonClicked.invoke(viewModel.search.value!!) },
        modifier = Modifier
            .fillMaxWidth(0.6f)
            .padding(top = 26.dp, start = 4.dp)
    ) {
        Text(text = "Search!", modifier = Modifier.padding(8.dp))
    }
}

@Composable
fun MainSearchTextField(
    edtValue: String,
    icon: Int,
    hint: String,
    onValueChanges: (String) -> Unit
) {

    OutlinedTextField(
        keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words),
        label = { Text(hint) },
        value = edtValue,
        singleLine = true,
        onValueChange = onValueChanges,
        placeholder = { Text(hint) },
        modifier = Modifier
            .fillMaxWidth(0.6f)
            .padding(top = 16.dp),
        shape = Shapes.medium,
        leadingIcon = { Icon(painterResource(icon), null) },
    )

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

fun clearInput(viewModel : SearchViewModel) {
    viewModel.search.value = ""
    viewModel.dataSearch.value = listOf()
}
