package com.example.proyectapplication.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectapplication.data.repository.AppRepository
import com.example.proyectapplication.models.Cliente
import com.example.proyectapplication.models.Producto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val repository: AppRepository) : ViewModel() {

    // Estado de Productos
    private val _productos = MutableStateFlow<List<Producto>>(emptyList())
    val productos: StateFlow<List<Producto>> = _productos.asStateFlow()

    // Estado de Cliente (Operaciones)
    private val _clienteActual = MutableStateFlow<Cliente?>(null)
    val clienteActual: StateFlow<Cliente?> = _clienteActual.asStateFlow()

    private val _mensajeUsuario = MutableStateFlow<String>("")
    val mensajeUsuario: StateFlow<String> = _mensajeUsuario.asStateFlow()

    // Cargar productos al iniciar
    init {
        obtenerProductosBackend()
    }

    fun obtenerProductosBackend() {
        viewModelScope.launch {
            val resultado = repository.getProductos()
            resultado.onSuccess { lista ->
                _productos.value = lista
            }.onFailure {
                _mensajeUsuario.value = "Error al cargar productos"
            }
        }
    }

    // CRUD Cliente: Crear o Actualizar
    fun guardarCliente(nombre: String, email: String, telefono: String) {
        if (nombre.isBlank() || email.isBlank()) {
            _mensajeUsuario.value = "Datos inválidos"
            return
        }

        val cliente = Cliente(nombre = nombre, email = email, telefono = telefono)

        viewModelScope.launch {
            val resultado = repository.guardarCliente(cliente)
            resultado.onSuccess {
                _clienteActual.value = it
                _mensajeUsuario.value = "Cliente guardado con éxito"
            }.onFailure {
                _mensajeUsuario.value = "Error: ${it.message}"
            }
        }
    }

    // CRUD Cliente: Obtener
    fun buscarCliente(email: String) {
        viewModelScope.launch {
            val resultado = repository.buscarCliente(email)
            resultado.onSuccess {
                _clienteActual.value = it
                _mensajeUsuario.value = "Cliente encontrado"
            }.onFailure {
                _clienteActual.value = null
                _mensajeUsuario.value = "Cliente no encontrado"
            }
        }
    }

    fun limpiarMensaje() {
        _mensajeUsuario.value = ""
    }
}