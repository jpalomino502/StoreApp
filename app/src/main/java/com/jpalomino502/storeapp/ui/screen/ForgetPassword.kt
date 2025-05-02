package com.jpalomino502.storeapp.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.jpalomino502.storeapp.R
import com.jpalomino502.storeapp.ui.validation.validateEmail

@Composable
fun ForgetPassword(navController: NavController) {
    val auth = FirebaseAuth.getInstance()
    var email by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 24.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            Text(
                text = "¿Olvidaste tu contraseña?",
                color = Color(0xFFFF9900),
                fontSize = 26.sp,
            )

            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(Icons.Default.Email, contentDescription = null)
                },
                label = { Text("Correo Electrónico") },
                shape = RoundedCornerShape(10.dp),
                isError = isError
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (message.isNotEmpty()) {
                Text(
                    text = message,
                    color = if (isError) Color.Red else Color.Green,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )
            }

            Button(
                onClick = {
                    val (isValid, validationMessage) = validateEmail(email)

                    if (isValid) {
                        auth.sendPasswordResetEmail(email)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    message = "Correo de recuperación enviado"
                                    isError = false
                                } else {
                                    message = "Error al enviar el correo"
                                    isError = true
                                }
                            }
                    } else {
                        message = validationMessage
                        isError = true
                    }
                }, modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFF9900),
                    contentColor = Color.White
                )
            ) {
                Text("Enviar correo de restablecimiento", color = Color.White)
            }

            TextButton(onClick = {
                navController.popBackStack()
            }) {
                Text("Iniciar Sesión", color = Color(0xFFFF9900))
            }
        }
    }
}
