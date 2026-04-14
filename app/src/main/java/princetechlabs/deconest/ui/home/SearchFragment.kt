package princetechlabs.deconest.ui.home

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import princetechlabs.deconest.databinding.FragmentSearchBinding
import princetechlabs.deconest.ui.adpter.GridProductAdapter
import princetechlabs.deconest.ui.data.ProductData
import princetechlabs.deconest.ui.utils.ProductRepository

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var searchAdapter: GridProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val allProducts = ProductRepository.getAllProducts()
        searchAdapter = GridProductAdapter(allProducts) { product -> openDetail(product) }
        binding.rvSearchResults.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvSearchResults.adapter = searchAdapter

        showResults(allProducts)

        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString().trim()
                binding.btnClearSearch.visibility = if (query.isNotEmpty()) View.VISIBLE else View.GONE
                val filtered = ProductRepository.search(query)
                showResults(filtered)
                binding.tvSearchLabel.text =
                    if (query.isEmpty()) "All Products (${filtered.size})"
                    else "Results for \"$query\" (${filtered.size})"
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        binding.btnClearSearch.setOnClickListener {
            binding.etSearch.text?.clear()
        }

        binding.btnSearchBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    private fun showResults(results: List<ProductData>) {
        searchAdapter.updateList(results)
        if (results.isEmpty()) {
            binding.rvSearchResults.visibility = View.GONE
            binding.layoutNoResults.visibility = View.VISIBLE
        } else {
            binding.rvSearchResults.visibility = View.VISIBLE
            binding.layoutNoResults.visibility = View.GONE
        }
    }

    private fun openDetail(product: ProductData) {
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
