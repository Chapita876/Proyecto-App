package com.example.proyectapplication.ui.screens

import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.proyectapplication.models.Carro

@Composable
fun VentaScreen(modifier: Modifier = Modifier) {
    var total by remember { mutableStateOf(Carro.CalcularPrecio()) }


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

        if (Carro.item.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("No hay productos en el carrito")
            }
        } else {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(Carro.item) { carrito ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("${carrito.producto.nombre} x${carrito.cantidad}")
                        Text("$${carrito.producto.precio * carrito.cantidad}")
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Total de la venta: $${total}",
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    println("Venta confirmada por un total de $$total")
                    Carro.Limpiar()
                    total = Carro.CalcularPrecio()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Confirmar Venta")
            }
        }
    }


}
