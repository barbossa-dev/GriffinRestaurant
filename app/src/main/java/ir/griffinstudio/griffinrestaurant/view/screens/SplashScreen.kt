package ir.griffinstudio.griffinrestaurant.view.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import ir.griffinstudio.griffinrestaurant.R
import ir.griffinstudio.griffinrestaurant.utils.MenuScreen
import ir.griffinstudio.griffinrestaurant.view.ui.theme.GriffinRestaurantTheme
import ir.griffinstudio.griffinrestaurant.viewmodels.MainViewModel

@Composable
fun ViewSplash(navController: NavController, viewModel: MainViewModel = hiltViewModel()) {
    val lifecycle = LocalLifecycleOwner.current
    GriffinRestaurantTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.secondary)
        ) {
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_logo),
                    contentDescription = null
                )
                Text("Griffin Studio", color = MaterialTheme.colorScheme.primary)
            }
        }
    }
    LaunchedEffect(Unit) {
        viewModel.startTimer()
        viewModel.finishSplashString.observe(lifecycle) {
            if (it) {
                navController.navigate(MenuScreen)
            }
        }
    }
}