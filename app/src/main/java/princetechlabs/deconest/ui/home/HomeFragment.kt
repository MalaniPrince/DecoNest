package princetechlabs.deconest.ui.home

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import princetechlabs.deconest.R
import princetechlabs.deconest.databinding.FragmentFirstBinding
import princetechlabs.deconest.ui.adpter.CategoryGridHomeAdapter
import princetechlabs.deconest.ui.adpter.CategoryTabAdapter
import princetechlabs.deconest.ui.adpter.ImageSliderAdapter
import princetechlabs.deconest.ui.adpter.ProductAdapter
import princetechlabs.deconest.ui.data.ProductData
import princetechlabs.deconest.ui.utils.CustomDialog
import princetechlabs.deconest.ui.utils.MasterDataUtils
import princetechlabs.deconest.ui.utils.ProductRepository
import princetechlabs.deconest.ui.utils.RecentlyViewedRepository

class HomeFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    private lateinit var imageSliderAdapter: ImageSliderAdapter
    private lateinit var trendingAdapter: ProductAdapter
    private lateinit var newArrivalsAdapter: ProductAdapter
    private lateinit var recentlyViewedAdapter: ProductAdapter

    private val recentlyViewedListener: () -> Unit = {
        activity?.runOnUiThread { updateRecentlyViewed() }
    }

    private val productListener: (List<ProductData>) -> Unit = { products ->
        activity?.runOnUiThread {
            trendingAdapter.updateList(ProductRepository.getTrending())
            newArrivalsAdapter.updateList(ProductRepository.getNewArrivals())
        }
    }

    private var currentPage = 0
    private val handler = Handler(Looper.getMainLooper())
    private val delay: Long = 3000
    private val runnable: Runnable = object : Runnable {
        override fun run() {
            if (_binding != null) {
                val totalItems = imageSliderAdapter.count
                if (totalItems > 0) {
                    currentPage = (currentPage + 1) % totalItems
                    binding.viewPager.setCurrentItem(currentPage, true)
                }
                handler.postDelayed(this, delay)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupCategoryTabs()
        setupCategoryGrid()
        setupViewPager()
        setupProductSections()
        setupRecentlyViewed()
        setupSwipeRefresh()
        setupHeaderActions()
        RecentlyViewedRepository.addListener(recentlyViewedListener)
        ProductRepository.addListener(productListener)

        // Load products from API
        loadProductsFromApi()
    }

    private fun loadProductsFromApi() {
        viewLifecycleOwner.lifecycleScope.launch {
            // Show local data first (instant), then update with API data
            val result = ProductRepository.loadProducts()
            if (_binding != null) {
                val products = ProductRepository.getAllProducts()
                trendingAdapter.updateList(products.take(6))
                newArrivalsAdapter.updateList(products.reversed().take(6))
                binding.swipeRefresh.isRefreshing = false
            }
        }
    }

    private fun setupHeaderActions() {
        binding.layoutSearch.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, SearchFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefresh.setColorSchemeColors(
            resources.getColor(R.color.color_tab_active, null)
        )
        binding.swipeRefresh.setOnRefreshListener {
            viewLifecycleOwner.lifecycleScope.launch {
                ProductRepository.loadProducts(forceRefresh = true)
                updateRecentlyViewed()
                if (_binding != null) binding.swipeRefresh.isRefreshing = false
            }
        }
    }

    private fun setupCategoryTabs() {
        val tabs = MasterDataUtils.categoryTabs()
        val tabAdapter = CategoryTabAdapter(tabs, selectedIndex = 0) { index ->
            val selected = tabs[index]
            val products = if (selected == "All") ProductRepository.getAllProducts()
            else ProductRepository.getByCategory(selected)
            trendingAdapter.updateList(products.take(6))
            newArrivalsAdapter.updateList(products.reversed().take(6))
        }
        binding.rvCategoryTabs.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvCategoryTabs.adapter = tabAdapter
    }

    private fun setupCategoryGrid() {
        val categoryItems = MasterDataUtils.homeCategoryItems()
        binding.rvCategoryGrid.layoutManager =
            GridLayoutManager(requireContext(), 2, GridLayoutManager.HORIZONTAL, false)
        binding.rvCategoryGrid.adapter = CategoryGridHomeAdapter(categoryItems) { item ->
            // Map grid item name to category filter tab name
            val tabName = when {
                item.name.contains("Furniture",   ignoreCase = true) -> "Furniture"
                item.name.contains("Living",      ignoreCase = true) -> "Living Room"
                item.name.contains("Kitchen",     ignoreCase = true) -> "Kitchen"
                item.name.contains("Mattress",    ignoreCase = true) -> "Mattresses"
                item.name.contains("Lamp",        ignoreCase = true) -> "Living Room"
                item.name.contains("Sofa",        ignoreCase = true) -> "Furniture"
                item.name.contains("Modular",     ignoreCase = true) -> "Furniture"
                item.name.contains("Furnishing",  ignoreCase = true) -> "Living Room"
                item.name.contains("Decor",       ignoreCase = true) -> "Living Room"
                else -> item.name
            }
            val products = princetechlabs.deconest.ui.utils.ProductRepository.getByCategory(tabName)
                .ifEmpty { princetechlabs.deconest.ui.utils.ProductRepository.getAllProducts() }
            trendingAdapter.updateList(products.take(6))
            newArrivalsAdapter.updateList(products.reversed().take(6))
            // Scroll down to show filtered products
            binding.swipeRefresh.post {
                (view?.parent?.parent as? androidx.core.widget.NestedScrollView)
                    ?.smoothScrollTo(0, binding.recyclerView.top + 200)
            }
        }
    }

    private fun setupViewPager() {
        imageSliderAdapter = ImageSliderAdapter(
            requireContext(), MasterDataUtils.getBanners()
        )
        binding.viewPager.adapter = imageSliderAdapter
    }

    private fun setupProductSections() {
        // Start with local data, API will update via listener
        val localProducts = ProductRepository.getAllProducts()

        trendingAdapter = ProductAdapter(localProducts.take(6)) { product ->
            openProductDetail(product)
        }
        binding.recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerView.adapter = trendingAdapter

        newArrivalsAdapter = ProductAdapter(localProducts.reversed().take(6)) { product ->
            openProductDetail(product)
        }
        binding.recyclerView2.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerView2.adapter = newArrivalsAdapter
    }

    private fun setupRecentlyViewed() {
        recentlyViewedAdapter = ProductAdapter(emptyList()) { product -> openProductDetail(product) }
        binding.rvRecentlyViewed.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvRecentlyViewed.adapter = recentlyViewedAdapter
        updateRecentlyViewed()
    }

    private fun updateRecentlyViewed() {
        val items = RecentlyViewedRepository.items.toList()
        binding.layoutRecentlyViewed.visibility = if (items.isEmpty()) View.GONE else View.VISIBLE
        if (items.isNotEmpty()) recentlyViewedAdapter.updateList(items)
    }

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

    override fun onResume() {
        super.onResume()
        handler.postDelayed(runnable, delay)
        updateRecentlyViewed()
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        RecentlyViewedRepository.removeListener(recentlyViewedListener)
        ProductRepository.removeListener(productListener)
        _binding = null
    }
}
