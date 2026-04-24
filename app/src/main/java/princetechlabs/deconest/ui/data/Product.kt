package princetechlabs.deconest.ui.data

data class Product(
    val id: String = "",
    val name: String = "",
    val price: Double = 0.0,
    val imageUrl: String = "",
    val category: String = "",
    val description: String = "",
    val rating: Float = 0f
)
