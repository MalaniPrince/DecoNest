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
import princetechlabs.deconest.databinding.FragmentWishlistBinding
import princetechlabs.deconest.ui.adpter.GridProductAdapter
import princetechlabs.deconest.ui.data.ProductData
import princetechlabs.deconest.ui.utils.WishlistRepository

class WishlistFragment : Fragment() {

    private var _binding: FragmentWishlistBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: GridProductAdapter

    private val wishlistListener: () -> Unit = {
        activity?.runOnUiThread { refreshList() }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWishlistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = GridProductAdapter(emptyList()) { product -> openProductDetail(product) }
        binding.rvWishlist.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvWishlist.adapter = adapter

        binding.btnWishlistBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.btnExploreWishlist.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        WishlistRepository.addListener(wishlistListener)
        refreshList()
    }

    private fun refreshList() {
        val items = WishlistRepository.items.toList()
        binding.tvWishlistCount.text = "${items.size} item${if (items.size != 1) "s" else ""}"

        if (items.isEmpty()) {
            binding.layoutEmptyWishlist.visibility = View.VISIBLE
            binding.rvWishlist.visibility = View.GONE
        } else {
            binding.layoutEmptyWishlist.visibility = View.GONE
            binding.rvWishlist.visibility = View.VISIBLE
            adapter.updateList(items)
            val anim = AnimationUtils.loadLayoutAnimation(requireContext(), R.anim.layout_animation_fall_down)
            binding.rvWishlist.layoutAnimation = anim
            binding.rvWishlist.scheduleLayoutAnimation()
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
        WishlistRepository.removeListener(wishlistListener)
        _binding = null
    }
}
