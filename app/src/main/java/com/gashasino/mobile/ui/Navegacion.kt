package com.gashasino.mobile.ui

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gashasino.mobile.viewmodel.FormularioViewModel // Asegúrate de que este import exista
import com.gashasino.mobile.viewmodel.LoginViewModel     // Asegúrate de que este import exista
import com.gashasino.mobile.viewmodel.UserViewModel
import com.gashasino.mobile.viewmodel.UserViewModelFactory // ¡Importa la Factory que creaste!

@Composable
fun Navegacion() {
    val navController = rememberNavController()

    // --- PASO 1: Obtener el contexto de la aplicación ---
    // Obtenemos el contexto actual y lo casteamos a Application.
    val application = LocalContext.current.applicationContext as Application

    // --- PASO 2: Usar la Factory para crear el UserViewModel ---
    // Le pasamos nuestra factory personalizada a la función viewModel().
    // Ahora el sistema sabe cómo construir un UserViewModel que requiere el contexto de la aplicación.
    val userViewModel: UserViewModel = viewModel(
        factory = UserViewModelFactory(application)
    )

    // El resto de tu NavHost se mantiene como lo tenías.
    NavHost(navController = navController, startDestination = "Formulario") {
        composable("juegoScreen") { JuegoScreen(navController = navController, userViewModel = userViewModel) }

        // Utiliza viewModel() para que Compose gestione la instancia de ViewModels simples
        composable("Formulario") {
            Formulario(viewModel = viewModel<FormularioViewModel>(), navController = navController)
        }

        composable("Login") {
            Login(viewModel = viewModel<LoginViewModel>(), navController = navController)
        }

        composable("perfil") { PerfilScreen(navController) }
        composable("ruletaScreen") { RuletaScreen(navController = navController, userViewModel = userViewModel) }

        // Pasamos la instancia de UserViewModel que ya fue creada correctamente.
        composable("SlotsScreen") {
            SlotsScreen(navController = navController, userViewModel = userViewModel)
        }
    }
}
