package com.example.proyectapplication.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.proyectapplication.R
import androidx.compose.ui.res.painterResource
import com.example.proyectapplication.ui.viewmodel.MainViewModel

@Composable
fun CarritoScreen(viewModel: MainViewModel, modifier: Modifier = Modifier) {
    // 1. Observamos los datos desde el ViewModel (La única fuente de verdad)
    val listaCarrito by viewModel.carrito.collectAsState()
    val total by viewModel.totalCarrito.collectAsState()

    Column(
        modifier = modifier.fillMaxSize().padding(16.dp)
    ) {
        Text("🛒 Tu Carrito", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        if (listaCarrito.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize().weight(1f), contentAlignment = Alignment.Center) {
                Text("Tu carrito está vacío 😔")
            }
        } else {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(listaCarrito) { item ->
                    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                        Row(modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
                            AsyncImage(
                                model = item.producto.imagen,
                                contentDescription = null,
                                modifier = Modifier.size(60.dp).padding(end = 8.dp),
                                placeholder = painterResource(R.drawable.placeholder),
                                error = painterResource(R.drawable.placeholder)
                            )
                            Column(modifier = Modifier.weight(1f)) {
                                Text(item.producto.nombre, style = MaterialTheme.typography.titleMedium)
                                Text("Cant: ${item.cantidad} | $${item.producto.precio}")
                            }
                            Text("$${item.producto.precio * item.cantidad}", style = MaterialTheme.typography.titleMedium)
                        }
                    }
                }
            }

            Divider()

            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Total:", style = MaterialTheme.typography.headlineSmall)
                Text("$${total}", style = MaterialTheme.typography.headlineSmall)
            }

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedButton(onClick = { viewModel.vaciarCarrito() }, modifier = Modifier.weight(1f)) {
                    Text("Vaciar")
                }
                Button(onClick = {
                    viewModel.vaciarCarrito()
                    // Aquí podrías añadir un mensaje de éxito
                }, modifier = Modifier.weight(1f)) {
                    Text("Comprar")
                }
            }
        }
    }
}