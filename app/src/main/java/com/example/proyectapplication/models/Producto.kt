package com.example.proyectapplication.models

data class Producto (
    val id: Int,
    val nombre: String,
    val precio: Double,
    val imagen: Int

    ){

    fun mostrarInfo(){
        println("Producto: $nombre, $precio, $id")
    }

}