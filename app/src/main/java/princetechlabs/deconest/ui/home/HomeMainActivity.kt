package princetechlabs.deconest.ui.home

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import princetechlabs.deconest.R
import princetechlabs.deconest.databinding.ActivityMainBinding
import princetechlabs.deconest.ui.BottomNavigation.AccountFragment
import princetechlabs.deconest.ui.BottomNavigation.CartFragment
import princetechlabs.deconest.ui.BottomNavigation.CategoryFragment

class HomeMainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadFragment(HomeFragment())

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

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}
