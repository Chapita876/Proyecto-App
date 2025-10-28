package com.example.proyectapplication.models

data class Carrito (
    val producto: Producto,
    val cantidad: Int

)

object Carro{
    val item = mutableListOf<Carrito>()

    fun AgregarProd(producto: Producto){
        val existe= item.find { it.producto.id == producto.id }

        return if(existe!=null){
            println("Ups. El producto ya existe")
        }else{
            item.add(Carrito(producto,1))
            println("Producto agregado")
        }
    }

    fun CalcularPrecio(): Double{
        return item.sumOf { it.producto.precio * it.cantidad }
    }

    fun Limpiar(){
        item.clear()
    }
}