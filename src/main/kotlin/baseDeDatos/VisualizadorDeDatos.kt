package baseDeDatos

import inventario.InventarioDao
import tienda.TiendaDao
import baseDeDatos.GestorDeDatos

class VisualizadorDeDatos() {

    val gestor = GestorDeDatos()

    /**
     * Funcion que sirve para visualizar tiendas. Tiene que recibir una lista para que pueda visualizarla correctamente.
     */

    fun visTodasLasTiendas(){
        val visTienda = gestor.listaDeTodasLasTiendas()
        println("---------------------------------------------------------")
        println("MOSTRAR TODAS LAS TIENDAS:")
        visTienda.forEach {
            println("---------------------------------------------------------")
            println("ID de la tienda: ${it.id}")
            println("Nombre de la tienda: ${it.nombre}")
            println("Direccion de la tienda: ${it.direccion}")
            println("---------------------------------------------------------")
        }
    }

    /**
     * Funcion que sirve para visualizar inventarios. Tiene que recibir una lista para que pueda visualizarla correctamente.
     */
    fun visTodosLosInvPorTiendas() {
        val visInv = gestor.listaDeTodosLosInvPorTienda()
        println("---------------------------------------------------------")
        println("MOSTRAR TODOS LOS INVENTARIOS ORDENADOS POR ID_TIENDA:")
        visInv.forEach {
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