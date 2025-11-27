package com.example.proyectapplication.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.proyectapplication.ui.viewmodel.MainViewModel

@Composable
fun ClienteScreen(viewModel: MainViewModel, modifier: Modifier = Modifier) {
    // Observamos el estado del ViewModel
    val clienteActual by viewModel.clienteActual.collectAsState()
    val mensaje by viewModel.mensajeUsuario.collectAsState()

    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }

    // Si buscamos y encontramos un cliente, llenamos los campos (Actualizar)
    LaunchedEffect(clienteActual) {
        clienteActual?.let {
            nombre = it.nombre
            email = it.email
            telefono = it.telefono
        }
    }

    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
        Text("👤 Gestión de Clientes (MVVM)", style = MaterialTheme.typography.headlineMedium)

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email (ID)") },
            modifier = Modifier.fillMaxWidth()
        )

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { viewModel.buscarCliente(email) }, modifier = Modifier.weight(1f)) {
                Text("🔍 Buscar")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = telefono, onValueChange = { telefono = it }, label = { Text("Teléfono") }, modifier = Modifier.fillMaxWidth())

        Button(
            onClick = { viewModel.guardarCliente(nombre, email, telefono) },
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
        ) {
            Text(if (clienteActual == null) "Crear Cliente" else "Actualizar Cliente")
        }

        if (mensaje.isNotEmpty()) {
            Text(text = mensaje, color = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(top = 16.dp))
        }
    }
}