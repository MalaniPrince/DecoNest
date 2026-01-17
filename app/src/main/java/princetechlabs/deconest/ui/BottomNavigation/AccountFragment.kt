package princetechlabs.deconest.ui.BottomNavigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import princetechlabs.deconest.R
import princetechlabs.deconest.databinding.FragmentAccountBinding
import princetechlabs.deconest.ui.adpter.ProfileAdapter
import princetechlabs.deconest.ui.data.ProfileItem

class AccountFragment : Fragment() {

    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): ConstraintLayout? {
        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.profileRecycler.layoutManager =
            GridLayoutManager(requireContext(), 2)

        binding.profileRecycler.setHasFixedSize(true)

        val list = listOf(
            ProfileItem(
                R.drawable.ic_orders,
                "My Orders",
                "Track your order, check details"
            ),
            ProfileItem(
                R.drawable.ic_wallet,
                "My Wallet",
                "0 Credits"
            ),
            ProfileItem(
                R.drawable.ic_review,
                "My Reviews",
                "Share your experience"
            ),
            ProfileItem(
                R.drawable.ic_wishlist,
                "Wishlist",
                "Your favourites"
            ),
            ProfileItem(
                R.drawable.ic_recent,
                "Recently Viewed",
                "View your history"
            ),
            ProfileItem(
                R.drawable.ic_address,
                "Address Book",
                "Manage your addresses"
            )
        )
        binding.profileRecycler.adapter = ProfileAdapter(list)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}