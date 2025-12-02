package com.example.proyectapplication.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage // Importante: Coil para cargar imágenes desde URL
import com.example.proyectapplication.R
import com.example.proyectapplication.ui.viewmodel.MainViewModel

@Composable
fun HomeScreen(viewModel: MainViewModel, modifier: Modifier = Modifier) {
    // 1. Observamos la lista de productos que viene del ViewModel (Backend/Repo)
    val productos by viewModel.productos.collectAsState()

    // Opcional: También podrías observar el estado del carrito si quisieras mostrar un contador aquí
    // val carrito by viewModel.carrito.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "🍎 Mercado en línea (MVVM)",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Si la lista está vacía (quizás cargando o error), mostramos un indicador
        if (productos.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxWidth().weight(1f),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(productos) { producto ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Lógica para mostrar imagen: Priorizamos URL (Backend), si falla usamos placeholder
                            AsyncImage(
                                model = producto.imagen,
                                contentDescription = producto.nombre,
                                modifier = Modifier
                                    .size(80.dp)
                                    .padding(end = 8.dp),
                                placeholder = painterResource(R.drawable.placeholder), // Tu imagen temporal
                                error = painterResource(R.drawable.placeholder) // Si falla la carga
                            )

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = producto.nombre,
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Text(
                                    text = "Precio: $${producto.precio}",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }

                            Button(onClick = {
                                // --- AQUÍ ESTÁ EL CAMBIO CLAVE ---
                                // Usamos el ViewModel para agregar al carrito
                                viewModel.agregarAlCarrito(producto)
                            }) {
                                Text("Agregar")
                            }
                        }
                    }
                }
            }
        }
    }
}