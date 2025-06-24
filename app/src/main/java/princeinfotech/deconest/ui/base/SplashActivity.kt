package princeinfotech.deconest.ui.base

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import princeinfotech.deconest.R
import princeinfotech.deconest.ui.home.HomeMainActivity
import princeinfotech.deconest.ui.utils.PreferenceHelper


class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
           if( PreferenceHelper.isUserLoggedIn(this)){
               startActivity(Intent(this,HomeMainActivity::class.java))
           }else {

               startActivity(Intent(this, onBoardingActivity::class.java))
           }
            finish()
        },2000)
    }
}
