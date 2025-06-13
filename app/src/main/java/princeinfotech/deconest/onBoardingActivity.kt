package princeinfotech.deconest

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import princeinfotech.deconest.databinding.ActivityOnBoardingBinding

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