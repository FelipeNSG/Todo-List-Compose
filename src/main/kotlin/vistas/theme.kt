package vistas

import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color

val DarkColors = Colors(
    primary = Color(0xFF6200EE),
    primaryVariant = Color(0xFF3700B3),
    secondary = Color(0xFF03DAC6),
    secondaryVariant = Color.Unspecified,
    background = Color.Unspecified,
    surface = Color.White, //Color of de alert screen window
    error = Color.Unspecified,
    onPrimary = Color.White, //Color of text the buttons
    onSecondary = Color.Black, //Color of the text Alert Screen
    onBackground = Color.Black,
    onSurface = Color.Black, //Component searchBar
    onError = Color.Unspecified,
    isLight = false
)

val LightColors = Colors(
    primary = Color(0xFF6200EE), //color of the buttons
    primaryVariant = Color.Yellow,
    secondary = Color.Red,
    secondaryVariant = Color.White,
    background = Color.Unspecified,
    surface = Color.White, //Color of de alert screen window
    error = Color.Unspecified,
    onPrimary = Color.White, //Color of text the buttons
    onSecondary = Color.Black, //Color of the text Alert Screen
    onBackground = Color.Blue,
    onSurface = Color.Unspecified, //Component searchBar
    onError = Color.White,
    isLight = true
)

@Composable
fun ToDoListTheme(
    content: @Composable () -> Unit
) {
    androidx.compose.material.MaterialTheme(
        colors = if (JetRedditThemeSettings.isInDarkTheme.value) DarkColors else LightColors,
        content = content
    )
}


object JetRedditThemeSettings {
    var isInDarkTheme: MutableState<Boolean> = mutableStateOf(false)
}

