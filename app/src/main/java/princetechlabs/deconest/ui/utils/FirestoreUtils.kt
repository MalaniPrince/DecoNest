package princetechlabs.deconest.ui.utils

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import princetechlabs.deconest.ui.data.Product

object FirestoreUtils {

    fun seedDatabase() {
        val firestore = FirebaseFirestore.getInstance()
        val productsCollection = firestore.collection("products")
        val batch = firestore.batch()

        val categories = listOf(
            "Furniture", "Living Room", "Home Decor", "Furnishings",
            "Kitchen & Dining", "Lamps & Lighting", "Mattresses", "Modular", "Sofas & Seating"
        )

        val unsplashIds = listOf(
            "1555041469-a586c61ea9bc", "1524758631624-e2822e304c36", "1505693416388-ac5ce068fe85",
            "1586023492125-27b2c045efd7", "1556911220-e15b29be8c8f", "1581783898377-1c85bf937427",
            "1531651008558-ed1758732ba9", "1507473885765-e6ed057f782c", "1513519245088-0e12902e5a38",
            "1540574163026-643ea20ade25", "1556909114-f6e7ad7d3136", "1595428774223-ef52624120d2",
            "1518455027359-f3f8164ba6bd", "1543922596-cfe5ae01a3d3", "1618220179428-22790b461013",
            "1550989460-0adf9ea622e2", "1503602642458-232111445657", "1631049307264-da0ec9d70304",
            "1493663284031-b7e3aefcae8e", "1581404476143-fb31d742929f"
        )

        for (category in categories) {
            for (i in 1..20) {
                val id = "${category.take(3).lowercase().replace(" ", "")}_${System.currentTimeMillis()}_$i"
                
                val name = when (category) {
                    "Furniture" -> listOf("Oak", "Walnut", "Marble", "Steel", "Glass")[i % 5] + " " + listOf("Table", "Chair", "Cabinet", "Shelf", "Desk")[i % 5] + " " + (i + 100)
                    "Living Room" -> listOf("Velvet", "Leather", "Cotton", "Suede", "Linen")[i % 5] + " " + listOf("Sofa", "Armchair", "Rug", "TV Unit", "Coffee Table")[i % 5]
                    "Kitchen & Dining" -> listOf("Modular", "Compact", "Luxury", "Modern", "Classic")[i % 5] + " " + listOf("Kitchen", "Dining Set", "Cookware", "Cutlery", "Pantry")[i % 5]
                    "Home Decor" -> listOf("Vintage", "Modern", "Boho", "Royal", "Minimalist")[i % 5] + " " + listOf("Mirror", "Vase", "Painting", "Wall Clock", "Candle Holder")[i % 5]
                    "Lamps & Lighting" -> listOf("Industrial", "Crystal", "LED", "Smart", "Retro")[i % 5] + " " + listOf("Chandelier", "Floor Lamp", "Desk Lamp", "Pendant", "Sconce")[i % 5]
                    "Mattresses" -> listOf("Memory Foam", "Ortho", "Hybrid", "Latex", "Gel")[i % 5] + " Mattress " + listOf("King", "Queen", "Single", "Double", "Twin")[i % 5]
                    "Sofas & Seating" -> listOf("Recliner", "Sectional", "Ottoman", "Bean Bag", "Loveseat")[i % 5] + " " + (i + 50)
                    else -> "Premium $category $i"
                }

                val price = (2000 + (i * 1500) + (category.length * 400)).toDouble()
                val imageUrl = "https://images.unsplash.com/photo-${unsplashIds[(i - 1) % unsplashIds.size]}?w=800&q=80"
                
                val product = Product(
                    id = id,
                    name = name,
                    price = price,
                    imageUrl = imageUrl,
                    category = category,
                    description = "A beautiful and durable $name designed for your modern $category.",
                    rating = (4.0 + (i % 10) / 10.0).toFloat()
                )
                
                val docRef = productsCollection.document(id)
                batch.set(docRef, product)
            }
        }

        batch.commit()
            .addOnSuccessListener { Log.d("FirestoreUtils", "Seeded 180 realistic products!") }
            .addOnFailureListener { e -> Log.e("FirestoreUtils", "Seed failed", e) }
    }
}
