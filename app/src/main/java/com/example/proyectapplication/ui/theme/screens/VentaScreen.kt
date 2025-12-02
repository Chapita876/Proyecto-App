package com.example.proyectapplication.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.proyectapplication.ui.viewmodel.MainViewModel

@Composable
fun VentaScreen(viewModel: MainViewModel, modifier: Modifier = Modifier) {
    // 1. Conectamos con el ViewModel para obtener los datos del carrito y el total
    val carrito by viewModel.carrito.collectAsState()
    val total by viewModel.totalCarrito.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "📃 Registrar Venta",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (carrito.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize().weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text("No hay productos en el carrito")
            }
        } else {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(carrito) { item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("${item.producto.nombre} x${item.cantidad}")
                        Text("$${item.producto.precio * item.cantidad}")
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Divider()
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Total de la venta: $${total}",
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    // Al confirmar, vaciamos el carrito usando el ViewModel
                    viewModel.vaciarCarrito()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Confirmar Venta")
            }
        }
    }
}