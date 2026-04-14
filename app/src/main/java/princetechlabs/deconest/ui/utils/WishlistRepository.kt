package princetechlabs.deconest.ui.utils

import princetechlabs.deconest.ui.data.ProductData

object WishlistRepository {
    val items = mutableListOf<ProductData>()
    private val listeners = mutableListOf<() -> Unit>()

    fun toggle(product: ProductData) {
        val existing = items.find { it.name == product.name }
        if (existing != null) items.remove(existing)
        else items.add(product)
        notifyListeners()
    }

    fun isWishlisted(product: ProductData): Boolean = items.any { it.name == product.name }

    fun addListener(l: () -> Unit) { listeners.add(l) }
    fun removeListener(l: () -> Unit) { listeners.remove(l) }
    private fun notifyListeners() { listeners.forEach { it() } }
}
