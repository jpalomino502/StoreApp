package com.jpalomino502.storeapp.ui.validation

fun validateEmail(email:String): Pair<Boolean, String> {
    return when{
        email.isEmpty() -> Pair(false, "El correo es requerido")
        else -> {
            Pair(true, "")
        }
    }
}

fun validatePassword(password:String): Pair<Boolean, String> {
    return when{
        password.isEmpty() -> Pair(false, "La contraseña es requerida" +
                " " +
                "")
        else -> {
            Pair(true, "")
        }
    }
}