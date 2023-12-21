package vistas

import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color

val DarkColors = Colors(
    primary = md_theme_dark_primary,
    onPrimary = Color.White,
    secondary = md_theme_dark_primary,
    onSecondary = md_theme_dark_onSecondary,
    error = md_theme_dark_error,
    onError = md_theme_dark_onError,
    background = md_theme_dark_background,
    onBackground = Color.White,
    surface = md_theme_dark_surface,
    onSurface = Color.White,
    isLight = false,
    primaryVariant = md_theme_dark_primary,
    secondaryVariant = md_theme_dark_secondary
)

val LightColors = Colors(
    primary = md_theme_light_primary, // Color buttons
    onPrimary = Color.White, //color of letters buttons
    secondary = md_theme_light_primary,//Color of box of the checkbox
    onSecondary = md_theme_light_onSecondary,
    error = md_theme_light_error,
    onError = md_theme_light_onError,
    background = Color.White, //Color all elements background
    onBackground = Color.Black, //Color text alert dialog
    surface = Color.White, //color window alert dialog/ color check of the checkbox
    onSurface = Color.Unspecified, //color search bar and text
    isLight = true,
    primaryVariant = md_theme_light_primary,
    secondaryVariant = md_theme_light_secondary
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

