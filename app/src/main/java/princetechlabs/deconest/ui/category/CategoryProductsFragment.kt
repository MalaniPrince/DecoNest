package princetechlabs.deconest.ui.category

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import princetechlabs.deconest.adapter.CategoryProductsAdapter
import princetechlabs.deconest.databinding.FragmentCategoryProductsBinding
import princetechlabs.deconest.ui.home.ProductDetailActivity
import kotlin.getValue

class CategoryProductsFragment : Fragment() {

    private var _binding: FragmentCategoryProductsBinding? = null
    private val binding get() = _binding!!
    private val categoryName: String by lazy {
        arguments?.getString(ARG_CATEGORY_NAME) ?: ""
    }
    private lateinit var viewModel: CategoryProductsViewModel
    private lateinit var adapter: CategoryProductsAdapter
    companion object {
        private const val ARG_CATEGORY_NAME = "categoryName"

        fun newInstance(categoryName: String): CategoryProductsFragment {
            val fragment = CategoryProductsFragment()
            val args = Bundle()
            args.putString(ARG_CATEGORY_NAME, categoryName)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoryProductsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[CategoryProductsViewModel::class.java]

        setupToolbar()
        setupRecyclerView()
        observeViewModel()

        viewModel.fetchProductsByCategory(categoryName)
    }

    private fun setupToolbar() {
        binding.toolbar.title = categoryName
        binding.toolbar.setNavigationOnClickListener {
            if (parentFragmentManager.backStackEntryCount > 0) {
                parentFragmentManager.popBackStack()
            } else {
                requireActivity().onBackPressed()
            }
        }
    }

    private fun setupRecyclerView() {
        adapter = CategoryProductsAdapter { product ->
            val formattedPrice = "₹ ${String.format("%,.2f", product.price)}"
            val intent = Intent(requireContext(), ProductDetailActivity::class.java).apply {
                putExtra(ProductDetailActivity.EXTRA_NAME, product.name)
                putExtra(ProductDetailActivity.EXTRA_IMAGE, product.imageUrl)
                putExtra(ProductDetailActivity.EXTRA_CATEGORY, product.category)
                putExtra(ProductDetailActivity.EXTRA_PRICE, formattedPrice)
                putExtra(ProductDetailActivity.EXTRA_RATING, product.rating)
            }
            startActivity(intent)
        }
        binding.rvProducts.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvProducts.adapter = adapter
    }

    private fun observeViewModel() {
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.shimmerLayout.visibility = View.VISIBLE
                binding.shimmerLayout.startShimmer()
                binding.rvProducts.visibility = View.GONE
            } else {
                binding.shimmerLayout.stopShimmer()
                binding.shimmerLayout.visibility = View.GONE
                binding.rvProducts.visibility = View.VISIBLE
            }
        }

        viewModel.products.observe(viewLifecycleOwner) { products ->
            if (products.isEmpty()) {
                binding.layoutEmpty.visibility = View.VISIBLE
                binding.rvProducts.visibility = View.GONE
            } else {
                binding.layoutEmpty.visibility = View.GONE
                binding.rvProducts.visibility = View.VISIBLE
                adapter.submitList(products)
            }
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { error ->
            if (error != null) {
                binding.layoutError.visibility = View.VISIBLE
                binding.tvError.text = error
                binding.rvProducts.visibility = View.GONE
                binding.btnRetry.setOnClickListener {
                    viewModel.fetchProductsByCategory(categoryName)
                }
            } else {
                binding.layoutError.visibility = View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
