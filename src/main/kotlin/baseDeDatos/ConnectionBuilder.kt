package baseDeDatos

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException


class ConnectionBuilder { //Aqui se encuentra los datos de conexion de la base de datos
    lateinit var connection: Connection
    private val jdbcURL = "jdbc:h2:~/u7ej3"
    private val jdbcUsername = "user"
    private val jdbcPassword = "user"


    init {
        try {
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword)
            connection.autoCommit=false
        } catch (e: SQLException) {
            e.printStackTrace()
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }
    }

}