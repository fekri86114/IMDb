package info.fekri.tmdb.ui.feature.category

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import dev.burnoo.cokoin.navigation.getNavController
import dev.burnoo.cokoin.navigation.getNavViewModel
import info.fekri.tmdb.R
import info.fekri.tmdb.model.data.QueryResult
import info.fekri.tmdb.model.data.movie.Mystery
import info.fekri.tmdb.ui.feature.detail.RowTextItemStyle
import info.fekri.tmdb.ui.theme.Blue
import info.fekri.tmdb.ui.theme.CoverBlue
import info.fekri.tmdb.ui.theme.ItemBackground
import info.fekri.tmdb.ui.theme.Shapes
import info.fekri.tmdb.ui.theme.WhiteCover
import info.fekri.tmdb.util.MyScreens
import info.fekri.tmdb.util.POSTER_BASE_URL

@Composable
fun CategoryScreen(movieId: String) {

    val context = LocalContext.current

    val viewModel = getNavViewModel<CategoryViewModel>()
    viewModel.loadDataByCategory(movieId)
    val categoryDataState = viewModel.categoryData

    val navigation = getNavController()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 2.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        CategoryTopToolbar(
            movieId,
            onBackIconPressed = {
                navigation.popBackStack()
            },
            onSearchIconPressed = {
                navigation.navigate(MyScreens.SearchScreen.route) {
                    popUpTo(MyScreens.MainScreen.route) {
                        inclusive = true
                    }
                }
            }
        )

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(top = 24.dp)
        ) {
            CategoryItemsData(data = categoryDataState.value) {
                navigation.navigate(MyScreens.DetailScreen.route + "/" + it) {
                    popUpTo(MyScreens.MainScreen.route) {
                        inclusive = true
                    }
                }
            }
        }

    }

}

// ----------------

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CategoryItemsData(data: List<QueryResult>, onCategoryItemClicked: (Int) -> Unit) {
    val pageCount = data.size

    val startIndex = Int.MAX_VALUE / 2
    val pagerState = rememberPagerState(initialPage = startIndex)

    HorizontalPager(
        // Set the raw page count to a really large number
        pageCount = pageCount,
        state = pagerState,
        // Add 32.dp horizontal padding to 'center' the pages
        contentPadding = PaddingValues(horizontal = 32.dp),
        // Add some horizontal spacing between items
        pageSpacing = 4.dp,
        modifier = Modifier.fillMaxWidth()
    ) { index ->

        CategoryItem(data[index], onCategoryItemClicked)

    }

}

@Composable
fun CategoryItem(category: QueryResult, onCategoryItemClicked: (Int) -> Unit) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
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
                    model = POSTER_BASE_URL + category.posterPath,
                    contentDescription = null,
                    modifier = Modifier.size(width = 340.dp, height = 340.dp),
                    contentScale = ContentScale.Crop
                )
            }
        }
        
        Spacer(modifier = Modifier.height(36.dp))

        Card(
            modifier = Modifier
                .width(300.dp)
                .height(280.dp),
            backgroundColor = ItemBackground,
            shape= RoundedCornerShape(8.dp),
            elevation = 0.dp
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = category.title,
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
                    RowTextItemStyle(first = "Language", second = category.originalLanguage)
                    RowTextItemStyle(first = "Release date", second = category.releaseDate)
                    RowTextItemStyle(first = "Vote info", second = "${category.voteAverage} (total: ${category.voteCount})")
                    RowTextItemStyle(first = "Popularity", second = category.popularity.toString())
                }
            }

        }

        Button(
            modifier = Modifier.fillMaxWidth(0.9f)
                .padding(top = 16.dp),
            onClick = { onCategoryItemClicked.invoke(category.id) },
            shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
        ) {
            Text(text = "Visit")
        }
    }
}

// --------------------

@Composable
fun CategoryTopToolbar(
    title: String,
    onBackIconPressed: () -> Unit,
    onSearchIconPressed: () -> Unit
) {
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
            Text(text = title)
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
