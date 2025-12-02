package com.example.proyectapplication.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.proyectapplication.ui.viewmodel.MainViewModel

@Composable
fun ClienteScreen(viewModel: MainViewModel, modifier: Modifier = Modifier) {
    // 1. Observamos los estados del ViewModel
    val clienteActual by viewModel.clienteActual.collectAsState()
    val mensaje by viewModel.mensajeUsuario.collectAsState()

    // 2. Variables locales para el formulario
    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }


    LaunchedEffect(clienteActual) {
        clienteActual?.let {
            nombre = it.nombre
            email = it.email
            telefono = it.telefono
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "👤 Gestión de Clientes",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Buscar Cliente Existente",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email para buscar") },
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                    Button(
                        onClick = { viewModel.buscarCliente(email) }
                    ) {
                        Text("🔍")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Datos del Cliente",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre Completo") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo Electrónico (ID)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = telefono,
            onValueChange = { telefono = it },
            label = { Text("Teléfono") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))


        Button(
            onClick = {

                viewModel.guardarCliente(nombre, email, telefono)
            },
            modifier = Modifier.fillMaxWidth().height(50.dp)
        ) {
            Text(if (clienteActual == null) "Crear Nuevo Cliente" else "Actualizar Cliente")
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(
            onClick = {
                nombre = ""
                email = ""
                telefono = ""
                viewModel.limpiarMensaje()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Limpiar Formulario")
        }

        if (mensaje.isNotEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))


            val esError = mensaje.contains("Error", ignoreCase = true) || mensaje.contains("inválidos", ignoreCase = true)

            val colorFondo = if (esError) MaterialTheme.colorScheme.errorContainer else Color(0xFFE8F5E9)
            val colorTexto = if (esError) MaterialTheme.colorScheme.onErrorContainer else Color(0xFF1B5E20)

            Card(
                colors = CardDefaults.cardColors(containerColor = colorFondo),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = mensaje,
                    color = colorTexto,
                    modifier = Modifier.padding(16.dp),
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}