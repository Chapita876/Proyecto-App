package com.example.proyectapplication.models

data class Usuario (
    val nombre: String,
    val correo: String,
    val edad: Int,
    val contra: String
)
object UsuarioMag{
    private val user= mutableListOf<Usuario>()

    fun crearUsuario(usuario: Usuario): Boolean{
        val existe= user.any{it.correo == usuario.correo}
        return if (existe){
            println("El usuario ya existe")
            false
        }else{
            user.add(usuario)
            println("El usuario ha sido guardado")
            false
        }
    }

    fun eliminarUsuario(correo: String): Boolean{
        val usuario = user.find { it.correo == correo }
        return if (usuario!=null){
            user.remove(usuario)
            println("El usuario ha sido eliminado")
            true
        }else{
           println("Usuario no encontrado")
           false
        }
    }

    fun buscarUsuario(correo: String): Usuario?{
        val usuario = user.find { it.correo == correo }
        if (usuario != null){
            println("Usuario encontrado: $usuario")
        }else{
            println("Error. Usuario no encontrado")
        }
        return usuario

    }
}


