package com.naol.moviemania

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutInput
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.naol.moviemania.data.auth.GoogleAuth
import com.naol.moviemania.data.remote.model.bottomNavItems
import com.naol.moviemania.presentation.favmovies.FavoriteMoviesScreen
import com.naol.moviemania.presentation.home.HomeScreen
import com.naol.moviemania.presentation.home.MovieCategoryRoute
import com.naol.moviemania.presentation.home.MovieDetailScreenRoute
import com.naol.moviemania.presentation.home.allmovies.AllMoviesScreen
import com.naol.moviemania.presentation.login.LoginViewModel
import com.naol.moviemania.presentation.moviedetail.MovieDetailsScreen
import com.naol.moviemania.presentation.searchmovie.SearchMoviesScreen
import com.naol.moviemania.ui.theme.LightColor
import com.naol.moviemania.ui.theme.MovieManiaTheme
import com.naol.moviemania.ui.theme.Pink
import com.naol.moviemania.ui.theme.Pink41
import com.naol.moviemania.ui.theme.Pink81
import com.naol.moviemania.ui.theme.PrimaryColor
import com.naol.moviemania.ui.theme.SecondaryColor
import com.naol.moviemania.ui.theme.robotoFontFamily
import org.koin.androidx.compose.koinViewModel
import org.koin.core.context.GlobalContext.get
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MovieManiaTheme {
                MainScreen()
            }
        }
    }
}


@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    var selectedIndex by rememberSaveable { mutableIntStateOf(0) }

    Scaffold(modifier = Modifier.fillMaxSize(),
//        topBar = {
//            TopAppBar(title = { AppName() })
//        },
        bottomBar = {
            NavigationBar {
                bottomNavItems.forEachIndexed { index, item ->
                    NavigationBarItem(selected = index == selectedIndex, onClick = {
                        navigateToDestination(navController, item.route)
                        selectedIndex = index
                    }, icon = {
                        Icon(
                            imageVector = if (index == selectedIndex) item.selectedIcon else item.unselectedIcon,
                            contentDescription = item.title,
                            tint = if (index == selectedIndex) PrimaryColor else LightColor,
                        )
                    }, label = {
                        Text(
                            text = item.title,
                            color = if (index == selectedIndex) PrimaryColor else LightColor
                        )
                    })
                }
            }
        }) { innerPadding ->
            Navigation(
                navController,
                Modifier
                    .navigationBarsPadding()
                    .padding(innerPadding)
            )
    }
}


private fun navigateToDestination(navController: NavHostController, route: String) {
    if (navController.currentDestination?.route != route) {
        navController.navigate(route) {
            popUpTo(navController.graph.startDestinationId)
            launchSingleTop = true
            restoreState = true
        }
    }
}

@Composable
fun Navigation(navController: NavHostController, modifier: Modifier) {
    NavHost(
        navController, startDestination = bottomNavItems[0].route, modifier = modifier
    ) {
        bottomNavItems.forEach { item ->
            composable(item.route) {
                when (item.route) {
                    "home" -> HomeScreen(navController = navController)
                    "search" -> SearchMoviesScreen(navController = navController)
                    "favorite" -> FavoriteMoviesScreen()
                }
            }

            composable<MovieCategoryRoute> {
                val args = it.toRoute<MovieCategoryRoute>()
                AllMoviesScreen(args.title, args.route, navController)
            }

            composable<MovieDetailScreenRoute> {
                val args = it.toRoute<MovieDetailScreenRoute>()
                MovieDetailsScreen(args.id)
            }
        }
    }
}


@Composable
fun AppName(
    modifier: Modifier = Modifier
) {
    Text(
        text = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    color = Pink41, fontSize = 32.sp, fontWeight = FontWeight.Bold
                )
            ) {
                append("M")
            }
            withStyle(style = SpanStyle(color = Pink)) {
                append("ovie")
            }
            withStyle(
                style = SpanStyle(
                    color = Pink, fontSize = 32.sp, fontWeight = FontWeight.Bold
                )
            ) {
                append("M")
            }
            withStyle(style = SpanStyle(color = Pink81)) {
                append("ania")
            }
        },
        color = SecondaryColor,
        fontFamily = robotoFontFamily,
        modifier = modifier.background(Color.Transparent),
    )
}

@Composable
fun LoginScreen(modifier: Modifier = Modifier) {

    val activity = LocalContext.current as? Activity ?: return

    // Retrieve the ViewModel, passing the Activity context as a parameter
    val viewModel: LoginViewModel = koinViewModel { parametersOf(activity) }

    Column(
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        AppName()
        Text(
            text = "Login",
            fontFamily = robotoFontFamily,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )
        Text(text = "Login to your account", fontFamily = robotoFontFamily, fontSize = 16.sp)

        Row {
            Button(onClick = { viewModel.signIn() }) {
                Image(
                    painter = painterResource(id = R.drawable.google),
                    contentDescription = "Login with Google"
                )
            }
            Button(onClick = { /*TODO*/ }) {
                Image(
                    painter = painterResource(id = R.drawable.facebook),
                    contentDescription = "Login with Facebook"
                )
            }

        }
    }
}

@Preview
@Composable
private fun LoginPreview() {
//    LoginScreen()
}