package ir.griffinstudio.griffinrestaurant.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ir.griffinstudio.griffinrestaurant.utils.ArScreen
import ir.griffinstudio.griffinrestaurant.utils.MenuScreen
import ir.griffinstudio.griffinrestaurant.utils.SplashScreen
import ir.griffinstudio.griffinrestaurant.view.screens.ViewAr
import ir.griffinstudio.griffinrestaurant.view.screens.ViewMenu
import ir.griffinstudio.griffinrestaurant.view.screens.ViewSplash
import ir.griffinstudio.griffinrestaurant.view.ui.theme.GriffinRestaurantTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GriffinRestaurantTheme {
                ViewMainContent()
            }
        }
    }
}

@Composable
fun ViewMainContent() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = SplashScreen) {
        composable<SplashScreen> {
            ViewSplash(navController = navController)
        }
        composable<ArScreen> {
            ViewAr()
        }
        composable<MenuScreen> {
            ViewMenu(navController = navController)
        }
    }
}