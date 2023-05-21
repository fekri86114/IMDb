package info.fekri.tmdb.ui.feature.search

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dev.burnoo.cokoin.navigation.getNavController
import dev.burnoo.cokoin.navigation.getNavViewModel
import info.fekri.tmdb.ui.theme.Blue
import info.fekri.tmdb.util.MyScreens
import info.fekri.tmdb.util.NetworkChecker

@Composable
fun SearchScreen() {
    val context = LocalContext.current
    val navigation = getNavController()
    val viewModel = getNavViewModel<SearchViewModel>()
    val uiController = rememberSystemUiController()
    SideEffect { uiController.setStatusBarColor(Blue) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 16.dp, end = 8.dp, start = 8.dp)
    ) {
        if (viewModel.showProgress.value) CircularProgressIndicator(
            color = Blue,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            strokeCap = StrokeCap.Round
        )

        SearchTopToolbar {
            navigation.popBackStack()
        }

        // will complete later

    }

}

@Composable
fun SearchTopToolbar(onBackIconPressed: () -> Unit) {
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