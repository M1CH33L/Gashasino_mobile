package com.gashasino.mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.gashasino.mobile.ui.*
import com.gashasino.mobile.ui.theme.Formulario
import com.gashasino.mobile.ui.theme.Gashasino_mobileTheme
import com.gashasino.mobile.viewmodel.FormularioViewModel

class MainActivity : ComponentActivity() {

    private val viewModel = FormularioViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Gashasino_mobileTheme{ // <- aquí va el nombre de TU TEMA, lo puedes encontrar en ui/theme/Theme.kt
                Formulario(viewModel)
                //BotonCargando()
                //TextoInvertido()
                //Persistencia()
                //UsuarioFormScreen()
                //Modal()
                // Formulario()
                //Login()
                //Navegacion()
                //CamaraFotos()
                //ModalScreen()
                //GPS()
                //AgregarUsuarios()
                // TocarPantalla()
            }
        }
    }



}