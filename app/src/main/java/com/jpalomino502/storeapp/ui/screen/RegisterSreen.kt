package com.jpalomino502.storeapp.ui.screen


import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
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
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.auth
import com.jpalomino502.storeapp.R
import com.jpalomino502.storeapp.ui.validation.validateConfirmPassword
import com.jpalomino502.storeapp.ui.validation.validateEmail
import com.jpalomino502.storeapp.ui.validation.validateName
import com.jpalomino502.storeapp.ui.validation.validatePassword

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(navController: NavController, onSuccessfulRegister:()->Unit = {}) {

    val auth = Firebase.auth
    val activity = LocalView.current.context as Activity
    var inputName by remember { mutableStateOf("") }
    var inputEmail by remember { mutableStateOf("") }
    var inputPassword by remember { mutableStateOf("") }
    var inputPasswordConfirmation by remember { mutableStateOf("") }

    var nameError by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }
    var passwordConfirmationError by remember { mutableStateOf("") }

    var registerError by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { innerPadding ->

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

            Spacer(modifier = Modifier.size(32.dp))
            Text(
                "Register",
                fontSize = 28.sp,
                color = Color(0xFFFF9900)
            )

            Spacer(modifier = Modifier.size(16.dp))
            OutlinedTextField(
                value = inputName,
                onValueChange = { inputName = it },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Name Icon"
                    )
                },
                supportingText = {
                    if (nameError.isNotEmpty()) {
                        Text(
                            text = nameError,
                            color = Color.Red
                        )
                    }
                },
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.size(16.dp))
            OutlinedTextField(
                value = inputEmail,
                onValueChange = { inputEmail = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = "Email Icon"
                    )
                },
                supportingText = {
                    if (emailError.isNotEmpty()) {
                        Text(
                            text = emailError,
                            color = Color.Red
                        )
                    }
                },
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.size(16.dp))
            OutlinedTextField(
                value = inputPassword,
                onValueChange = { inputPassword = it },
                label = { Text("Contraseña") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Password Icon"
                    )
                },
                supportingText = {
                    if (passwordError.isNotEmpty()) {
                        Text(
                            text = passwordError,
                            color = Color.Red
                        )
                    }
                },
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.size(16.dp))
            OutlinedTextField(
                value = inputPasswordConfirmation,
                onValueChange = { inputPasswordConfirmation = it },
                label = { Text("Confirmar Contraseña") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Password Icon"
                    )
                },
                supportingText = {
                    if (passwordError.isNotEmpty()) {
                        Text(
                            text = passwordError,
                            color = Color.Red
                        )
                    }
                },
                shape = RoundedCornerShape(12.dp)
            )

            if (registerError.isNotEmpty()) {
                Text(registerError, color = Color.Red)
            }

            Spacer(modifier = Modifier.size(16.dp))
            Button(
                onClick = {

                    val isValidName = validateName(inputName).first
                    val isValidEmail = validateEmail(inputEmail).first
                    val isValidPassword = validatePassword(inputPassword).first
                    val isValidConfirmPassword =
                        validateConfirmPassword(inputPassword, inputPasswordConfirmation).first

                    nameError = validateName(inputName).second
                    emailError = validateEmail(inputEmail).second
                    passwordError = validatePassword(inputPassword).second
                    passwordConfirmationError =
                        validateConfirmPassword(inputPassword, inputPasswordConfirmation).second


                    if (isValidName && isValidEmail && isValidPassword && isValidConfirmPassword) {
                        auth.createUserWithEmailAndPassword(inputEmail, inputPassword)
                            .addOnCompleteListener(activity) { task ->
                                if (task.isSuccessful) {
                                    onSuccessfulRegister()
                                } else {
                                    registerError = when (task.exception) {
                                        is FirebaseAuthInvalidCredentialsException -> "Correo invalido"
                                        is FirebaseAuthUserCollisionException -> "Correo ya registrado"
                                        else -> "Error al registrarse"

                                    }
                                }
                            }

                    } else {
                        registerError = "Hubo un error en el register"
                    }

                }, modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFF9900),
                    contentColor = Color.White
                )
            ) {
                Text("Registro")
            }

        }

    }
}