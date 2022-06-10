import baseDeDatos.GestorDeDatos
import baseDeDatos.VisualizadorDeDatos

fun main() {
    val gestor = GestorDeDatos()
//gestor.cambiarPrecioParaMayoresDe(2000,15)

    val visulizador = VisualizadorDeDatos()

    visulizador.visTodasLasTiendas()
    visulizador.visTodosLosInvPorTiendas()

    gestor.close()
}