package com.example.proyectapplication.data.network

import com.example.proyectapplication.models.Cliente
import com.example.proyectapplication.models.Producto
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("productos")
    suspend fun obtenerProductos(): Response<List<Producto>>


    @POST("clientes")
    suspend fun crearCliente(@Body cliente: Cliente): Response<Cliente>


    @PUT("clientes/{id}")
    suspend fun actualizarCliente(@Path("id") id: String, @Body cliente: Cliente): Response<Cliente>


    @GET("clientes/{email}")
    suspend fun obtenerCliente(@Path("email") email: String): Response<Cliente>
}