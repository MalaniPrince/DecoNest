package princetechlabs.deconest.ui.data

data class ProductData(
    val name: String,
    val imageUrl: String,
    val category: String,
    val price: String,
    val rating: Float = 4.2f,
    val reviewCount: Int = 128,
    val isNew: Boolean = false,
    val isTrending: Boolean = false
)
