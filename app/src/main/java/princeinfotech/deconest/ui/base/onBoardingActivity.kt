package princeinfotech.deconest.ui.base

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import princeinfotech.deconest.databinding.ActivityOnBoardingBinding
import princeinfotech.deconest.ui.login.LoginActivity

class onBoardingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnBoardingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.getstartButton.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}