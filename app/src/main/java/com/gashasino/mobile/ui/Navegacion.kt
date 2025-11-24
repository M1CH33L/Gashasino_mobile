package com.gashasino.mobile.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gashasino.mobile.viewmodel.FormularioViewModel
import com.gashasino.mobile.viewmodel.LoginViewModel
import com.gashasino.mobile.viewmodel.UserViewModel

@Composable
fun Navegacion() {
    val navController = rememberNavController()

    // UserViewModel compartido para manejar el estado del usuario y monedas en toda la app
    val userViewModel: UserViewModel = viewModel()

    NavHost(navController = navController, startDestination = "Login") {
        
        composable("juegoScreen") {
            JuegoScreen(navController = navController, userViewModel = userViewModel)
        }

        composable("Formulario") {
            Formulario(viewModel = viewModel<FormularioViewModel>(), navController = navController)
        }

        composable("Login") {
            Login(
                viewModel = viewModel<LoginViewModel>(), 
                navController = navController,
                onLoginSuccess = { usuario ->
                    // Actualizamos el ViewModel compartido con el usuario logueado
                    userViewModel.setUsuarioLogueado(usuario)
                }
            )
        }

        composable("perfil") { 
            PerfilScreen(navController = navController, userViewModel = userViewModel) 
        }
        
        composable("ruletaScreen") { 
            RuletaScreen(navController = navController, userViewModel = userViewModel) 
        }

        composable("SlotsScreen") {
            SlotsScreen(navController = navController, userViewModel = userViewModel)
        }
    }
}
