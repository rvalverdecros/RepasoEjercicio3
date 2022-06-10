package inventario

data class Inventario(
    val idArticulo: Int,
    val nombre: String,
    val comentario: String,
    val precio: Double,
    val idTienda: Int
)

