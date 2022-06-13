package baseDeDatos

import inventario.Inventario
import tienda.Tienda

class VisualizadorDeDatos(private val gestor: MiTienda) {


    /**
     * Funcion que sirve para visualizar tiendas. Recibira la lista de gestor y luego utilizara la funcion privada
     *  para mostrarlo en pantalla.
     */

    fun visTodasLasTiendas() {
        val visTienda = gestor.listaDeTodasLasTiendas()
        mostrarPorPantallaTodasLasTiendas(visTienda)
    }

    private fun mostrarPorPantallaTodasLasTiendas(lista: List<Tienda>) {
        println("---------------------------------------------------------")
        println("MOSTRAR TODAS LAS TIENDAS:")
        lista.forEach {
            println("---------------------------------------------------------")
            println("ID de la tienda: ${it.id}")
            println("Nombre de la tienda: ${it.nombre}")
            println("Direccion de la tienda: ${it.direccion}")
            println("---------------------------------------------------------")
        }
    }

    /**
     * Funcion que sirve para visualizar inventarios. Recibira la lista de gestor y luego utilizara la funcion privada
     *  para mostrarlo en pantalla.
     */
    fun visTodosLosInvPorTiendas() {
        val visInv = gestor.listaDeTodosLosInvPorTienda()
        mostrarPorPantallaLosInventariosPorTienda(visInv)

    }

    private fun mostrarPorPantallaLosInventariosPorTienda(lista: List<Inventario>) {
        println("---------------------------------------------------------")
        println("MOSTRAR TODOS LOS INVENTARIOS ORDENADOS POR ID_TIENDA:")
        lista.forEach {
            println("---------------------------------------------------------")
            println("ID del articulo: ${it.idArticulo}")
            println("Nombre del articulo: ${it.nombre}")
            println("Comentario del articulo: ${it.comentario}")
            println("Precio del articulo: ${it.precio}")
            println("ID de la tienda que tiene el articulo: ${it.idTienda}")
            println("---------------------------------------------------------")
        }
    }

}