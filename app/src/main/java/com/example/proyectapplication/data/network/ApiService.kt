package com.example.proyectapplication.data.network

import com.example.proyectapplication.models.Cliente
import com.example.proyectapplication.models.Producto
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    // Coincide con @GetMapping("/productos") en tu ProductoController
    // La ruta base en MainActivity ya incluirá /api/v1/
    @GET("productos")
    suspend fun obtenerProductos(): Response<List<Producto>>

    // Coincide con @PostMapping("/clientes") en ClienteController
    @POST("clientes")
    suspend fun crearCliente(@Body cliente: Cliente): Response<Cliente>

    // Coincide con @PutMapping("/clientes/{id}")
    @PUT("clientes/{id}")
    suspend fun actualizarCliente(@Path("id") id: String, @Body cliente: Cliente): Response<Cliente>

    // Coincide con @GetMapping("/clientes/buscar")
    @GET("clientes/buscar")
    suspend fun buscarCliente(@Query("email") email: String): Response<Cliente>
}