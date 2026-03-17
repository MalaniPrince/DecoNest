package princetechlabs.deconest.ui.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import princetechlabs.deconest.databinding.FragmentFirstBinding
import princetechlabs.deconest.ui.adpter.CategoryGridHomeAdapter
import princetechlabs.deconest.ui.adpter.CategoryTabAdapter
import princetechlabs.deconest.ui.adpter.ImageSliderAdapter
import princetechlabs.deconest.ui.adpter.ProductAdapter
import princetechlabs.deconest.ui.utils.MasterDataUtils

class HomeFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    private lateinit var imageSliderAdapter: ImageSliderAdapter
    private lateinit var trendingAdapter: ProductAdapter
    private lateinit var newArrivalsAdapter: ProductAdapter
    private lateinit var filteredAdapter: ProductAdapter

    private var currentPage = 0
    private val handler = Handler(Looper.getMainLooper())
    private val delay: Long = 3000
    private val runnable: Runnable = object : Runnable {
        override fun run() {
            val totalItems = imageSliderAdapter.count
            currentPage = (currentPage + 1) % totalItems
            binding.viewPager.setCurrentItem(currentPage, true)
            handler.postDelayed(this, delay)
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
        setupFilteredSection()
    }

    private fun setupCategoryTabs() {
        val tabs = MasterDataUtils.categoryTabs()
        val tabAdapter = CategoryTabAdapter(tabs, selectedIndex = 0) { index ->
            val selected = tabs[index]
            if (selected == "All") {
                showDefaultSections()
            } else {
                showFilteredSection(selected)
            }
        }
        binding.rvCategoryTabs.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvCategoryTabs.adapter = tabAdapter
    }

    private fun setupCategoryGrid() {
        val categoryItems = MasterDataUtils.homeCategoryItems()
        binding.rvCategoryGrid.layoutManager =
            GridLayoutManager(requireContext(), 2, GridLayoutManager.HORIZONTAL, false)
        binding.rvCategoryGrid.adapter = CategoryGridHomeAdapter(categoryItems)
    }

    private fun setupViewPager() {
        imageSliderAdapter = ImageSliderAdapter(
            requireContext(), MasterDataUtils.viewPagerImage(requireContext())
        )
        binding.viewPager.adapter = imageSliderAdapter
    }

    private fun setupProductSections() {
        trendingAdapter = ProductAdapter(MasterDataUtils.getTrendingProducts())
        binding.recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerView.adapter = trendingAdapter

        newArrivalsAdapter = ProductAdapter(MasterDataUtils.getNewArrivals())
        binding.recyclerView2.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerView2.adapter = newArrivalsAdapter
    }

    private fun setupFilteredSection() {
        filteredAdapter = ProductAdapter(emptyList())
        binding.rvFilteredProducts.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvFilteredProducts.adapter = filteredAdapter
    }

    private fun showFilteredSection(category: String) {
        val filtered = MasterDataUtils.getProductsByCategory(category)

        binding.layoutDefaultSections.visibility = View.GONE
        binding.layoutFilterSection.visibility = View.VISIBLE

        binding.tvFilterTitle.text = category
        binding.tvFilterCount.text = "${filtered.size} items"

        if (filtered.isEmpty()) {
            binding.rvFilteredProducts.visibility = View.GONE
            binding.tvNoProducts.visibility = View.VISIBLE
        } else {
            binding.rvFilteredProducts.visibility = View.VISIBLE
            binding.tvNoProducts.visibility = View.GONE
            filteredAdapter.updateList(filtered)
        }
    }

    private fun showDefaultSections() {
        binding.layoutFilterSection.visibility = View.GONE
        binding.layoutDefaultSections.visibility = View.VISIBLE
    }

    override fun onResume() {
        super.onResume()
        handler.postDelayed(runnable, delay)
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
