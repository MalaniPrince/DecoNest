package princetechlabs.deconest.ui.utils

import princetechlabs.deconest.ui.data.ProductData

object ProductRepository {

    // Lazy — Retrofit is created on background thread, NOT at app startup on main thread
    private val apiService by lazy { ProductApiService.create() }

    private var cachedProducts: List<ProductData> = emptyList()
    private var isLoaded = false

    private val listeners = mutableListOf<(List<ProductData>) -> Unit>()

    fun addListener(l: (List<ProductData>) -> Unit) { listeners.add(l) }
    fun removeListener(l: (List<ProductData>) -> Unit) { listeners.remove(l) }
    private fun notify(products: List<ProductData>) { listeners.toList().forEach { it(products) } }

    fun getCached(): List<ProductData> = cachedProducts
    fun isLoaded(): Boolean = isLoaded

    suspend fun loadProducts(forceRefresh: Boolean = false): Result<List<ProductData>> {
        if (isLoaded && !forceRefresh) return Result.success(cachedProducts)

        return try {
            val furnitureResp = apiService.getFurnitureProducts(limit = 30)
            val decoResp      = apiService.getHomeDecorationProducts(limit = 30)
            val kitchenResp   = apiService.getKitchenProducts(limit = 20)
            val luxuryResp    = apiService.getLuxuryProducts(limit = 15)

            val furniture  = furnitureResp.body()?.products?.map { it.toProductData() } ?: emptyList()
            val decoration = decoResp.body()?.products?.map { it.toProductData() } ?: emptyList()
            val kitchen    = kitchenResp.body()?.products?.map { it.toProductData() } ?: emptyList()
            val luxury     = luxuryResp.body()?.products?.map { it.toProductData() } ?: emptyList()

            val combined = furniture + decoration + kitchen + luxury

            if (combined.isNotEmpty()) {
                cachedProducts = combined
                isLoaded = true
                notify(combined)
                Result.success(combined)
            } else {
                val local = MasterDataUtils.getAllProducts()
                cachedProducts = local
                isLoaded = true
                notify(local)
                Result.success(local)
            }
        } catch (e: Exception) {
            // Network error — use local products, don't override if we already have data
            if (cachedProducts.isEmpty()) {
                val local = MasterDataUtils.getAllProducts()
                cachedProducts = local
                isLoaded = true
                notify(local)
            }
            Result.failure(e)
        }
    }

    fun getAllProducts(): List<ProductData> =
        cachedProducts.ifEmpty { MasterDataUtils.getAllProducts() }

    fun getTrending(): List<ProductData> =
        getAllProducts().filter { it.isTrending }.take(8).ifEmpty { getAllProducts().take(8) }

    fun getNewArrivals(): List<ProductData> =
        getAllProducts().filter { it.isNew }.take(8).ifEmpty { getAllProducts().reversed().take(8) }

    fun getByCategory(category: String): List<ProductData> {
        if (category == "All") return getAllProducts()
        return getAllProducts().filter { it.category.equals(category, ignoreCase = true) }
    }

    fun search(query: String): List<ProductData> {
        if (query.isBlank()) return getAllProducts()
        val q = query.trim().lowercase()
        return getAllProducts().filter {
            it.name.lowercase().contains(q) || it.category.lowercase().contains(q)
        }
    }
}
