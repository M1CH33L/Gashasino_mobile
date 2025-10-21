package com.gashasino.mobile.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.gashasino.mobile.viewmodel.FormularioViewModel
import com.gashasino.mobile.viewmodel.LoginViewModel
import androidx.navigation.NavController


@Composable
fun Login(viewModel: LoginViewModel, navController: NavController) {

    var abrirModal by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Iniciar sesión", style = MaterialTheme.typography.headlineMedium)
        OutlinedTextField(
            value = viewModel.login.correo,
            onValueChange = { viewModel.login.correo = it },
            label = { Text("Ingresa correo") },
            isError = !viewModel.verificarCorreo(),
            supportingText = { Text( viewModel.mensajesError.correo, color = androidx.compose.ui.graphics.Color.Red) }
        )
        OutlinedTextField(
            value = viewModel.login.contrasena,
            onValueChange = { viewModel.login.contrasena = it },
            label = { Text("Ingresa contraseña") },
            isError = !viewModel.comprobarContrasena(),
            supportingText = { Text( viewModel.mensajesError.contrasena, color = androidx.compose.ui.graphics.Color.Red) }
        )
        Checkbox(
            checked = viewModel.login.recordar,
            onCheckedChange = { viewModel.login.recordar = it },
        )
        Text("recordar usuario")

        Button(
            onClick = {
                if(viewModel.verificarLogin()) {
                    abrirModal = true
                }
            }
        ) {
            Text("Enviar")
        }

        if (abrirModal) {
            AlertDialog(
                onDismissRequest = { },
                title = { Text("Confirmación") },
                text = { Text("Formulario enviado correctamente") },
                confirmButton = {
                    Button(onClick = { abrirModal = false
                        navController.navigate("juegoscreen") }
                    ) { Text("OK") }
                }
            )
        }

    }
}