package princetechlabs.deconest.ui.BottomNavigation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import princetechlabs.deconest.R
import princetechlabs.deconest.databinding.FragmentCartBinding
import princetechlabs.deconest.ui.adpter.CartAdapter
import princetechlabs.deconest.ui.home.CheckoutActivity
import princetechlabs.deconest.ui.utils.CartRepository

class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private lateinit var cartAdapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupCartRecycler()
        updateSummary()

        binding.btnCheckout.setOnClickListener {
            startActivity(Intent(requireContext(), CheckoutActivity::class.java))
        }

        binding.btnShopNow.setOnClickListener {
            val homeFragment = princetechlabs.deconest.ui.home.HomeFragment()
            parentFragmentManager.beginTransaction()
                .replace(princetechlabs.deconest.R.id.fragmentContainer, homeFragment)
                .commit()
            val mainActivity = requireActivity() as princetechlabs.deconest.ui.home.HomeMainActivity
            mainActivity.findViewById<com.google.android.material.bottomnavigation.BottomNavigationView>(princetechlabs.deconest.R.id.bottomNavigationView).selectedItemId = princetechlabs.deconest.R.id.HomeFragment
        }
    }

    private fun setupCartRecycler() {
        cartAdapter = CartAdapter(CartRepository.items) {
            updateSummary()
        }
        binding.rvCartItems.layoutManager = LinearLayoutManager(requireContext())
        binding.rvCartItems.adapter = cartAdapter
        binding.rvCartItems.layoutAnimation =
            AnimationUtils.loadLayoutAnimation(requireContext(), R.anim.layout_animation_slide_up)

        updateCartVisibility()
    }

    private fun updateCartVisibility() {
        if (CartRepository.items.isEmpty()) {
            binding.layoutEmptyCart.visibility = View.VISIBLE
            binding.layoutCartContent.visibility = View.GONE
            val anim = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in)
            binding.layoutEmptyCart.startAnimation(anim)
        } else {
            binding.layoutEmptyCart.visibility = View.GONE
            binding.layoutCartContent.visibility = View.VISIBLE
            binding.rvCartItems.scheduleLayoutAnimation()
        }
    }

    private fun updateSummary() {
        if (CartRepository.items.isEmpty()) {
            binding.tvCartCount.text = ""
            updateCartVisibility()
            return
        }

        val subtotal = CartRepository.getTotalPrice()
        val discount = (subtotal * 0.15).toInt()
        val total = subtotal - discount
        val totalItems = CartRepository.getTotalCount()

        binding.tvCartCount.text = "$totalItems item${if (totalItems > 1) "s" else ""}"
        binding.tvSubtotal.text = "₹${String.format("%,d", subtotal)}"
        binding.tvDiscount.text = "-₹${String.format("%,d", discount)}"
        binding.tvTotal.text = "₹${String.format("%,d", total)}"

        updateCartVisibility()
    }

    override fun onResume() {
        super.onResume()
        if (_binding != null) {
            cartAdapter.notifyDataSetChanged()
            updateSummary()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
