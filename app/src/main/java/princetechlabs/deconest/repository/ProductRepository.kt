package princetechlabs.deconest.repository

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import princetechlabs.deconest.ui.data.Product

class ProductRepository {

    private val firestore = FirebaseFirestore.getInstance()

    fun getProductsByCategory(category: String): Flow<Result<List<Product>>> = callbackFlow {
        val query = firestore.collection("products")
            .whereEqualTo("category", category)

        val listener = query.addSnapshotListener { snapshot, error ->
            if (error != null) {
                trySend(Result.failure(error))
                return@addSnapshotListener
            }

            if (snapshot != null) {
                val firestoreProducts = snapshot.toObjects(Product::class.java)
                if (firestoreProducts.isEmpty()) {
                    // Fallback to local data if Firestore is empty
                    trySend(Result.success(getLocalFallbackProducts(category)))
                } else {
                    trySend(Result.success(firestoreProducts))
                }
            }
        }

        awaitClose { listener.remove() }
    }

    private fun getLocalFallbackProducts(category: String): List<Product> {
        val allLocal = listOf(
            Product("f1", "Modern L-Shape Sofa", 32999.0, "https://images.unsplash.com/photo-1555041469-a586c61ea9bc?w=800", "Furniture", "Premium comfort sofa.", 4.5f),
            Product("f2", "Solid Oak Dining Table", 18499.0, "https://images.unsplash.com/photo-1577140917170-285929fb55b7?w=800", "Furniture", "Solid oak wood.", 4.2f),
            Product("l1", "Luxury Velvet Armchair", 12500.0, "https://images.unsplash.com/photo-1567538096621-38d2284b23ff?w=800", "Living Room", "Soft velvet finish.", 4.8f),
            Product("l2", "Marble Coffee Table", 9499.0, "https://images.unsplash.com/photo-1556909114-f6e7ad7d3136?w=800", "Living Room", "Elegant marble top.", 4.3f),
            Product("d1", "Boho Wall Macrame", 1299.0, "https://images.unsplash.com/photo-1513519245088-0e12902e5a38?w=800", "Home Decor", "Handmade wall hanging.", 4.9f),
            Product("k1", "Modern Modular Kitchen", 145000.0, "https://images.unsplash.com/photo-1556911220-e15b29be8c8f?w=800", "Kitchen & Dining", "Full modular setup.", 4.7f),
            Product("la1", "Arc Floor Lamp", 4299.0, "https://images.unsplash.com/photo-1507473885765-e6ed057f782c?w=800", "Lamps & Lighting", "Adjustable height lamp.", 4.5f),
            Product("m1", "Memory Foam Mattress", 22999.0, "https://images.unsplash.com/photo-1631049307264-da0ec9d70304?w=800", "Mattresses", "Pressure relief foam.", 4.7f),
            Product("s1", "Executive Recliner Sofa", 19999.0, "https://images.unsplash.com/photo-1567538096621-38d2284b23ff?w=800", "Sofas & Seating", "Comfortable recliner.", 4.8f)
        )
        
        return allLocal.filter { it.category.contains(category, ignoreCase = true) || category == "All" }
            .ifEmpty { allLocal.take(4) } // Show some products if no match
    }
}
