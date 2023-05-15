package info.fekri.tmdb.ui.feature.start

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dev.burnoo.cokoin.navigation.getNavController
import info.fekri.tmdb.R
import info.fekri.tmdb.ui.theme.Blue
import info.fekri.tmdb.ui.theme.CoverBlue
import info.fekri.tmdb.ui.theme.WhiteCover
import info.fekri.tmdb.util.MyScreens

@Preview(showBackground = true)
@Composable
fun StartScreenPreview() {
    Surface(modifier = Modifier.fillMaxSize(), color = Blue) {
        StartScreen()
    }
}

@Composable
fun StartScreen() {
    val uiController = rememberSystemUiController()
    SideEffect { uiController.setStatusBarColor(CoverBlue) }
    val navigation = getNavController()

    Box(modifier = Modifier.fillMaxSize().padding(vertical = 8.dp), contentAlignment = Alignment.TopCenter) {

            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier  = Modifier.height(10.dp))

                ShowHelloAnim()

                Column {
                    StartContent("Let's start normally :-)") {
                        navigation.navigate(MyScreens.MainScreen.route)
                    }
                    StartContent("Let's search movie") {
                        navigation.navigate(MyScreens.SearchScreen.route)
                    }
                    StartContent("You choose :-)") {
                        navigation.navigate(MyScreens.MainScreen.route)
                    }
                }

            }

    }
}

@Composable
fun StartContent(
    message: String,
    onContentClicked: () -> Unit
) {

    Column(
        modifier = Modifier
            .padding(vertical = 1.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextButton(
            onClick = { onContentClicked.invoke() },
            modifier = Modifier.fillMaxWidth(0.7f)
        ) {
            Text(
                text = message,
                style = TextStyle(
                    color = WhiteCover
                )
            )
        }
    }

}

@Composable
fun ShowHelloAnim() {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.anim_hello)
    )
    LottieAnimation(
        composition = composition,
        iterations = LottieConstants.IterateForever,
        modifier = Modifier.size(280.dp)
    )
}


