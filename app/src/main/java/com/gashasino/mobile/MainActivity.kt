package com.gashasino.mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.gashasino.mobile.ui.Navegacion
import com.gashasino.mobile.ui.theme.Gashasino_mobileTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Gashasino_mobileTheme {
                Navegacion()
            }
        }
    }
}
