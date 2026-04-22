package princetechlabs.deconest.ui.utils

import princetechlabs.deconest.ui.data.OrderData

object OrderRepository {
    val orders = mutableListOf<OrderData>()

    fun placeOrder(address: String, discountedTotal: Int = CartRepository.getTotalPrice()): String {
        val orderId = "DN" + (100000..999999).random()
        orders.add(0, OrderData(orderId, address, discountedTotal, CartRepository.items.toList()))
        CartRepository.clearCart()
        return orderId
    }
}
