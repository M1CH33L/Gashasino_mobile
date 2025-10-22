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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.gashasino.mobile.viewmodel.FormularioViewModel


@Composable
fun Formulario(viewModel: FormularioViewModel, navController: NavController) {

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
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 56.dp), // Adjust padding to avoid overlap with the back arrow
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Registrarse", style = MaterialTheme.typography.headlineMedium, color = Color.Green)

                OutlinedTextField(
                    value = viewModel.formulario.nombre,
                    onValueChange = { viewModel.formulario.nombre = it },
                    label = { Text("Ingresa nombre") },
                    isError = !viewModel.verificarNombre(),
                    supportingText = { if(!viewModel.verificarNombre()) Text( viewModel.mensajesError.nombre, color = androidx.compose.ui.graphics.Color.Red) },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Black,
                        unfocusedContainerColor = Color.Black,
                        disabledContainerColor = Color.Black,
                        errorContainerColor = Color.Black,
                        cursorColor = Color.White,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        errorTextColor = Color.Red
                    )
                )
                OutlinedTextField(
                    value = viewModel.formulario.correo,
                    onValueChange = { viewModel.formulario.correo = it },
                    label = { Text("Ingresa correo") },
                    isError = !viewModel.verificarCorreo(),
                    supportingText = { if(!viewModel.verificarCorreo()) Text( viewModel.mensajesError.correo, color = androidx.compose.ui.graphics.Color.Red) },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Black,
                        unfocusedContainerColor = Color.Black,
                        disabledContainerColor = Color.Black,
                        errorContainerColor = Color.Black,
                        cursorColor = Color.White,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        errorTextColor = Color.Red
                    )
                )
                OutlinedTextField(
                    value = viewModel.formulario.edad,
                    onValueChange = { viewModel.formulario.edad = it },
                    label = { Text("Ingresa edad") },
                    isError = !viewModel.verificarEdad(),
                    supportingText = { if(!viewModel.verificarEdad()) Text( viewModel.mensajesError.edad, color = androidx.compose.ui.graphics.Color.Red) },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Black,
                        unfocusedContainerColor = Color.Black,
                        disabledContainerColor = Color.Black,
                        errorContainerColor = Color.Black,
                        cursorColor = Color.White,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        errorTextColor = Color.Red
                    )
                )
                OutlinedTextField(
                    value = viewModel.formulario.contrasena,
                    onValueChange = { viewModel.formulario.contrasena = it },
                    label = { Text("Ingresa contraseña") },
                    isError = !viewModel.verificarContrasena(),
                    visualTransformation = PasswordVisualTransformation(),
                    supportingText = { if(!viewModel.verificarContrasena()) Text( viewModel.mensajesError.contrasena, color = androidx.compose.ui.graphics.Color.Red) },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Black,
                        unfocusedContainerColor = Color.Black,
                        disabledContainerColor = Color.Black,
                        errorContainerColor = Color.Black,
                        cursorColor = Color.White,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        errorTextColor = Color.Red
                    )
                )
                Checkbox(
                    checked = viewModel.formulario.terminos,
                    onCheckedChange = { viewModel.formulario.terminos = it },
                    colors = CheckboxDefaults.colors(
                        checkedColor = Color.Green,
                        uncheckedColor = Color.White,
                        checkmarkColor = Color.Black
                    )
                )
                Text("Acepta los términos", color = Color.White)

                Button(
                    onClick = {
                        if(viewModel.verificarFormulario()) {
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
                                navController.navigate("login")
                            }) { Text("OK") }
                        }
                    )
                }
            }
        }
    }
}
// Force re-index