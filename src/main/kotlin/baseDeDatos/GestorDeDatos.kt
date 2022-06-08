package baseDeDatos

import inventario.Inventario
import inventario.InventarioDao
import tienda.Tienda
import tienda.TiendaDao
import java.util.logging.Level
import java.util.logging.LogManager

internal val l = LogManager.getLogManager().getLogger("").apply { level = Level.ALL }

class GestorDeDatos {

    private val c = ConnectionBuilder()
     val conInvDAO = InventarioDao(c.connection)
     val conTienDAO = TiendaDao(c.connection)

    init {
        connect()
        creacionDeTablas()
    }

     private fun connect():Boolean{

     l.info("Conectando!")
        var conection = true
     val c = ConnectionBuilder()
     if (c.connection.isValid(10)){
     }else{
         conection = false
     }
        return conection
    }

    fun close() {
        l.info("Cerrando Base de Datos.....")
        val c = ConnectionBuilder()
        c.connection.close()
    }

    private fun creacionDeTablas() {

        tablaInv(conInvDAO)

        tablaTienda(conTienDAO)
    }


     fun cambiarPrecioParaMayoresDe(umbral:Double, porcentaje:Double): Boolean{

            val calculo = porcentaje / 100
            val todoinv = conInvDAO.selectAll()
            todoinv.forEach {
                val precio = it.precio
                if (precio > umbral) {
                    val newprice = precio + (precio * calculo)
                    conInvDAO.update(
                        Inventario(it.idArticulo, it.nombre, it.comentario, newprice, it.idTienda)
                    )
                }
        }
        return true
    }


    private fun tablaTienda(contienda: TiendaDao) {
        if (contienda.prepareTable()) {
            contienda.insert(Tienda(1, "La Nena", "Callejon de la Nena #123, Colonia Dulce Amor"))
            contienda.insert(Tienda(2, "La Virgen", "Calle Rosa de Guadalupe #2, Colonia Bajo del Cerro"))
            contienda.insert(Tienda(3, "La Piscina", "Avenida de los Charcos #78, Colonia El Mojado"))
            contienda.insert(Tienda(4, "El Churro", "Calle el Pason #666, Colonia El Viaje"))
            contienda.insert(Tienda(5, "Don Pacho", "Avenida del Reboso #1521, Colonia El Burro"))
        } else {
            l.warning("Ya esta creada la tabla Tienda.Tienda")
        }
    }

    private fun tablaInv(coninv: InventarioDao) {
        if (coninv.prepareTable()) {
            coninv.insert(Inventario(1, "CD-DVD", "900 MB DE ESPACIO", 35.50, 5))
            coninv.insert(Inventario(2, "USB-HP", "32GB, USB 3.0", 155.90, 4))
            coninv.insert(Inventario(3, "Laptop SONY", "4GB RAM, 300 HDD, i5 2.6 GHz.", 13410.07, 3))
            coninv.insert(Inventario(4, "Mouse Optico", "700 DPI", 104.40, 2))
            coninv.insert(Inventario(5, "Disco Duro", "200 TB, HDD, USB 3.0", 2300.00, 1))
            coninv.insert(Inventario(6, "Proyector TSHB", "TOSHIBA G155", 5500.00, 5))
        } else {
            l.warning("Ya esta creada la tabla Inventario.Inventario")
        }
    }

     fun visTodasLasTiendas(visTienda:List<Tienda>){

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

     fun visTodosLosInvPorTiendas(visInv:List<Inventario>) {
        println("---------------------------------------------------------")
        println("MOSTRAR TODAS LOS INVENTARIOS ORDENADOS POR ID_TIENDA:")
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