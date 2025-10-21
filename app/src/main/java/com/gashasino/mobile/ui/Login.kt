package com.gashasino.mobile.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.gashasino.mobile.viewmodel.LoginViewModel


@Composable
fun Login(viewModel: LoginViewModel, navController: NavController) {

    var abrirModal by remember { mutableStateOf(false) }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Black
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Volver atrás",
                tint = Color.White,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(16.dp)
                    .clickable { navController.navigate("juegoScreen") }
            )
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Iniciar sesión", style = MaterialTheme.typography.headlineMedium, color = Color.Green)
                OutlinedTextField(
                    value = viewModel.login.correo,
                    onValueChange = { viewModel.login.correo = it },
                    label = { Text("Ingresa correo") },
                    isError = !viewModel.verificarCorreo(),
                    supportingText = {
                        Text(
                            viewModel.mensajesError.correo,
                            color = Color.Red
                        )
                    },
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        cursorColor = Color.White,
                        focusedLabelColor = Color.White,
                    ),
                )

                OutlinedTextField(
                    value = viewModel.login.contrasena,
                    onValueChange = { viewModel.login.contrasena = it },
                    label = { Text("Ingresa contraseña") },
                    isError = !viewModel.comprobarContrasena(),
                    supportingText = {
                        Text(
                            viewModel.mensajesError.contrasena,
                            color = Color.Red
                        )
                    },
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        cursorColor = Color.White,
                        focusedLabelColor = Color.White,
                    ),
                )
                Checkbox(
                    checked = viewModel.login.recordar,
                    onCheckedChange = { viewModel.login.recordar = it },
                    colors = CheckboxDefaults.colors(
                        checkedColor = Color.Green,
                        uncheckedColor = Color.White,
                        checkmarkColor = Color.Black
                    )
                )
                Text("recordar usuario", color = Color.White)

                Button(
                    onClick = {
                        if (viewModel.verificarLogin()) {
                            abrirModal = true
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Green)
                ) {
                    Text("Enviar", color = Color.Black)
                }

                if (abrirModal) {
                    AlertDialog(
                        onDismissRequest = { },
                        title = { Text("Confirmación") },
                        text = { Text("Formulario enviado correctamente") },
                        confirmButton = {
                            Button(onClick = {
                                abrirModal = false
                                navController.navigate("juegoscreen")
                            }
                            ) { Text("OK") }
                        }
                    )
                }

            }
        }
    }
}