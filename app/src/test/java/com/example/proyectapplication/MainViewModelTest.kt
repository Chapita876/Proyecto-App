package com.example.proyectapplication

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.proyectapplication.data.repository.AppRepository
import com.example.proyectapplication.models.Cliente
import com.example.proyectapplication.models.Producto
import com.example.proyectapplication.ui.viewmodel.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
// --- CAMBIO IMPORTANTE: Usamos los imports de mockito-kotlin ---
import org.mockito.kotlin.any
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: MainViewModel

    @Mock
    private lateinit var repository: AppRepository

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = MainViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `obtenerProductosBackend actualiza lista cuando es exitoso`() = runTest {
        val listaMock = listOf(Producto(1, "Test", 100.0,  0))

        // Usamos 'whenever' en lugar de `when` para evitar conflictos y errores
        whenever(repository.getProductos()).thenReturn(Result.success(listaMock))

        viewModel.obtenerProductosBackend()

        assertEquals(listaMock, viewModel.productos.value)
    }

    @Test
    fun `guardarCliente con datos invalidos muestra error`() = runTest {
        viewModel.guardarCliente("", "", "")

        assertEquals("Datos inválidos", viewModel.mensajeUsuario.value)

        // Aquí 'any()' ya no fallará porque es la versión segura de Kotlin
        verify(repository, never()).guardarCliente(any())
    }

    @Test
    fun `guardarCliente exitoso actualiza estado cliente`() = runTest {
        val cliente = Cliente("01", "juan", "juan@test.com", "978456324")

        // Usamos 'whenever' y 'any()' seguro
        whenever(repository.guardarCliente(any())).thenReturn(Result.success(cliente))

        viewModel.guardarCliente("Juan", "juan@test.com", "123")

        assertEquals(cliente, viewModel.clienteActual.value)
        assertEquals("Cliente guardado con éxito", viewModel.mensajeUsuario.value)
    }
}