package com.example.proyectapplication.models

data class Venta (
    val producto: List<Producto>,
    val cantidades : List<Int>,
    val total: Double
)

class Ventita{
    companion object{
        fun realizarVenta(): Venta?{
            if (Carro.item.isEmpty()){
                println("El carrito es vacio ")
                return null
            }
            val total = Carro.CalcularPrecio()
            val venta = Venta(
                producto = Carro.item.map { it.producto },
                cantidades = Carro.item.map { it.cantidad },
                total= total

            )
            println("Venta realizada con exito")
            Carro.Limpiar()

            return venta



        }
    }
}