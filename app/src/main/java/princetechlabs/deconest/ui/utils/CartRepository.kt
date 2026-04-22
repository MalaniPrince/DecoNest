package princetechlabs.deconest.ui.utils

import princetechlabs.deconest.ui.data.CartItem
import princetechlabs.deconest.ui.data.ProductData

object CartRepository {
    val items = mutableListOf<CartItem>()
    private val listeners = mutableListOf<() -> Unit>()

    fun addProduct(product: ProductData) {
        val priceInt = product.price.filter { it.isDigit() }.toIntOrNull() ?: 0
        val existing = items.find { it.name == product.name }
        if (existing != null) {
            existing.quantity++
        } else {
            items.add(CartItem(product.name, product.imageUrl, product.category, priceInt, 1))
        }
        notifyListeners()
    }

    fun removeAt(index: Int) {
        if (index in items.indices) {
            items.removeAt(index)
            notifyListeners()
        }
    }

    fun removeItem(item: CartItem) {
        if (items.remove(item)) {
            notifyListeners()
        }
    }

    fun clearCart() {
        items.clear()
        notifyListeners()
    }

    fun getTotalCount(): Int = items.sumOf { it.quantity }
    fun getTotalPrice(): Int = items.sumOf { it.price * it.quantity }
    fun addListener(l: () -> Unit) { listeners.add(l) }
    fun removeListener(l: () -> Unit) { listeners.remove(l) }
    fun notifyListeners() { listeners.forEach { it() } }
}
