package princetechlabs.deconest.ui.BottomNavigation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import princetechlabs.deconest.R
import princetechlabs.deconest.databinding.FragmentAccountBinding
import princetechlabs.deconest.ui.adpter.ProfileAdapter
import princetechlabs.deconest.ui.data.ProfileItem
import princetechlabs.deconest.ui.home.AddressManagementActivity
import princetechlabs.deconest.ui.home.EditProfileActivity
import princetechlabs.deconest.ui.home.InfoActivity
import princetechlabs.deconest.ui.home.OrdersFragment
import princetechlabs.deconest.ui.home.RecentlyViewedFragment
import princetechlabs.deconest.ui.home.ReviewsActivity
import princetechlabs.deconest.ui.home.WalletActivity
import princetechlabs.deconest.ui.home.WishlistFragment
import princetechlabs.deconest.ui.login.LoginActivity
import princetechlabs.deconest.ui.utils.CustomDialog
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
        setupDarkModeSwitch()
    }

    override fun onResume() {
        super.onResume()
        if (_binding != null) setupProfileInfo()
    }

    private fun setupProfileInfo() {
        val name = PreferenceHelper.getName(requireContext())
        val email = PreferenceHelper.getUserEmail(requireContext())
        val phone = PreferenceHelper.getPhone(requireContext())
        if (!name.isNullOrEmpty()) {
            binding.tvProfileName.text = name
            binding.tvProfileInitial.text = name.first().uppercaseChar().toString()
        }
        if (!email.isNullOrEmpty()) {
            binding.tvProfileEmail.text = email
        }
        if (!phone.isNullOrEmpty()) {
            binding.tvProfilePhone.text = phone
        }
    }

    private fun setupDarkModeSwitch() {
        val isDark = PreferenceHelper.isDarkMode(requireContext())
        binding.switchDarkMode.isChecked = isDark
        binding.switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            PreferenceHelper.setDarkMode(requireContext(), isChecked)
            AppCompatDelegate.setDefaultNightMode(
                if (isChecked) AppCompatDelegate.MODE_NIGHT_YES
                else AppCompatDelegate.MODE_NIGHT_NO
            )
        }
    }

    private fun setupRecyclerView() {
        val list = listOf(
            ProfileItem(R.drawable.ic_orders,   getString(R.string.account_orders),           getString(R.string.account_orders_sub),   "#FFF3E0"),
            ProfileItem(R.drawable.ic_wallet,   getString(R.string.account_wallet),           getString(R.string.account_wallet_sub),   "#E8F5E9"),
            ProfileItem(R.drawable.ic_review,   getString(R.string.account_reviews),          getString(R.string.account_reviews_sub),  "#E3F2FD"),
            ProfileItem(R.drawable.ic_wishlist, getString(R.string.account_wishlist),         getString(R.string.account_wishlist_sub), "#FCE4EC"),
            ProfileItem(R.drawable.ic_recent,   getString(R.string.account_recently_viewed),  getString(R.string.account_recent_sub),   "#F3E5F5"),
            ProfileItem(R.drawable.ic_address,  getString(R.string.account_address),          getString(R.string.account_address_sub),  "#E0F7FA")
        )
        binding.profileRecycler.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.profileRecycler.setHasFixedSize(true)
        binding.profileRecycler.adapter = ProfileAdapter(list) { item ->
            handleProfileItemClick(item.title)
        }
    }

    private fun handleProfileItemClick(title: String) {
        when (title) {
            getString(R.string.account_orders) -> loadFragment(OrdersFragment())
            getString(R.string.account_wishlist) -> loadFragment(WishlistFragment())
            getString(R.string.account_recently_viewed) -> loadFragment(RecentlyViewedFragment())

            getString(R.string.account_wallet) -> startActivity(Intent(requireContext(), WalletActivity::class.java))
            getString(R.string.account_reviews) -> startActivity(Intent(requireContext(), ReviewsActivity::class.java))
            getString(R.string.account_address) -> startActivity(Intent(requireContext(), AddressManagementActivity::class.java))
            else -> CustomDialog.showInfo(
                requireContext(),
                title,
                getString(R.string.coming_soon_message)
            )
        }
    }

    private fun setupClickListeners() {
        binding.btnLogout.setOnClickListener {
            CustomDialog.showConfirm(
                requireContext(),
                title = getString(R.string.confirm_logout_title),
                message = getString(R.string.confirm_logout_message),
                positiveText = getString(R.string.btn_logout),
                negativeText = getString(R.string.cancel)
            ) {
                PreferenceHelper.clearAll(requireContext())
                startActivity(Intent(requireActivity(), LoginActivity::class.java))
                requireActivity().finish()
            }
        }
        binding.btnEditProfile.setOnClickListener {
            startActivity(Intent(requireContext(), EditProfileActivity::class.java))
        }
        binding.menuHelpCenter.setOnClickListener {
            startActivity(Intent(requireContext(), InfoActivity::class.java).apply {
                putExtra(InfoActivity.EXTRA_TYPE, InfoActivity.TYPE_HELP)
            })
        }
        binding.menuPrivacy.setOnClickListener {
            startActivity(Intent(requireContext(), InfoActivity::class.java).apply {
                putExtra(InfoActivity.EXTRA_TYPE, InfoActivity.TYPE_PRIVACY)
            })
        }
        binding.menuAbout.setOnClickListener {
            startActivity(Intent(requireContext(), InfoActivity::class.java).apply {
                putExtra(InfoActivity.EXTRA_TYPE, InfoActivity.TYPE_ABOUT)
            })
        }
    }

    private fun loadFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
