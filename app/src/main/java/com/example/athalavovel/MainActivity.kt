package com.example.athalavovel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.athalavovel.ui.theme.AthalaVOVELTheme

class Register : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AthalaVOVELTheme {
                RegisterScreen()
            }
        }
    }
}

@Composable
fun RegisterScreen() {

}