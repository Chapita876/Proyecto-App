package com.example.proyectapplication.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.proyectapplication.R // Asegúrate de que este import sea correcto para tu paquete
import com.example.proyectapplication.ui.viewmodel.MainViewModel
// Si tienes un objeto Carro global y quieres seguir usándolo para agregar items, descomenta abajo:
// import com.example.proyectapplication.models.Carro

@Composable
fun HomeScreen(viewModel: MainViewModel, modifier: Modifier = Modifier) {
    // 1. Observamos la lista de productos que viene del ViewModel (Backend/Repo)
    val productos by viewModel.productos.collectAsState()

    // Nota: Hemos eliminado 'carritoCount' local para evitar el error.
    // Si quisieras mostrar el contador del carrito, deberías gestionarlo en el ViewModel
    // o leer el tamaño de tu lista global Carro.item.size si decides mantener esa lógica mixta.

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

        // Si la lista está vacía (quizás cargando o error), mostramos un mensaje
        if (productos.isEmpty()) {
            Box(modifier = Modifier.fillMaxWidth().weight(1f), contentAlignment = Alignment.Center) {
                CircularProgressIndicator() // O Text("Cargando productos...")
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
                            // Lógica para mostrar imagen: Si viene del backend (URL) vs Local (Recurso)
                            // Por ahora, como el Repo simulado usa recursos locales (R.drawable...), usamos eso.
                            val imagen = if (producto.imagen != 0) producto.imagen else R.drawable.placeholder

                            Image(
                                painter = painterResource(id = imagen),
                                contentDescription = producto.nombre,
                                modifier = Modifier
                                    .size(80.dp)
                                    .padding(end = 8.dp)
                            )

                            Column(modifier = Modifier.weight(1f)) {
                                Text(text = producto.nombre, style = MaterialTheme.typography.titleMedium)
                                Text(text = "Precio: $${producto.precio}", style = MaterialTheme.typography.bodyMedium)
                            }

                            Button(onClick = {
                                // AQUÍ: Lógica para agregar al carrito.
                                // Si sigues usando tu objeto global Carro:
                                // Carro.AgregarProd(producto)
                                // println("Producto agregado: ${producto.nombre}")

                                // Idealmente, esto debería ser una llamada al ViewModel:
                                // viewModel.agregarAlCarrito(producto)
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