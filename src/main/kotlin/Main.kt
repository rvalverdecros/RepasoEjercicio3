import baseDeDatos.MiTienda
import baseDeDatos.VisualizadorDeDatos

fun main() {
    val gestor = MiTienda()

    //gestor.cambiarPrecioParaMayoresDe(2000.00,15.00)

    val visulizador = VisualizadorDeDatos(gestor)

    visulizador.visTodasLasTiendas()
    visulizador.visTodosLosInvPorTiendas()

    gestor.close()
}