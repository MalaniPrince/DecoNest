package princeinfotech.deconest

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import princeinfotech.deconest.databinding.ActivityOnBoardingBinding

class onBoardingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnBoardingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
       binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.getstartButton.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }
    }
}