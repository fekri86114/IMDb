package info.fekri.tmdb.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val LightColorTheme = lightColors(
    primary = Blue,
    secondary = Blue,
    background = BackgroundMain
)

@Composable
fun MainAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colors = LightColorTheme

    MaterialTheme(
        colors = colors,
        typography = Typography,
        content = content
    )

    val uiController = rememberSystemUiController()
    uiController.setStatusBarColor(Blue)

}