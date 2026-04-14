package princetechlabs.deconest.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import princetechlabs.deconest.R
import princetechlabs.deconest.databinding.FragmentOrdersBinding
import princetechlabs.deconest.ui.adpter.OrderAdapter
import princetechlabs.deconest.ui.utils.OrderRepository

class OrdersFragment : Fragment() {

    private var _binding: FragmentOrdersBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrdersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val orders = OrderRepository.orders

        if (orders.isEmpty()) {
            binding.layoutEmptyOrders.visibility = View.VISIBLE
            binding.rvOrders.visibility = View.GONE
        } else {
            binding.layoutEmptyOrders.visibility = View.GONE
            binding.rvOrders.visibility = View.VISIBLE
            binding.rvOrders.layoutManager = LinearLayoutManager(requireContext())
            binding.rvOrders.adapter = OrderAdapter(orders)
        }

        binding.btnOrdersBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.btnShopNowOrders.setOnClickListener {
            val mainActivity = requireActivity() as HomeMainActivity
            val navView = mainActivity.findViewById<com.google.android.material.bottomnavigation.BottomNavigationView>(R.id.bottomNavigationView)
            navView.selectedItemId = R.id.HomeFragment
            
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, HomeFragment())
                .commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
