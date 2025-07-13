package princetechlabs.deconest.ui.base

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import princetechlabs.deconest.R
import princetechlabs.deconest.ui.home.HomeMainActivity
import princetechlabs.deconest.ui.login.LoginActivity
import princetechlabs.deconest.ui.utils.PreferenceHelper


class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

            // Moved inside the lambda block
            finish()
        }, 2000)

    }
}

