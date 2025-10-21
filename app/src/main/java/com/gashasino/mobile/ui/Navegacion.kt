package com.gashasino.mobile.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gashasino.mobile.viewmodel.FormularioViewModel
import com.gashasino.mobile.viewmodel.LoginViewModel

@Composable
fun Navegacion() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "juegoScreen") {
        composable("juegoScreen") { juegoScreen(navController) }
        composable("Formulario") { Formulario( viewModel = FormularioViewModel(), navController) }
        composable("Login") { Login(viewModel = LoginViewModel(), navController) }
        composable("perfil") { PerfilScreen(navController) }



    }
}
