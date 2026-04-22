package princetechlabs.deconest.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import princetechlabs.deconest.R
import princetechlabs.deconest.databinding.FragmentRecentlyViewedBinding
import princetechlabs.deconest.ui.adpter.GridProductAdapter
import princetechlabs.deconest.ui.data.ProductData
import princetechlabs.deconest.ui.utils.RecentlyViewedRepository

class RecentlyViewedFragment : Fragment() {

    private var _binding: FragmentRecentlyViewedBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: GridProductAdapter

    private val recentListener: () -> Unit = {
        activity?.runOnUiThread { refreshList() }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecentlyViewedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = GridProductAdapter(emptyList()) { product -> openProductDetail(product) }
        binding.rvRecentlyViewed.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvRecentlyViewed.adapter = adapter

        binding.btnRecentBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.btnExploreRecent.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        RecentlyViewedRepository.addListener(recentListener)
        refreshList()
    }

    private fun refreshList() {
        val items = RecentlyViewedRepository.items.toList()
        binding.tvRecentCount.text = "${items.size} item${if (items.size != 1) "s" else ""}"

        if (items.isEmpty()) {
            binding.layoutEmptyRecent.visibility = View.VISIBLE
            binding.rvRecentlyViewed.visibility = View.GONE
        } else {
            binding.layoutEmptyRecent.visibility = View.GONE
            binding.rvRecentlyViewed.visibility = View.VISIBLE
            adapter.updateList(items)
            val anim = AnimationUtils.loadLayoutAnimation(requireContext(), R.anim.layout_animation_fall_down)
            binding.rvRecentlyViewed.layoutAnimation = anim
            binding.rvRecentlyViewed.scheduleLayoutAnimation()
        }
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

    override fun onDestroyView() {
        super.onDestroyView()
        RecentlyViewedRepository.removeListener(recentListener)
        _binding = null
    }
}
