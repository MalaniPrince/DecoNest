package princetechlabs.deconest.ui.utils

import android.content.Context
import princetechlabs.deconest.R
import princetechlabs.deconest.ui.data.BannerData
import princetechlabs.deconest.ui.data.HomeCategoryItem
import princetechlabs.deconest.ui.data.ModelClass
import princetechlabs.deconest.ui.data.ProductData

object MasterDataUtils {

    fun MasterDataList(context: Context): ArrayList<ModelClass> {
        val dataList = ArrayList<ModelClass>()
        dataList.add(ModelClass("https://images.unsplash.com/photo-1555041469-a586c61ea9bc?w=400", name = "L-Shape Sofa"))
        dataList.add(ModelClass("https://images.unsplash.com/photo-1540574163026-643ea20ade25?w=400", name = "Velvet Sofa"))
        dataList.add(ModelClass("https://images.unsplash.com/photo-1506439773649-6e0eb8cfb237?w=400", name = "Accent Chair"))
        return dataList
    }

    fun viewPagerImage(context: Context): ArrayList<String> {
        return ArrayList(getBanners().map { it.imageUrl })
    }

    fun getBanners(): List<BannerData> = listOf(
        BannerData(
            imageUrl   = "https://images.unsplash.com/photo-1555041469-a586c61ea9bc?w=800&q=80",
            title      = "Premium Sofas",
            subtitle   = "Starting from ₹8,999  •  Free Delivery",
            badge      = "UP TO 30% OFF"
        ),
        BannerData(
            imageUrl   = "https://images.unsplash.com/photo-1586023492125-27b2c045efd7?w=800&q=80",
            title      = "Bedroom Collection",
            subtitle   = "Beds, wardrobes & more",
            badge      = "NEW ARRIVAL"
        ),
        BannerData(
            imageUrl   = "https://images.unsplash.com/photo-1540574163026-643ea20ade25?w=800&q=80",
            title      = "Luxury Living",
            subtitle   = "Premium pieces for premium homes",
            badge      = "EXCLUSIVE"
        ),
        BannerData(
            imageUrl   = "https://images.unsplash.com/photo-1616486338812-3dadae4b4ace?w=800&q=80",
            title      = "Bedroom Makeover",
            subtitle   = "Extra 15% off on all orders today",
            badge      = "15% OFF"
        )
    )

    fun categoryTabs(): List<String> {
        return listOf("All", "Furniture", "Living Room", "Kitchen", "Luxury", "Bed Room", "Mattresses")
    }

    fun homeCategoryItems(): List<HomeCategoryItem> {
        return listOf(
            HomeCategoryItem("Furniture", R.drawable.furniture),
            HomeCategoryItem("Living Room", R.drawable.living),
            HomeCategoryItem("Home Decor", R.drawable.homedecor),
            HomeCategoryItem("Furnishings", R.drawable.furnishings),
            HomeCategoryItem("Kitchen & Dining", R.drawable.kitchn),
            HomeCategoryItem("Lamps & Lighting", R.drawable.lamli),
            HomeCategoryItem("Mattresses", R.drawable.mattresses),
            HomeCategoryItem("Modular", R.drawable.module),
            HomeCategoryItem("Sofas & Seating", R.drawable.sofaseting)
        )
    }

