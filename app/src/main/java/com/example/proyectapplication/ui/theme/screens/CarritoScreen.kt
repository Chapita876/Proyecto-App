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
import com.example.proyectapplication.models.Carro

@Composable
fun CarritoScreen(modifier: Modifier = Modifier) {
    var carritoCount by remember { mutableStateOf(Carro.item.size) }
    var total by remember { mutableStateOf(Carro.CalcularPrecio()) }


    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "🛒 Tu Carrito",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (Carro.item.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Tu carrito está vacío")
            }
        } else {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(Carro.item) { carrito ->
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
                            Image(
                                painter = painterResource(id = carrito.producto.imagen),
                                contentDescription = carrito.producto.nombre,
                                modifier = Modifier
                                    .size(80.dp)
                                    .padding(end = 8.dp)
                            )

                            Column(modifier = Modifier.weight(1f)) {
                                Text(text = carrito.producto.nombre)
                                Text(text = "Cantidad: ${carrito.cantidad}")
                                Text(text = "Precio: $${carrito.producto.precio}")
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Total: $${total}",
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = {
                    Carro.Limpiar()
                    carritoCount = Carro.item.size
                    total = Carro.CalcularPrecio()
                }) {
                    Text("Vaciar carrito")
                }

                Button(onClick = {
                    println("Compra confirmada por un total de $$total")
                    Carro.Limpiar()
                    carritoCount = Carro.item.size
                    total = Carro.CalcularPrecio()
                }) {
                    Text("Comprar")
                }
            }
        }
    }


}
