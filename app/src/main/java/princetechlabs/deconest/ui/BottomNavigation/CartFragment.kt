package princetechlabs.deconest.ui.BottomNavigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import princetechlabs.deconest.databinding.FragmentCartBinding
import princetechlabs.deconest.ui.adpter.CartAdapter
import princetechlabs.deconest.ui.data.CartItem

class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private lateinit var cartAdapter: CartAdapter

    // Sample cart data
    private val cartItems = mutableListOf(
        CartItem("Modern L-Shape Sofa", "https://images.unsplash.com/photo-1555041469-a586c61ea9bc?w=400", "Furniture", 32999, 1),
        CartItem("Wooden Dining Table", "https://images.unsplash.com/photo-1577140917170-285929fb55b7?w=400", "Furniture", 18499, 1),
        CartItem("Floor Lamp", "https://images.unsplash.com/photo-1507473885765-e6ed057f782c?w=400", "Living Room", 4299, 2)
    )

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
    }

    private fun setupCartRecycler() {
        cartAdapter = CartAdapter(cartItems) {
            updateSummary()
        }
        binding.rvCartItems.layoutManager = LinearLayoutManager(requireContext())
        binding.rvCartItems.adapter = cartAdapter

        updateCartVisibility()
    }

    private fun updateCartVisibility() {
        if (cartItems.isEmpty()) {
            binding.layoutEmptyCart.visibility = View.VISIBLE
            binding.layoutCartContent.visibility = View.GONE
        } else {
            binding.layoutEmptyCart.visibility = View.GONE
            binding.layoutCartContent.visibility = View.VISIBLE
        }
    }

    private fun updateSummary() {
        if (cartItems.isEmpty()) {
            updateCartVisibility()
            return
        }

        val subtotal = cartAdapter.getTotalPrice()
        val discount = (subtotal * 0.15).toInt()
        val total = subtotal - discount
        val totalItems = cartItems.sumOf { it.quantity }

        binding.tvCartCount.text = "$totalItems item${if (totalItems > 1) "s" else ""}"
        binding.tvSubtotal.text = "₹${String.format("%,d", subtotal)}"
        binding.tvDiscount.text = "-₹${String.format("%,d", discount)}"
        binding.tvTotal.text = "₹${String.format("%,d", total)}"

        updateCartVisibility()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
