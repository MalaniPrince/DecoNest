package princetechlabs.deconest.ui.BottomNavigation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import princetechlabs.deconest.R
import princetechlabs.deconest.databinding.FragmentCategoryBinding
import princetechlabs.deconest.ui.adpter.CategoryTabAdapter
import princetechlabs.deconest.ui.adpter.GridProductAdapter
import princetechlabs.deconest.ui.data.ProductData
import princetechlabs.deconest.ui.home.ProductDetailActivity
import princetechlabs.deconest.ui.utils.CustomDialog
import princetechlabs.deconest.ui.utils.MasterDataUtils
import princetechlabs.deconest.ui.utils.ProductRepository

class CategoryFragment : Fragment() {

    private var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding!!

    private lateinit var gridAdapter: GridProductAdapter
    private val tabs = MasterDataUtils.categoryTabs()

    private var currentCategory = "All"
    private var sortAscending: Boolean? = null

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
        setupCategoryTabs()
        setupProductGrid()
        setupSortButton()
        showProducts("All")
    }

    private fun setupCategoryTabs() {
        val tabAdapter = CategoryTabAdapter(tabs, selectedIndex = 0) { index ->
            showProducts(tabs[index])
        }
        binding.rvCategoryFilterTabs.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvCategoryFilterTabs.adapter = tabAdapter
    }

    private fun setupProductGrid() {
        gridAdapter = GridProductAdapter(emptyList()) { product ->
            openProductDetail(product)
        }
        binding.rvCategoryProducts.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvCategoryProducts.adapter = gridAdapter
    }

    private fun setupSortButton() {
        binding.btnSort.setOnClickListener {
            val options = arrayOf(
                getString(R.string.sort_default),
                getString(R.string.sort_low_to_high),
                getString(R.string.sort_high_to_low)
            )
            CustomDialog.showSortDialog(
                requireContext(),
                getString(R.string.sort_by),
                options
            ) { index ->
                sortAscending = when (index) {
                    1 -> true
                    2 -> false
                    else -> null
                }
                updateSortIcon()
                applyAndDisplay()
            }
        }
    }

    private fun updateSortIcon() {
        val tint = if (sortAscending != null)
            resources.getColor(R.color.color_tab_active, null)
        else
            resources.getColor(R.color.color_gray_text, null)
        binding.btnSort.setColorFilter(tint)
    }

    private fun showProducts(category: String) {
        currentCategory = category
        applyAndDisplay()
    }

    private fun applyAndDisplay() {
        var products = ProductRepository.getByCategory(currentCategory)

        products = when (sortAscending) {
            true  -> products.sortedBy { parsePrice(it.price) }
            false -> products.sortedByDescending { parsePrice(it.price) }
            null  -> products
        }

        gridAdapter.updateList(products)

        val label = if (currentCategory == "All")
            "${getString(R.string.all_products_label)} (${products.size})"
        else
            "$currentCategory · ${products.size} ${getString(R.string.items_suffix)}"

        binding.tvCategoryProductCount.text = label

        val animation = AnimationUtils.loadLayoutAnimation(
            requireContext(), R.anim.layout_animation_fall_down
        )
        binding.rvCategoryProducts.layoutAnimation = animation
        binding.rvCategoryProducts.scheduleLayoutAnimation()
    }

    private fun parsePrice(price: String): Int =
        price.filter { it.isDigit() }.toIntOrNull() ?: 0

    private fun openProductDetail(product: ProductData) {
        startActivity(Intent(requireContext(), ProductDetailActivity::class.java).apply {
            putExtra(ProductDetailActivity.EXTRA_NAME, product.name)
            putExtra(ProductDetailActivity.EXTRA_IMAGE, product.imageUrl)
            putExtra(ProductDetailActivity.EXTRA_CATEGORY, product.category)
            putExtra(ProductDetailActivity.EXTRA_PRICE, product.price)
            putExtra(ProductDetailActivity.EXTRA_RATING, product.rating)
            putExtra(ProductDetailActivity.EXTRA_REVIEW_COUNT, product.reviewCount)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
