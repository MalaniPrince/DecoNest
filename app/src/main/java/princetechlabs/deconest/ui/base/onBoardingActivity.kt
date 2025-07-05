package princetechlabs.deconest.ui.base

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import princetechlabs.deconest.databinding.ActivityOnBoardingBinding
import princetechlabs.deconest.ui.login.LoginActivity
import princetechlabs.deconest.ui.utils.PreferenceHelper

class onBoardingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnBoardingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.getstartButton.setOnClickListener {
            PreferenceHelper.setOnBoarding(this,true)
            startActivity(Intent(this, LoginActivity::class.java))
            PreferenceHelper.setOnBoardShow(this, true)
        }
    }
}