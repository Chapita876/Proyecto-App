package com.example.proyectapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.proyectapplication.data.repository.AppRepository
import com.example.proyectapplication.ui.screens.CarritoScreen
import com.example.proyectapplication.ui.screens.ClienteScreen
import com.example.proyectapplication.ui.screens.HomeScreen
import com.example.proyectapplication.ui.screens.VentaScreen
import com.example.proyectapplication.ui.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. Inicializamos el Repositorio (simulando API null por ahora)
        val repository = AppRepository(null)

        // 2. Inicializamos el ViewModel con el repositorio
        val viewModel = MainViewModel(repository)

        setContent {
            // 3. Le pasamos el viewModel a la función principal
            ProyectApplicationApp(viewModel)
        }
    }
}

// CORRECCIÓN AQUÍ: Agregamos 'viewModel: MainViewModel' como parámetro
@Composable
fun ProyectApplicationApp(viewModel: MainViewModel) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            // Pasamos el viewModel a las pantallas que lo necesitan
            composable("home") { HomeScreen(viewModel = viewModel) }
            composable("carrito") { CarritoScreen() }
            composable("venta") { VentaScreen() }

            // Usamos ClienteScreen en lugar de UsuarioScreen
            composable("usuario") { ClienteScreen(viewModel = viewModel) }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    NavigationBar {
        NavigationBarItem(
            icon = { Text("🏠") },
            label = { Text("Home") },
            selected = false, // Puedes mejorar la lógica de selección después
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