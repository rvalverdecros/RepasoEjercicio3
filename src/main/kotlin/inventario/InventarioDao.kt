package inventario

import java.sql.Connection
import java.sql.SQLException
import java.util.logging.Level
import java.util.logging.LogManager

internal val l = LogManager.getLogManager().getLogger("").apply { level = Level.ALL }

class InventarioDao(private val c: Connection) {
    companion object {
        private const val SCHEMA = "default"
        private const val TABLE = "INVENTARIOS"
        private const val TRUNCATE_TABLE_INVENTARIOS_SQL = "TRUNCATE TABLE INVENTARIOS"
        private const val DROP_TABLE_INVENTARIOS_SQL = "DROP TABLE INVENTARIOS"
        private const val CREATE_TABLE_INVENTARIOS_SQL =
            "CREATE TABLE INVENTARIOS (ID_ARTICULO NUMBER(10,0), NOMBRE VARCHAR2(50), COMENTARIO VARCHAR2(200) NOT NULL, PRECIO NUMBER(10,2), ID_TIENDA NUMBER(10,0) );"
        private const val INSERT_INVENTARIOS_SQL =
            "INSERT INTO INVENTARIOS" + "  (ID_ARTICULO, NOMBRE, COMENTARIO,PRECIO,ID_TIENDA) VALUES " + " (?, ?, ?, ?, ?);"
        private const val SELECT_ALL_INVENTARIOS = "select * from INVENTARIOS"
        private const val SELECT_INVENTARIOS_GROUP =
            "select ID_ARTICULO, NOMBRE, COMENTARIO, PRECIO, ID_TIENDA from INVENTARIOS ORDER BY ID_TIENDA"
        private const val DELETE_INVENTARIOS_SQL = "delete from INVENTARIOS where ID_TIENDA = ?;"
        private const val UPDATE_INVENTARIOS_PRECIO_SQL =
            "update INVENTARIOS set PRECIO =  PRECIO + (PRECIO * ?) WHERE PRECIO > 2000.00;"
        private const val UPDATE_INVENTARIOS_SQL =
            "update INVENTARIOS set NOMBRE =?, COMENTARIO=?,PRECIO=?, ID_TIENDA=?  where ID_ARTICULO = ?;"
    }

    fun prepareTable(): Boolean {
        var tablacreada = false
        val metaData = c.metaData
        val rs = metaData.getTables(null, null, TABLE, null)

        if (!rs.next())
            tablacreada = createTable()
        return tablacreada
    }

    private fun truncateTable() { //Sirve para eliminar los datos de la tabla
        l.info(TRUNCATE_TABLE_INVENTARIOS_SQL)
        try {
            c.createStatement().use { st ->
                st.execute(TRUNCATE_TABLE_INVENTARIOS_SQL)
            }
            c.commit()
        } catch (e: SQLException) {
            printSQLException(e)
        }
    }

    private fun createTable(): Boolean {
        var tablacreada = false
        l.info(CREATE_TABLE_INVENTARIOS_SQL)
        try {

            c.createStatement().use { st ->
                st.execute(CREATE_TABLE_INVENTARIOS_SQL)
                tablacreada = true
            }
            c.commit()
        } catch (e: SQLException) {
            printSQLException(e)
        }
        return tablacreada
    }

    fun dropTable() {
        l.info(DROP_TABLE_INVENTARIOS_SQL)
        try {

            c.createStatement().use { st ->
                st.execute(DROP_TABLE_INVENTARIOS_SQL)
            }
            c.commit()
        } catch (e: SQLException) {
            printSQLException(e)
        }
    }

    fun selectGroup(): List<Inventario> {

        val inventarios: MutableList<Inventario> = ArrayList()
        try {
            c.prepareStatement(SELECT_INVENTARIOS_GROUP).use { st ->
                l.info(st.toString())
                val rs = st.executeQuery()
                while (rs.next()) {
                    val articuloid = rs.getInt("ID_ARTICULO")
                    val nombre = rs.getString("NOMBRE")
                    val comentario = rs.getString("COMENTARIO")
                    val precio = rs.getDouble("PRECIO")
                    val tiendaid = rs.getInt("ID_TIENDA")
                    inventarios.add(Inventario(articuloid, nombre, comentario, precio, tiendaid))
                }
            }

        } catch (e: SQLException) {
            printSQLException(e)
        }
        return inventarios
    }

    fun insert(inventario: Inventario) {
        l.info(INSERT_INVENTARIOS_SQL)
        try {
            c.prepareStatement(INSERT_INVENTARIOS_SQL).use { st ->
                st.setInt(1, inventario.idArticulo)
                st.setString(2, inventario.nombre)
                st.setString(3, inventario.comentario)
                st.setDouble(4, inventario.precio)
                st.setInt(5, inventario.idTienda)
                println(st)
                st.executeUpdate()
            }
            c.commit()
        } catch (e: SQLException) {
            printSQLException(e)
        }
    }

    fun selectAll(): List<Inventario> {

        val inventarios: MutableList<Inventario> = ArrayList()
        try {
            c.prepareStatement(SELECT_ALL_INVENTARIOS).use { st ->
                l.info(st.toString())
                val rs = st.executeQuery()
                while (rs.next()) {
                    val articuloid = rs.getInt("ID_ARTICULO")
                    val nombre = rs.getString("NOMBRE")
                    val comentario = rs.getString("COMENTARIO")
                    val precio = rs.getDouble("PRECIO")
                    val tiendaid = rs.getInt("ID_TIENDA")
                    inventarios.add(Inventario(articuloid, nombre, comentario, precio, tiendaid))
                }
            }

        } catch (e: SQLException) {
            printSQLException(e)
        }
        return inventarios
    }

    fun updateInventarioPrecio(aumento: Double): Boolean {
        var rowUpdated = false

        try {
            c.prepareStatement(UPDATE_INVENTARIOS_PRECIO_SQL).use { st ->
                st.setDouble(1, aumento)
                rowUpdated = st.executeUpdate() > 0
            }
            c.commit()
        } catch (e: SQLException) {
            printSQLException(e)
        }
        return rowUpdated
    }

    fun update(inventario: Inventario): Boolean {
        var rowUpdated = false

        try {
            c.prepareStatement(UPDATE_INVENTARIOS_SQL).use { st ->
                st.setInt(5, inventario.idArticulo)
                st.setString(1, inventario.nombre)
                st.setString(2, inventario.comentario)
                st.setDouble(3, inventario.precio)
                st.setInt(4, inventario.idTienda)
                rowUpdated = st.executeUpdate() > 0
            }
            c.commit()
        } catch (e: SQLException) {
            printSQLException(e)
        }
        return rowUpdated
    }

    private fun printSQLException(ex: SQLException) { //Esta funcion privada es para mostrar si ha habido un error
        for (e in ex) {
            if (e is SQLException) {
                e.printStackTrace(System.err)
                System.err.println("SQLState: " + e.sqlState)
                System.err.println("Error Code: " + e.errorCode)
                System.err.println("Message: " + e.message)
                var t = ex.cause
                while (t != null) {
                    println("Cause: $t")
                    t = t.cause
                }
            }
        }
    }
}