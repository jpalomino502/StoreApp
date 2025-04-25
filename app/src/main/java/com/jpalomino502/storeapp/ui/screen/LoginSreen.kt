package com.jpalomino502.storeapp.ui.screen

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.jpalomino502.storeapp.R


@Composable
fun LoginScreen(navController: NavController) {

    var inputEmail by remember { mutableStateOf("") }
    var inputPassword by remember { mutableStateOf("") }
    val activity = LocalView.current.context as Activity

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
                text = "Iniciar Sesión",
                color = Color(0xFFFF9900),
                // fontWeight = FontWeight.Bold,
                // No me gusta el bold
                fontSize = 26.sp,
            )

            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = inputEmail,
                onValueChange = { inputEmail = it },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(Icons.Default.Email, contentDescription = null)
                },
                label = {
                    Text("Correo Electrónico")
                },
                shape = RoundedCornerShape(10.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = inputPassword,
                onValueChange = { inputPassword = it},
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(Icons.Default.Lock, contentDescription = null)
                },
                label = {
                    Text("Contraseña")
                },
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    val auth = Firebase.auth

                    auth.signInWithEmailAndPassword(inputEmail, inputPassword).addOnCompleteListener(activity){
                        task -> if (task.isSuccessful){
                        navController.navigate("home")
                    } else{
                        Toast.makeText(
                            activity.applicationContext,
                            "Error en credenciales",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    }

                          },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9900)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
            ) {
                Text("Iniciar Sesión", color = Color.White)
            }
            TextButton(onClick = {
                navController.navigate("register")
            }) {
                Text(
                    "¿No tienes cuenta? Registrate.",
                    color = Color(0xFFFF9900)
                )
            }
            TextButton(onClick = {
                navController.navigate("forgetPassword")
            }) {
                Text(
                    "¿Olvidaste tu contraseña?.",
                    color = Color(0xFFFF9900)
                )
            }
        }
    }
}