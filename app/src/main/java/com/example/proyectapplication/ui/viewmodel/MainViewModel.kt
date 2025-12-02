package com.example.proyectapplication.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectapplication.data.repository.AppRepository
import com.example.proyectapplication.models.Carrito
import com.example.proyectapplication.models.Cliente
import com.example.proyectapplication.models.Producto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val repository: AppRepository) : ViewModel() {

    private val _productos = MutableStateFlow<List<Producto>>(emptyList())
    val productos: StateFlow<List<Producto>> = _productos.asStateFlow()

    private val _clienteActual = MutableStateFlow<Cliente?>(null)
    val clienteActual: StateFlow<Cliente?> = _clienteActual.asStateFlow()

    private val _mensajeUsuario = MutableStateFlow<String>("")
    val mensajeUsuario: StateFlow<String> = _mensajeUsuario.asStateFlow()


    private val _carrito = MutableStateFlow<List<Carrito>>(emptyList())
    val carrito: StateFlow<List<Carrito>> = _carrito.asStateFlow()


    private val _totalCarrito = MutableStateFlow(0.0)
    val totalCarrito: StateFlow<Double> = _totalCarrito.asStateFlow()

    init {
        obtenerProductosBackend()
    }

    fun obtenerProductosBackend() {
        viewModelScope.launch {
            val resultado = repository.getProductos()
            resultado.onSuccess { lista ->
                _productos.value = lista
            }.onFailure {
                _mensajeUsuario.value = "Error al cargar productos: ${it.message}"
            }
        }
    }

    //Cliente
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

//Carrito
    fun agregarAlCarrito(producto: Producto) {
        // Creamos una copia modificable de la lista actual
        val listaActual = _carrito.value.toMutableList()

        // Buscamos si el producto ya está en el carrito
        val itemExistente = listaActual.find { it.producto.id == producto.id }

        if (itemExistente != null) {
            // Si ya existe, aumentamos la cantidad en 1
            val index = listaActual.indexOf(itemExistente)
            listaActual[index] = itemExistente.copy(cantidad = itemExistente.cantidad + 1)
        } else {
            // Si no existe, lo agregamos nuevo con cantidad 1
            listaActual.add(Carrito(producto, 1))
        }

        _carrito.value = listaActual
        calcularTotal()
        _mensajeUsuario.value = "Producto agregado: ${producto.nombre}"
    }

    fun vaciarCarrito() {
        _carrito.value = emptyList()
        calcularTotal()
        _mensajeUsuario.value = "Carrito vaciado"
    }

    private fun calcularTotal() {
        _totalCarrito.value = _carrito.value.sumOf { it.producto.precio * it.cantidad }
    }
}