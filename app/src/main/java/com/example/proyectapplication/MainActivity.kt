package com.example.proyectapplication


import androidx.compose.foundation.layout.padding
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.proyectapplication.ui.screens.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProyectApplicationApp()
        }
    }
}

@Composable
fun ProyectApplicationApp() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home") { HomeScreen() }
            composable("carrito") { CarritoScreen() }
            composable("venta") { VentaScreen() }
            composable("usuario") { UsuarioScreen() }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    NavigationBar {
        NavigationBarItem(
            icon = { Text("🏠") },
            label = { Text("Home") },
            selected = false,
            onClick = { navController.navigate("home") }
        )
        NavigationBarItem(
            icon = { Text("🛒") },
            label = { Text("Carrito") },
            selected = false,
            onClick = { navController.navigate("carrito") }
        )
        NavigationBarItem(
            icon = { Text("📃") },
            label = { Text("Venta") },
            selected = false,
            onClick = { navController.navigate("venta") }
        )
        NavigationBarItem(
            icon = { Text("👤") },
            label = { Text("Usuario") },
            selected = false,
            onClick = { navController.navigate("usuario") }
        )
    }
}
