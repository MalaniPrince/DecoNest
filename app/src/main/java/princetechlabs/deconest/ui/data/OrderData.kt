package princetechlabs.deconest.ui.data

import princetechlabs.deconest.ui.data.CartItem

data class OrderData(
    val orderId: String,
    val address: String,
    val totalAmount: Int,
    val items: List<CartItem>,
    val status: String = "Order Placed"
)
