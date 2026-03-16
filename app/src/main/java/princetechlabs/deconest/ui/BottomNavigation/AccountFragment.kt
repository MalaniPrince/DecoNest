package princetechlabs.deconest.ui.BottomNavigation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import princetechlabs.deconest.R
import princetechlabs.deconest.databinding.FragmentAccountBinding
import princetechlabs.deconest.ui.adpter.ProfileAdapter
import princetechlabs.deconest.ui.data.ProfileItem
import princetechlabs.deconest.ui.login.LoginActivity
import princetechlabs.deconest.ui.utils.PreferenceHelper

class AccountFragment : Fragment() {

    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupProfileInfo()
        setupRecyclerView()
        setupClickListeners()
    }

    private fun setupProfileInfo() {
        val name = PreferenceHelper.getName(requireContext())
        val email = PreferenceHelper.getUserEmail(requireContext())
        if (!name.isNullOrEmpty()) {
            binding.tvProfileName.text = name
            binding.tvProfileInitial.text = name.first().uppercaseChar().toString()
        }
        if (!email.isNullOrEmpty() && email != "1") {
            binding.tvProfileEmail.text = email
        }
    }

    private fun setupRecyclerView() {
        val list = listOf(
            ProfileItem(R.drawable.ic_orders,   "My Orders",        "Track your orders",      "#FFF3E0"),
            ProfileItem(R.drawable.ic_wallet,   "My Wallet",        "0 Credits",              "#E8F5E9"),
            ProfileItem(R.drawable.ic_review,   "My Reviews",       "Share your experience",  "#E3F2FD"),
            ProfileItem(R.drawable.ic_wishlist, "Wishlist",         "Your favourites",        "#FCE4EC"),
            ProfileItem(R.drawable.ic_recent,   "Recently Viewed",  "View your history",      "#F3E5F5"),
            ProfileItem(R.drawable.ic_address,  "Address Book",     "Manage addresses",       "#E0F7FA")
        )
        binding.profileRecycler.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.profileRecycler.setHasFixedSize(true)
        binding.profileRecycler.adapter = ProfileAdapter(list)
    }

    private fun setupClickListeners() {
        binding.btnLogout.setOnClickListener {
            PreferenceHelper.clearAll(requireContext())
            startActivity(Intent(requireActivity(), LoginActivity::class.java))
            requireActivity().finish()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
