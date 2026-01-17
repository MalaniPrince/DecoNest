package princetechlabs.deconest.ui.BottomNavigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import princetechlabs.deconest.R
import princetechlabs.deconest.data.CategoryData
import princetechlabs.deconest.data.CategoryTwo
import princetechlabs.deconest.databinding.FragmentCategoryBinding
import princetechlabs.deconest.ui.adpter.CategoryAdapter
import princetechlabs.deconest.ui.adpter.CategoryAdapterTwo

class CategoryFragment : Fragment() {

    private var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.categoryRecycler.layoutManager =
            LinearLayoutManager(requireContext())

        binding.categoryRecycler.adapter =
            CategoryAdapter(getCategoryList())
    }

    private fun getCategoryList(): List<CategoryData> {
        return listOf(
            CategoryData(
                "Furniture",
                "Sofas, Sectional Sofas, Sofa Cum Beds",
                R.drawable.furniture,
                tworecyclelist = listOf(
                    CategoryTwo("Sofas", true),
                    CategoryTwo("Sectional Sofas", true),
                    CategoryTwo("Sofa Cum Beds", true),
                    CategoryTwo("Chaise Loungers", false),
                )
            ),


            CategoryData(
                "Sofas & Seating",
                "Sofas, Recliners, Chairs",
                R.drawable.sofaseting,
                tworecyclelist = listOf(
                    CategoryTwo("Sofas", true),
                    CategoryTwo("Sofa Sets", false),
                    CategoryTwo("Sectional Sofas", true),
                    CategoryTwo("Sofa Cum Beds", true),
                )
            ),
            CategoryData(
                "Mattresses",
                "King, Queen, Single Size",
                R.drawable.mattresses,
                tworecyclelist = listOf(
                    CategoryTwo("King Size Mattresses", false),
                    CategoryTwo("Queen Size Mattresses", false),
                    CategoryTwo("Single Size Mattresses", false),
                    CategoryTwo("Foldable Mattresses", false),
                    CategoryTwo("Baby Mattresses", false)
                )
            ),
            CategoryData("Home Decor", "Vases, Figurines, Wall Art", R.drawable.homedecor, tworecyclelist = listOf(
                CategoryTwo("Vases", true),
                CategoryTwo("Figurines", false),
                CategoryTwo("Collectibles", false),
                CategoryTwo("Showpieces", false)
            )),
            CategoryData("Furnishings", "Bed Sheets, Curtains, Carpets", R.drawable.furnishings, tworecyclelist = listOf(
                CategoryTwo("Bed Sheets", true),
                CategoryTwo("Blankets & Quilts", true),
                CategoryTwo("Bath Linen", true),
                CategoryTwo("Bed Linen", true),
            )),
            CategoryData("Lamps & Lighting", "Table Lamps, Floor Lamps", R.drawable.lamli, tworecyclelist = listOf(
                CategoryTwo("Floor Lamps", false),
                CategoryTwo("Shelf Floor Lamps", false),
                CategoryTwo("Table Lamps", false),
                CategoryTwo("Night Lamps", false),
                CategoryTwo("Work and Study Lamps", false)
            )),
            CategoryData("Kitchen & Dining", "Cookware, Dinnerware", R.drawable.kitchn, tworecyclelist = listOf(
                CategoryTwo("Serveware", true),
                CategoryTwo("Jugs", false),
                CategoryTwo("Bakeware", true),
                CategoryTwo("Dinnerware", true),
                CategoryTwo("Cookware", true),),
            ),
            CategoryData("Luxury", "Living Room, Bedroom", R.drawable.living, tworecyclelist = listOf(),false),
            CategoryData("Modular", "Modular Kitchen, Wardrobe", R.drawable.module,tworecyclelist = listOf(),false),
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
