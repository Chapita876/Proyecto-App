package com.example.proyectapplication.models

data class Producto (
    val id: Int,
    val nombre: String,
    val precio: Double,

    ){

    fun mostrarInfo(){
        println("Producto: $nombre, $precio, $id")
    }

}