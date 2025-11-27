package com.example.proyectapplication.data.repository

import com.example.proyectapplication.data.network.ApiService
import com.example.proyectapplication.models.Cliente
import com.example.proyectapplication.models.Producto
import com.example.proyectapplication.R // Asegúrate de importar tu R

class AppRepository(private val apiService: ApiService?) {

    // Simulación de "Backend" local si la API es null (para el ejemplo)
    private val productosLocales = listOf(
        Producto(1, "Manzana", 550.0, imagen = R.drawable.manzana),
        Producto(2, "Platano", 300.0, imagen = R.drawable.platanos),
        Producto(3, "Zanahoria", 150.0, imagen = R.drawable.zanahoria)
    )

    // Simulación de base de datos de clientes en memoria
    private val clientesEnMemoria = mutableListOf<Cliente>()

    suspend fun getProductos(): Result<List<Producto>> {
        return try {
            // Intento llamar a la API
            if (apiService != null) {
                val response = apiService.obtenerProductos()
                if (response.isSuccessful) {
                    Result.success(response.body() ?: emptyList())
                } else {
                    Result.success(productosLocales) // Fallback a local
                }
            } else {
                Result.success(productosLocales) // Si no hay API configurada
            }
        } catch (e: Exception) {
            Result.success(productosLocales) // En caso de error de red, mostramos locales
        }
    }

    suspend fun guardarCliente(cliente: Cliente): Result<Cliente> {
        return try {
            // Lógica: Si tiene ID, actualizamos. Si no, creamos.
            // Simulamos backend aquí
            val existenteIndex = clientesEnMemoria.indexOfFirst { it.email == cliente.email }

            if (existenteIndex != -1) {
                // Actualizar
                clientesEnMemoria[existenteIndex] = cliente
                Result.success(cliente)
            } else {
                // Crear
                clientesEnMemoria.add(cliente)
                Result.success(cliente)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun buscarCliente(email: String): Result<Cliente> {
        val cliente = clientesEnMemoria.find { it.email == email }
        return if (cliente != null) Result.success(cliente)
        else Result.failure(Exception("Cliente no encontrado"))
    }
}