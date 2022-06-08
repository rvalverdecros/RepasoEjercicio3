import baseDeDatos.ConnectionBuilder
import baseDeDatos.GestorDeDatos
import baseDeDatos.l
import inventario.InventarioDao
import tienda.TiendaDao
fun main() {
val gestor = GestorDeDatos()
//gestor.cambiarPrecioParaMayoresDe(2000.00,15.00)
gestor.visTodasLasTiendas(gestor.conTienDAO.selectAll())
gestor.visTodosLosInvPorTiendas(gestor.conInvDAO.selectGroup())
gestor.close()
}