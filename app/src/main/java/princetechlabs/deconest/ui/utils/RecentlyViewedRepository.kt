package princetechlabs.deconest.ui.utils

import princetechlabs.deconest.ui.data.ProductData

object RecentlyViewedRepository {
    private const val MAX_SIZE = 10
    val items = mutableListOf<ProductData>()
    private val listeners = mutableListOf<() -> Unit>()

    fun add(product: ProductData) {
        items.removeAll { it.name == product.name }
        items.add(0, product)
        if (items.size > MAX_SIZE) items.removeAt(items.lastIndex)
        notifyListeners()
    }

    fun addListener(l: () -> Unit) { listeners.add(l) }
    fun removeListener(l: () -> Unit) { listeners.remove(l) }
    private fun notifyListeners() { listeners.forEach { it() } }
}
