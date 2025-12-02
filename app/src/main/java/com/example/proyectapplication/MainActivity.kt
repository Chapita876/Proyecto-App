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
import com.example.proyectapplication.data.network.ApiService
import com.example.proyectapplication.data.repository.AppRepository
import com.example.proyectapplication.ui.screens.CarritoScreen
import com.example.proyectapplication.ui.screens.ClienteScreen
import com.example.proyectapplication.ui.screens.HomeScreen
import com.example.proyectapplication.ui.screens.VentaScreen
import com.example.proyectapplication.ui.viewmodel.MainViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // --- CONEXIÓN CON MICROSERVICIOS (SPRING BOOT) ---

        // 1. Definimos la URL de tu servidor local.
        // "10.0.2.2" es una dirección especial que usa el Emulador de Android
        // para referirse a "localhost" de tu computadora.
        val BASE_URL = "http://10.0.2.2:8080/api/v1/"

        // 2. Configuramos Retrofit para que hable con esa URL
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()) // Convierte el JSON a tus objetos Kotlin
            .build()

        // 3. Creamos el servicio de API real
        val apiService = retrofit.create(ApiService::class.java)

        // 4. Pasamos el servicio real al repositorio
        val repository = AppRepository(apiService)

        // 5. Inicializamos el ViewModel con el repositorio conectado
        val viewModel = MainViewModel(repository)

        setContent {
            ProyectApplicationApp(viewModel)
        }
    }
}

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
            // Pasamos el viewModel a TODAS las pantallas que lo necesitan
            composable("home") { HomeScreen(viewModel = viewModel) }

            // --- CORRECCIÓN AQUÍ ---
            // Antes fallaba porque no le pasabas el viewModel
            composable("carrito") { CarritoScreen(viewModel = viewModel) }
            composable("venta") { VentaScreen(viewModel = viewModel) }
            // -----------------------

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