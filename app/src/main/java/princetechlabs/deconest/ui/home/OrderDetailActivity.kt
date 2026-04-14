package princetechlabs.deconest.ui.home

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import princetechlabs.deconest.R
import princetechlabs.deconest.databinding.ActivityOrderDetailBinding
import princetechlabs.deconest.ui.adpter.CartAdapter
import princetechlabs.deconest.ui.utils.OrderRepository

class OrderDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOrderDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)

        val orderId = intent.getStringExtra("order_id") ?: ""
        val order = OrderRepository.orders.find { it.orderId == orderId }

        binding.btnOrderDetailBack.setOnClickListener { finish() }

        if (order == null) {
            Toast.makeText(this, "Order not found", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        binding.tvDetailOrderId.text = "Order #${order.orderId}"
        binding.tvDetailOrderStatus.text = order.status
        binding.tvDetailOrderAddress.text = order.address
        binding.tvDetailOrderTotal.text = "₹${String.format("%,d", order.totalAmount)}"
        binding.tvDetailItemCount.text = "${order.items.sumOf { it.quantity }} item(s)"

        binding.rvOrderDetailItems.layoutManager = LinearLayoutManager(this)
        binding.rvOrderDetailItems.adapter = CartAdapter(order.items.toMutableList()) {}
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}
