package princetechlabs.deconest.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import princetechlabs.deconest.R
import princetechlabs.deconest.databinding.ActivityMainBinding
import princetechlabs.deconest.ui.BottomNavigation.AccountFragment
import princetechlabs.deconest.ui.BottomNavigation.CartFragment
import princetechlabs.deconest.ui.BottomNavigation.CategoryFragment
import princetechlabs.deconest.ui.login.LoginActivity
import princetechlabs.deconest.ui.utils.CartRepository
import princetechlabs.deconest.ui.utils.CustomDialog
import princetechlabs.deconest.ui.utils.PreferenceHelper
import princetechlabs.deconest.ui.utils.OrderRepository
import princetechlabs.deconest.ui.utils.WishlistRepository

class HomeMainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val cartBadgeListener: () -> Unit = { updateCartBadge() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.getBooleanExtra("open_orders", false)) {
            loadFragment(OrdersFragment())
            binding.bottomNavigationView.selectedItemId = R.id.AccountFragment
        } else {
            loadFragment(HomeFragment())
        }

        setupBottomNavigation()
        setupToolbar()
        setupDrawer()
        setupCartBadge()
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            bounceNavIcon(item.itemId)
            when (item.itemId) {
                R.id.HomeFragment     -> loadFragment(HomeFragment())
                R.id.AccountFragment  -> loadFragment(AccountFragment())
                R.id.CartFragment     -> loadFragment(CartFragment())
                R.id.CategoryFragment -> loadFragment(CategoryFragment())
            }
            true
        }
    }

    private fun bounceNavIcon(itemId: Int) {
        val anim = AnimationUtils.loadAnimation(this, R.anim.bounce_icon)
        binding.bottomNavigationView.post {
            binding.bottomNavigationView.findViewById<android.view.View>(itemId)?.startAnimation(anim)
        }
    }

    private fun setupToolbar() {
        binding.btnMenu.setOnClickListener {
            updateDrawerStats()
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }
        binding.imgCart.setOnClickListener {
            loadFragment(CartFragment())
            binding.bottomNavigationView.selectedItemId = R.id.CartFragment
        }
        binding.imgSearch.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, SearchFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    private fun setupCartBadge() {
        CartRepository.addListener(cartBadgeListener)
        updateCartBadge()
    }

    private fun updateCartBadge() {
        val count = CartRepository.getTotalCount()
        val badge = binding.bottomNavigationView.getOrCreateBadge(R.id.CartFragment)
        badge.isVisible = count > 0
        badge.number = count
    }

    private fun updateDrawerStats() {
        val orderCount = OrderRepository.orders.size
        val wishlistCount = WishlistRepository.items.size
        binding.tvDrawerOrderCount.text = orderCount.toString()
        binding.tvDrawerWishlistCount.text = wishlistCount.toString()
    }

    private fun setupDrawer() {
        val userName = PreferenceHelper.getName(this)
        val userEmail = PreferenceHelper.getUserEmail(this)

        if (!userName.isNullOrEmpty()) {
            binding.tvDrawerName.text = userName
            binding.tvDrawerInitial.text = userName.first().uppercaseChar().toString()
        }
        if (!userEmail.isNullOrEmpty()) {
            binding.tvDrawerEmail.text = userEmail
        }

        updateDrawerStats()

        binding.ivDrawerClose.setOnClickListener {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }

        // Stats row taps
        binding.statOrders.setOnClickListener {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            loadFragment(OrdersFragment())
            binding.bottomNavigationView.selectedItemId = R.id.AccountFragment
        }
        binding.statWishlist.setOnClickListener {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, WishlistFragment())
                .addToBackStack(null)
                .commit()
        }
        binding.statWallet.setOnClickListener {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            startActivity(Intent(this, WalletActivity::class.java))
        }

        // Shopping section
        binding.menuMyCart.setOnClickListener {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            loadFragment(CartFragment())
            binding.bottomNavigationView.selectedItemId = R.id.CartFragment
        }
        binding.menuBrowseCategories.setOnClickListener {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            loadFragment(CategoryFragment())
            binding.bottomNavigationView.selectedItemId = R.id.CategoryFragment
        }

        // My Account section
        binding.menuTrackOrder.setOnClickListener {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            loadFragment(OrdersFragment())
            binding.bottomNavigationView.selectedItemId = R.id.AccountFragment
        }
        binding.menuWallet.setOnClickListener {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            startActivity(Intent(this, WalletActivity::class.java))
        }
        binding.menuAddress.setOnClickListener {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            startActivity(Intent(this, AddressManagementActivity::class.java))
        }
        binding.menuWishlist.setOnClickListener {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, WishlistFragment())
                .addToBackStack(null)
                .commit()
        }
        binding.menuRecentlyViewed.setOnClickListener {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, RecentlyViewedFragment())
                .addToBackStack(null)
                .commit()
        }
        binding.menuReviews.setOnClickListener {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            startActivity(Intent(this, ReviewsActivity::class.java))
        }

        // Support section
        binding.menuPrivacyPolicy.setOnClickListener {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            startActivity(Intent(this, InfoActivity::class.java).apply {
                putExtra(InfoActivity.EXTRA_TYPE, InfoActivity.TYPE_PRIVACY)
            })
        }
        binding.menuHelpCenter.setOnClickListener {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            startActivity(Intent(this, InfoActivity::class.java).apply {
                putExtra(InfoActivity.EXTRA_TYPE, InfoActivity.TYPE_HELP)
            })
        }
        binding.menuAboutDecoNest.setOnClickListener {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            startActivity(Intent(this, InfoActivity::class.java).apply {
                putExtra(InfoActivity.EXTRA_TYPE, InfoActivity.TYPE_ABOUT)
            })
        }

        binding.tvDrawerLogout.setOnClickListener {
            CustomDialog.showConfirm(
                this,
                title = getString(R.string.confirm_logout_title),
                message = getString(R.string.confirm_logout_message),
                positiveText = getString(R.string.btn_logout),
                negativeText = getString(R.string.cancel)
            ) {
                PreferenceHelper.clearAll(this)
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }
    }

    fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    fun openDrawer() {
        updateDrawerStats()
        binding.drawerLayout.openDrawer(GravityCompat.START)
    }

    override fun onDestroy() {
        super.onDestroy()
        CartRepository.removeListener(cartBadgeListener)
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }
}
