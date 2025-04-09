package com.jpalomino502.storeapp.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jpalomino502.storeapp.ui.screen.ForgetPassword
import com.jpalomino502.storeapp.ui.screen.LoginScreen
import com.jpalomino502.storeapp.ui.screen.RegisterScreem

@Composable
fun NavigationApp(){
    val myNavController = rememberNavController()
    val myStartDestination: String = "login"

    NavHost(
        navController = myNavController,
        startDestination =  myStartDestination,
    ){
        composable("login"){
            LoginScreen(myNavController)
        }
        composable("register"){
            RegisterScreem(myNavController)
        }
        composable("forgetPassword"){
            ForgetPassword(myNavController)
        }
    }

}
