package com.example.proyectapplication.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.proyectapplication.models.Usuario
import com.example.proyectapplication.models.UsuarioMag

@Composable
fun UsuarioScreen(modifier: Modifier = Modifier) {
    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var edad by remember { mutableStateOf("") }
    var contra by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf("") }
    var correoBuscar by remember { mutableStateOf("") }
    var correoEliminar by remember { mutableStateOf("") }


    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("👤 Gestión de Usuarios", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        // Crear usuario
        Text("Registrar Usuario", style = MaterialTheme.typography.titleMedium)
        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = correo,
            onValueChange = { correo = it },
            label = { Text("Correo") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = edad,
            onValueChange = { edad = it },
            label = { Text("Edad") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = contra,
            onValueChange = { contra = it },
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = {
                val usuario = Usuario(nombre, correo, edad.toIntOrNull() ?: 0, contra)
                val creado = UsuarioMag.crearUsuario(usuario)
                mensaje = if (creado) "Usuario creado con éxito" else "Error al crear usuario"
            },
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Text("Crear Usuario")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Buscar usuario
        Text("Buscar Usuario", style = MaterialTheme.typography.titleMedium)
        OutlinedTextField(
            value = correoBuscar,
            onValueChange = { correoBuscar = it },
            label = { Text("Correo a buscar") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = {
                val encontrado = UsuarioMag.buscarUsuario(correoBuscar)
                mensaje = encontrado?.let { "Usuario encontrado: $it" } ?: "Usuario no encontrado"
            },
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Text("Buscar Usuario")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Eliminar usuario
        Text("Eliminar Usuario", style = MaterialTheme.typography.titleMedium)
        OutlinedTextField(
            value = correoEliminar,
            onValueChange = { correoEliminar = it },
            label = { Text("Correo a eliminar") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = {
                val eliminado = UsuarioMag.eliminarUsuario(correoEliminar)
                mensaje = if (eliminado) "Usuario eliminado" else "Usuario no encontrado"
            },
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Text("Eliminar Usuario")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Mensaje de salida
        if (mensaje.isNotEmpty()) {
            Text(mensaje, style = MaterialTheme.typography.bodyLarge)
        }
    }


}
