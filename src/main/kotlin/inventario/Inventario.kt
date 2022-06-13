package inventario

data class Inventario(
    val idArticulo: Int,
    var nombre: String,
    var comentario: String,
    var precio: Double,
    var idTienda: Int
)

