package com.example.proyectapplication.models


data class Venta(
    val productos: List<Producto>,
    val total: Double
)