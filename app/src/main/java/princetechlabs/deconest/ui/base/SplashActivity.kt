package princetechlabs.deconest.ui.base

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import princetechlabs.deconest.R
import princetechlabs.deconest.ui.home.HomeMainActivity
import princetechlabs.deconest.ui.login.LoginActivity
import princetechlabs.deconest.ui.utils.PreferenceHelper


class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(
            if (PreferenceHelper.isDarkMode(this)) AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO
        )
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            if (PreferenceHelper.getOnBoardShow(this)) {
                if (PreferenceHelper.isUserLoggedIn(this)) {
                    startActivity(Intent(this, HomeMainActivity::class.java))
                } else {
                    startActivity(Intent(this,LoginActivity::class.java))
                }
            } else {
                startActivity(Intent(this, onBoardingActivity::class.java))
            }

            finish()
        }, 2000)

    }
}

