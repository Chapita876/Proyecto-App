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
import com.example.proyectapplication.R
import com.example.proyectapplication.Utils.DataProvider
import com.example.proyectapplication.models.Carro
import com.example.proyectapplication.models.Producto

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    var carritoCount by remember { mutableStateOf(Carro.item.size) }


    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "🍎 Mercado en línea",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(DataProvider.productosPrueba) { producto: Producto ->
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
                            painter = painterResource(id = producto.imagen),
                            contentDescription = producto.nombre,
                            modifier = Modifier
                                .size(80.dp)
                                .padding(end = 8.dp)
                        )

                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = producto.nombre)
                            Text(text = "Precio: $${producto.precio}")
                        }

                        Button(onClick = {
                            val agregado = Carro.AgregarProd(producto)
                            if (!agregado) {
                                println("Ups. Este producto ya ha sido agregado")
                            }
                            carritoCount = Carro.item.size
                        }) {
                            Text("Agregar")
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Productos en carrito: $carritoCount",
            style = MaterialTheme.typography.bodyLarge
        )
    }


}
