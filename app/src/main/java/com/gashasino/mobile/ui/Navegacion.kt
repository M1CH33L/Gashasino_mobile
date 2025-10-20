package com.gashasino.mobile.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gashasino.mobile.viewmodel.FormularioViewModel
import com.gashasino.mobile.viewmodel.LoginViewModel

@Composable
fun Navegacion() {
    val navController = rememberNavController()
    val formularioViewModel: FormularioViewModel = viewModel()
    val loginViewModel: LoginViewModel = viewModel()

    NavHost(navController = navController, startDestination = "formulario") {
        composable("formulario") {
            Formulario(formularioViewModel, navController)
        }
        composable("login") {
            Login(loginViewModel)
        }
    }
}