    fun getAllProducts(): List<ProductData> = listOf(

        // Furniture
        ProductData(
            "Modern L-Shape Sofa",
            "https://images.unsplash.com/photo-1555041469-a586c61ea9bc?w=500&q=80",
            "Furniture", "₹32,999"
        ),
        ProductData(
            "Wooden Dining Table",
            "https://images.unsplash.com/photo-1577140917170-285929fb55b7?w=500&q=80",
            "Furniture", "₹18,499"
        ),
        ProductData(
            "King Size Bed Frame",
            "https://images.unsplash.com/photo-1505693416388-ac5ce068fe85?w=500&q=80",
            "Furniture", "₹24,999"
        ),
        ProductData(
            "Ergonomic Study Chair",
            "https://images.unsplash.com/photo-1592078615290-033ee584e267?w=500&q=80",
            "Furniture", "₹8,499"
        ),
        ProductData(
            "Bookshelf Cabinet",
            "https://images.unsplash.com/photo-1594620302200-9a762244a156?w=500&q=80",
            "Furniture", "₹12,999"
        ),

        // Luxury
        ProductData(
            "Premium Velvet Sofa",
            "https://images.unsplash.com/photo-1540574163026-643ea20ade25?w=500&q=80",
            "Luxury", "₹89,999"
        ),
        ProductData(
            "Marble Center Table",
            "https://images.unsplash.com/photo-1618220179428-22790b461013?w=500&q=80",
            "Luxury", "₹45,000"
        ),
        ProductData(
            "Gold Accent Chair",
            "https://images.unsplash.com/photo-1506439773649-6e0eb8cfb237?w=500&q=80",
            "Luxury", "₹62,500"
        ),
        ProductData(
            "Crystal Chandelier",
            "https://images.unsplash.com/photo-1543922596-cfe5ae01a3d3?w=500&q=80",
            "Luxury", "₹38,000"
        ),

        // Living Room
        ProductData(
            "3-Seater Fabric Sofa",
            "https://images.unsplash.com/photo-1493663284031-b7e3aefcae8e?w=500&q=80",
            "Living Room", "₹27,999"
        ),
        ProductData(
            "Coffee Table Set",
            "https://images.unsplash.com/photo-1556909114-f6e7ad7d3136?w=500&q=80",
            "Living Room", "₹9,499"
        ),
        ProductData(
            "TV Unit Cabinet",
            "https://images.unsplash.com/photo-1585771724684-38269d6639fd?w=500&q=80",
            "Living Room", "₹15,999"
        ),
        ProductData(
            "Arc Floor Lamp",
            "https://images.unsplash.com/photo-1507473885765-e6ed057f782c?w=500&q=80",
            "Living Room", "₹4,299"
        ),
        ProductData(
            "Recliner Sofa Chair",
            "https://images.unsplash.com/photo-1567538096621-38d2284b23ff?w=500&q=80",
            "Living Room", "₹19,999"
        ),

        // Bed Room
        ProductData(
            "Queen Bed with Storage",
            "https://images.unsplash.com/photo-1540518614846-7eded433c457?w=500&q=80",
            "Bed Room", "₹28,499"
        ),
        ProductData(
            "Wooden Wardrobe",
            "https://images.unsplash.com/photo-1595428774223-ef52624120d2?w=500&q=80",
            "Bed Room", "₹35,000"
        ),
        ProductData(
            "Bedside Table",
            "https://images.unsplash.com/photo-1586023492125-27b2c045efd7?w=500&q=80",
            "Bed Room", "₹6,999"
        ),
        ProductData(
            "Dressing Table Mirror",
            "https://images.unsplash.com/photo-1616486338812-3dadae4b4ace?w=500&q=80",
            "Bed Room", "₹11,999"
        ),

        // Mattresses
        ProductData(
            "Memory Foam Mattress",
            "https://images.unsplash.com/photo-1631049307264-da0ec9d70304?w=500&q=80",
            "Mattresses", "₹22,999", rating = 4.6f, reviewCount = 342
        ),
        ProductData(
            "Ortho Spring Mattress",
            "https://images.unsplash.com/photo-1555041469-a586c61ea9bc?w=500&q=80",
            "Mattresses", "₹18,499", rating = 4.3f, reviewCount = 215
        ),
        ProductData(
            "Latex Cool Gel Mattress",
            "https://images.unsplash.com/photo-1551516594-56cb78394645?w=500&q=80",
            "Mattresses", "₹29,999", rating = 4.7f, reviewCount = 189, isTrending = true
        ),

        // Kitchen
        ProductData(
            "Ceramic Dinner Set (12pcs)",
            "https://images.unsplash.com/photo-1514237487632-b60bc844a47d?w=500&q=80",
            "Kitchen", "₹4,299", rating = 4.5f, reviewCount = 267, isNew = true
        ),
        ProductData(
            "Wooden Cutting Board",
            "https://images.unsplash.com/photo-1556909114-f6e7ad7d3136?w=500&q=80",
            "Kitchen", "₹1,899", rating = 4.4f, reviewCount = 156
        ),
        ProductData(
            "Cast Iron Cookware Set",
            "https://images.unsplash.com/photo-1585771724684-38269d6639fd?w=500&q=80",
            "Kitchen", "₹8,999", rating = 4.8f, reviewCount = 421, isTrending = true
        )
    )

    fun getProductsByCategory(category: String): List<ProductData> {
        return if (category == "All") getAllProducts()
        else getAllProducts().filter { it.category == category }
    }

    fun getTrendingProducts(): List<ProductData> {
        return getAllProducts().take(6)
    }

    fun getNewArrivals(): List<ProductData> {
        return getAllProducts().takeLast(6)
    }
}
