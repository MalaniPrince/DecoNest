package princetechlabs.deconest.ui.data

data class CartItem(
    val name: String,
    val imageUrl: String,
    val category: String,
    val price: Int,
    var quantity: Int = 1
)
