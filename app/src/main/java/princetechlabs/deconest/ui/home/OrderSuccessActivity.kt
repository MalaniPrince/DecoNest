package princetechlabs.deconest.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import princetechlabs.deconest.R
import princetechlabs.deconest.databinding.ActivityOrderSuccessBinding

class OrderSuccessActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOrderSuccessBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderSuccessBinding.inflate(layoutInflater)
        setContentView(binding.root)
        overridePendingTransition(R.anim.scale_up, R.anim.fade_in)

        val orderId = intent.getStringExtra("order_id") ?: ""
        val amount = intent.getIntExtra("order_amount", 0)

        binding.tvSuccessOrderId.text = "#$orderId"
        binding.tvSuccessAmount.text = "₹${String.format("%,d", amount)}"

        binding.btnContinueShopping.setOnClickListener {
            startActivity(
                Intent(this, HomeMainActivity::class.java)
                    .apply { flags = Intent.FLAG_ACTIVITY_CLEAR_TOP }
            )
            finish()
        }

        binding.btnViewOrders.setOnClickListener {
            startActivity(
                Intent(this, HomeMainActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    putExtra("open_orders", true)
                }
            )
            finish()
        }
    }
}
