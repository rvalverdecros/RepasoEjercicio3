package tienda

import java.sql.Connection
import java.sql.SQLException

class TiendaDao(private val c: Connection) {
    companion object {
        private const val SCHEMA = "default"
        private const val TABLE = "TIENDAS"
        private const val TRUNCATE_TABLE_TIENDAS_SQL = "TRUNCATE TABLE TIENDAS"
        private const val DROP_TABLE_TIENDAS_SQL = "DROP TABLE TIENDAS"
        private const val CREATE_TABLE_TIENDAS_SQL =
            "CREATE TABLE TIENDAS (ID_TIENDA NUMBER(10,0), NOMBRE_TIENDA VARCHAR2(40), DIRECCION_TIENDA VARCHAR2(200) );"
        private const val INSERT_TIENDAS_SQL = "INSERT INTO TIENDAS" + "  (ID_TIENDA, NOMBRE_TIENDA, DIRECCION_TIENDA) VALUES " + " (?, ?, ?);"
        private const val SELECT_ALL_TIENDAS = "select * from TIENDAS"
        private const val DELETE_TIENDAS_SQL = "delete from TIENDAS where ID_TIENDA = ?;"
        private const val UPDATE_TIENDAS_SQL = "update TIENDAS set NOMBRE_TIENDA =?, DIRECCION_TIENDA=? where ID_TIENDA = ?;"
    }
    fun prepareTable():Boolean {
        var tablacreada = false
        val metaData = c.metaData
        val rs = metaData.getTables(null, null, TABLE, null)

        if (!rs.next())
            tablacreada= createTable()
        return tablacreada
    }

    private fun truncateTable() {
        println(TRUNCATE_TABLE_TIENDAS_SQL)
        try {
            c.createStatement().use { st ->
                st.execute(TRUNCATE_TABLE_TIENDAS_SQL)
            }
            c.commit()
        } catch (e: SQLException) {
            printSQLException(e)
        }
    }

    private fun createTable():Boolean {
        var tablacreada = false
        println(CREATE_TABLE_TIENDAS_SQL)
        try {

            c.createStatement().use { st ->
                st.execute(CREATE_TABLE_TIENDAS_SQL)
                tablacreada = true
            }
            c.commit()
        } catch (e: SQLException) {
            printSQLException(e)
        }
        return tablacreada
    }

     fun dropTable() {
        println(DROP_TABLE_TIENDAS_SQL)
        try {

            c.createStatement().use { st ->
                st.execute(DROP_TABLE_TIENDAS_SQL)
            }
            c.commit()
        } catch (e: SQLException) {
            printSQLException(e)
        }
    }

    fun insert(tienda: Tienda) {
        println(INSERT_TIENDAS_SQL)
        try {
            c.prepareStatement(INSERT_TIENDAS_SQL).use { st ->
                st.setInt(1, tienda.id)
                st.setString(2, tienda.nombre)
                st.setString(3, tienda.direccion)
                println(st)
                st.executeUpdate()
            }
            //Commit the change to the database
            c.commit()
        } catch (e: SQLException) {
            printSQLException(e)
        }
    }

    fun selectAll(): List<Tienda> {

        val tiendas: MutableList<Tienda> = ArrayList()
        try {
            c.prepareStatement(SELECT_ALL_TIENDAS).use { st ->
                println(st)
                val rs = st.executeQuery()
                while (rs.next()) {
                    val tiendaid = rs.getInt("ID_TIENDA")
                    val nombretienda = rs.getString("NOMBRE_TIENDA")
                    val direccion = rs.getString("DIRECCION_TIENDA")
                    tiendas.add(Tienda(tiendaid, nombretienda,direccion))
                }
            }

        } catch (e: SQLException) {
            printSQLException(e)
        }
        return tiendas
    }

    fun deleteById(id: Int): Boolean {
        var rowDeleted = false

        try {
            c.prepareStatement(DELETE_TIENDAS_SQL).use { st ->
                st.setInt(1, id)
                rowDeleted = st.executeUpdate() > 0
            }
            c.commit()
        } catch (e: SQLException) {
            printSQLException(e)
        }
        return rowDeleted
    }

    fun update(tienda: Tienda): Boolean {
        var rowUpdated = false

        try {
            c.prepareStatement(UPDATE_TIENDAS_SQL).use { st ->
                st.setInt(3,tienda.id)
                st.setString(1,tienda.nombre)
                st.setString(2,tienda.direccion)
                rowUpdated = st.executeUpdate() > 0
            }
            c.commit()
        } catch (e: SQLException) {
            printSQLException(e)
        }
        return rowUpdated
    }

    private fun printSQLException(ex: SQLException) {
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