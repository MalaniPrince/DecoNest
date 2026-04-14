package princetechlabs.deconest.ui.data

import com.google.gson.annotations.SerializedName

data class DummyProductResponse(
    @SerializedName("products") val products: List<DummyProduct>,
    @SerializedName("total") val total: Int,
    @SerializedName("skip") val skip: Int,
    @SerializedName("limit") val limit: Int
)

data class DummyProduct(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("price") val price: Double,
    @SerializedName("discountPercentage") val discountPercentage: Double,
    @SerializedName("rating") val rating: Double,
    @SerializedName("stock") val stock: Int,
    @SerializedName("category") val category: String,
    @SerializedName("thumbnail") val thumbnail: String,
    @SerializedName("images") val images: List<String>
) {
    // Convert to app's ProductData format
    fun toProductData(): ProductData {
        val displayCategory = when (category) {
            "furniture"            -> "Furniture"
            "home-decoration"      -> "Living Room"
            "kitchen-accessories"  -> "Kitchen"
            "lighting"             -> "Lighting"
            "womens-bags"          -> "Luxury"
            "mens-shirts"          -> "Luxury"
            else                   -> "Decor"
        }
        // Convert USD to INR (approx 83x), ensure minimum price
        val priceInr = maxOf((price * 83).toInt(), 999)
        val formatted = formatPrice(priceInr)
        val safeRating = rating.toFloat().coerceIn(1f, 5f)
        val safeReviews = (stock * 3).coerceAtLeast(42)
        return ProductData(
            name         = title,
            imageUrl     = thumbnail,
            category     = displayCategory,
            price        = "₹$formatted",
            rating       = safeRating,
            reviewCount  = safeReviews,
            isNew        = discountPercentage > 10.0,
            isTrending   = rating > 4.5
        )
    }

    private fun formatPrice(amount: Int): String = String.format("%,d", amount)
}
