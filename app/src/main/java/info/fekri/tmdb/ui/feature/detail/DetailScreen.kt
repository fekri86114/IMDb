package info.fekri.tmdb.ui.feature.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dev.burnoo.cokoin.navigation.getNavController
import dev.burnoo.cokoin.navigation.getNavViewModel
import info.fekri.tmdb.model.data.MovieById
import info.fekri.tmdb.ui.theme.Blue
import info.fekri.tmdb.ui.theme.CoverBlue
import info.fekri.tmdb.ui.theme.WhiteCover
import info.fekri.tmdb.util.NetworkChecker
import info.fekri.tmdb.util.POSTER_BASE_URL
import org.koin.core.parameter.parametersOf

@Composable
fun DetailScreen(id: Int) {
    val context = LocalContext.current
    val navigation = getNavController()
    val uiController = rememberSystemUiController()
    uiController.setSystemBarsColor(Blue)

    val viewModel = getNavViewModel<DetailViewModel>(parameters = { parametersOf(id) })

    Column(
        modifier = Modifier.fillMaxSize().padding(bottom = 16.dp)
    ) {

        DetailTopToolbar { navigation.popBackStack() }

        MovieDetailItemBar(viewModel.detailMovie.value)

    }

}

@Composable
fun MovieDetailItemBar(movieById: MovieById) {



}

// ------------------------------------------------------------



// ------------------------------------------------------------

@Composable
fun DetailTopToolbar(onBackIconPressed: () -> Unit) {
    TopAppBar(
        elevation = 0.dp,
        backgroundColor = Blue,
        navigationIcon = {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null,
                modifier = Modifier.clickable { onBackIconPressed.invoke() }
            )
        },
        title = {
            Text(text = "IMDb Movies")
        }
    )
}

