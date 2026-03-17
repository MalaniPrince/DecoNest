package princetechlabs.deconest.ui.utils

import android.content.Context
import princetechlabs.deconest.R
import princetechlabs.deconest.ui.data.HomeCategoryItem
import princetechlabs.deconest.ui.data.ModelClass
import princetechlabs.deconest.ui.data.ProductData

object MasterDataUtils {

    fun MasterDataList(context: Context): ArrayList<ModelClass> {
        val dataList = ArrayList<ModelClass>()
        dataList.add(ModelClass("https://as2.ftcdn.net/v2/jpg/00/29/79/51/1000_F_29795157_gLLeeTiFDsqno207woilLAq0jeOyOqdu.jpg", name = "Chair"))
        dataList.add(ModelClass("https://as2.ftcdn.net/v2/jpg/02/83/53/27/1000_F_283532744_Rbs039ygDyekkcCgXp7n3fwEPfB21tjJ.jpg", name = "Black Chair"))
        dataList.add(ModelClass("https://as2.ftcdn.net/v2/jpg/02/83/53/27/1000_F_283532744_Rbs039ygDyekkcCgXp7n3fwEPfB21tjJ.jpg", name = "Black Chair"))
        return dataList
    }

    fun viewPagerImage(context: Context): ArrayList<String> {
        val itemList = ArrayList<String>()
        itemList.add("https://as2.ftcdn.net/v2/jpg/00/29/79/51/1000_F_29795157_gLLeeTiFDsqno207woilLAq0jeOyOqdu.jpg")
        itemList.add("https://as2.ftcdn.net/v2/jpg/02/83/53/27/1000_F_283532744_Rbs039ygDyekkcCgXp7n3fwEPfB21tjJ.jpg")
        itemList.add("https://as2.ftcdn.net/v2/jpg/02/83/53/27/1000_F_283532785_Nt40tPnij5PzXTUERVRmHJPOFmjZ87ZM.jpg")
        return itemList
    }

    fun categoryTabs(): List<String> {
        return listOf("All", "Furniture", "Luxury", "Living Room", "Bed Room", "Mattresses")
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
        ProductData("Modern L-Shape Sofa", "https://images.unsplash.com/photo-1555041469-a586c61ea9bc?w=400", "Furniture", "₹32,999"),
        ProductData("Wooden Dining Table", "https://images.unsplash.com/photo-1577140917170-285929fb55b7?w=400", "Furniture", "₹18,499"),
        ProductData("King Size Bed Frame", "https://images.unsplash.com/photo-1505693416388-ac5ce068fe85?w=400", "Furniture", "₹24,999"),
        ProductData("Study Chair", "https://as2.ftcdn.net/v2/jpg/00/29/79/51/1000_F_29795157_gLLeeTiFDsqno207woilLAq0jeOyOqdu.jpg", "Furniture", "₹8,499"),
        ProductData("Bookshelf Cabinet", "https://images.unsplash.com/photo-1594620302200-9a762244a156?w=400", "Furniture", "₹12,999"),
        // Luxury
        ProductData("Premium Velvet Sofa", "https://images.unsplash.com/photo-1540574163026-643ea20ade25?w=400", "Luxury", "₹89,999"),
        ProductData("Marble Center Table", "https://images.unsplash.com/photo-1618220179428-22790b461013?w=400", "Luxury", "₹45,000"),
        ProductData("Gold Accent Chair", "https://images.unsplash.com/photo-1506439773649-6e0eb8cfb237?w=400", "Luxury", "₹62,500"),
        // Living Room
        ProductData("3-Seater Fabric Sofa", "https://images.unsplash.com/photo-1555041469-a586c61ea9bc?w=400", "Living Room", "₹27,999"),
        ProductData("Coffee Table Set", "https://images.unsplash.com/photo-1586023492125-27b2c045efd7?w=400", "Living Room", "₹9,499"),
        ProductData("TV Unit Cabinet", "https://images.unsplash.com/photo-1555041469-a586c61ea9bc?w=400", "Living Room", "₹15,999"),
        ProductData("Floor Lamp", "https://images.unsplash.com/photo-1507473885765-e6ed057f782c?w=400", "Living Room", "₹4,299"),
        // Bed Room
        ProductData("Queen Bed with Storage", "https://images.unsplash.com/photo-1505693416388-ac5ce068fe85?w=400", "Bed Room", "₹28,499"),
        ProductData("Wooden Wardrobe", "https://images.unsplash.com/photo-1595428774223-ef52624120d2?w=400", "Bed Room", "₹35,000"),
        ProductData("Bedside Table", "https://images.unsplash.com/photo-1586023492125-27b2c045efd7?w=400", "Bed Room", "₹6,999"),
        ProductData("Dressing Table", "https://images.unsplash.com/photo-1555041469-a586c61ea9bc?w=400", "Bed Room", "₹11,999"),
        // Mattresses
        ProductData("Memory Foam Mattress", "https://images.unsplash.com/photo-1631049307264-da0ec9d70304?w=400", "Mattresses", "₹22,999"),
        ProductData("Ortho Spring Mattress", "https://images.unsplash.com/photo-1631049307264-da0ec9d70304?w=400", "Mattresses", "₹18,499"),
        ProductData("Latex Cool Gel Mattress", "https://images.unsplash.com/photo-1631049307264-da0ec9d70304?w=400", "Mattresses", "₹29,999")
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
