class GestorDeDatos() {

    private val c = ConnectionBuilder()
    private val conInvDAO = InventarioDao(c.connection)
    private val conTienda = TiendaDao(c.connection)
    private val listaDeTiendas = conTienda.selectStore()
    private val listaDeInv = conInvDAO.selectGroup()

    init {
        confDb()
    }
    private fun confDb() {
        println("conectando.....")

        if (c.connection.isValid(10)) {
            println("Conexion Valida")
            c.connection.use {//Prepara todas las bases de datos con sus datos

                tablaInv(conInvDAO)

                tablaTienda(conTienda)

                //cambiarPrecioParaMayoresDe(2000.00)

                VisTodasLasTiendas(listaDeTiendas)
                VisTodosLosInvPorTiendas(listaDeInv)

            }

        } else {
            println("Error de Conexion")
        }
    }

    private fun cambiarPrecioParaMayoresDe(umbral:Double): Boolean{
        val todoinv = conInvDAO.selectAllInventario()
        todoinv.forEach {
            val precio = it.PRECIO
            if (precio > umbral) {
                val newprice = precio + (precio * 0.15)
                conInvDAO.updateInventario(
                    Inventario(it.ID_ARTICULO, it.NOMBRE, it.COMENTARIO, newprice, it.ID_TIENDA),
                    it.ID_ARTICULO
                )
            }
        }
        return true
    }


    private fun tablaTienda(contienda: TiendaDao) {
        if (contienda.prepareTable()) {
            contienda.insertTiendas(Tienda(1, "La Nena", "Callejon de la Nena #123, Colonia Dulce Amor"))
            contienda.insertTiendas(Tienda(2, "La Virgen", "Calle Rosa de Guadalupe #2, Colonia Bajo del Cerro"))
            contienda.insertTiendas(Tienda(3, "La Piscina", "Avenida de los Charcos #78, Colonia El Mojado"))
            contienda.insertTiendas(Tienda(4, "El Churro", "Calle el Pason #666, Colonia El Viaje"))
            contienda.insertTiendas(Tienda(5, "Don Pacho", "Avenida del Reboso #1521, Colonia El Burro"))
        } else {
            println("Ya esta creada la tabla Tienda")
        }
    }

    private fun tablaInv(coninv: InventarioDao) {
        if (coninv.prepareTable()) {
            coninv.insertInventario(Inventario(1, "CD-DVD", "900 MB DE ESPACIO", 35.50, 5))
            coninv.insertInventario(Inventario(2, "USB-HP", "32GB, USB 3.0", 155.90, 4))
            coninv.insertInventario(Inventario(3, "Laptop SONY", "4GB RAM, 300 HDD, i5 2.6 GHz.", 13410.07, 3))
            coninv.insertInventario(Inventario(4, "Mouse Optico", "700 DPI", 104.40, 2))
            coninv.insertInventario(Inventario(5, "Disco Duro", "200 TB, HDD, USB 3.0", 2300.00, 1))
            coninv.insertInventario(Inventario(6, "Proyector TSHB", "TOSHIBA G155", 5500.00, 5))
        } else {
            println("Ya esta creada la tabla Inventario")
        }
    }

    private fun VisTodasLasTiendas(visTienda:List<Tienda>){
        println("---------------------------------------------------------")
        println("MOSTRAR TODAS LAS TIENDAS:")
        visTienda.forEach {
            println("---------------------------------------------------------")
            println("ID de la tienda: ${it.ID_TIENDA}")
            println("Nombre de la tienda: ${it.NOMBRE_TIENDA}")
            println("Direccion de la tienda: ${it.DIRECCION_TIENDA}")
        }
    }

    private fun VisTodosLosInvPorTiendas(visInv:List<Inventario>) {
        println("---------------------------------------------------------")
        println("MOSTRAR TODAS LOS INVENTARIOS ORDENADOS POR ID_TIENDA:")
        visInv.forEach {
            println("---------------------------------------------------------")
            println("ID del articulo: ${it.ID_ARTICULO}")
            println("Nombre del articulo: ${it.NOMBRE}")
            println("Comentario del articulo: ${it.COMENTARIO}")
            println("Precio del articulo: ${it.PRECIO}")
            println("ID de la tienda que tiene el articulo: ${it.ID_TIENDA}")
        }
    }

}