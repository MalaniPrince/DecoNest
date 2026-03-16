package princetechlabs.deconest.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import princetechlabs.deconest.R
import princetechlabs.deconest.databinding.ActivityMainBinding
import princetechlabs.deconest.ui.BottomNavigation.AccountFragment
import princetechlabs.deconest.ui.BottomNavigation.CartFragment
import princetechlabs.deconest.ui.BottomNavigation.CategoryFragment
import princetechlabs.deconest.ui.login.LoginActivity
import princetechlabs.deconest.ui.utils.PreferenceHelper

class HomeMainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadFragment(HomeFragment())
        setupBottomNavigation()
        setupToolbar()
        setupDrawer()
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.HomeFragment -> loadFragment(HomeFragment())
                R.id.AccountFragment -> loadFragment(AccountFragment())
                R.id.CartFragment -> loadFragment(CartFragment())
                R.id.CategoryFragment -> loadFragment(CategoryFragment())
            }
            true
        }
    }

    private fun setupToolbar() {
        binding.btnMenu.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }
        binding.imgCart.setOnClickListener {
            loadFragment(CartFragment())
            binding.bottomNavigationView.selectedItemId = R.id.CartFragment
        }
    }

    private fun setupDrawer() {
        val userName = PreferenceHelper.getName(this)
        if (!userName.isNullOrEmpty()) {
            binding.tvDrawerName.text = userName
            binding.tvDrawerInitial.text = userName.first().uppercaseChar().toString()
        }

        binding.ivDrawerClose.setOnClickListener {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }
        binding.btnDrawerOrders.setOnClickListener {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            loadFragment(AccountFragment())
            binding.bottomNavigationView.selectedItemId = R.id.AccountFragment
        }
        binding.btnDrawerWallet.setOnClickListener {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }
        binding.menuBrowseCategories.setOnClickListener {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            loadFragment(CategoryFragment())
            binding.bottomNavigationView.selectedItemId = R.id.CategoryFragment
        }
        binding.menuTrackOrder.setOnClickListener {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }
        binding.menuWishlist.setOnClickListener {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }
        binding.menuWallet.setOnClickListener {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }
        binding.menuAddress.setOnClickListener {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }
        binding.menuReviews.setOnClickListener {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }
        binding.menuHelp.setOnClickListener {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }
        binding.tvDrawerLogout.setOnClickListener {
            PreferenceHelper.clearAll(this)
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}
