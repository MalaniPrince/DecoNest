package princetechlabs.deconest.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import princetechlabs.deconest.R
import princetechlabs.deconest.databinding.ActivityOrderTrackingBinding
import princetechlabs.deconest.ui.adpter.TrackingAdapter
import princetechlabs.deconest.ui.data.TrackingNodeData

class OrderTrackingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOrderTrackingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderTrackingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)

        val orderId = intent.getStringExtra("order_id") ?: "Unknown"

        binding.tvTrackingOrderId.text = "Order #$orderId"
        binding.btnBackTracking.setOnClickListener { finish() }

        setupTrackingNodes()
    }

    private fun setupTrackingNodes() {
        val list = listOf(
            TrackingNodeData("Order Placed", "Your order has been received.", true, false),
            TrackingNodeData("Order Confirmed", "Your order has been confirmed.", true, false),
            TrackingNodeData("Order Processed", "We are preparing your order.", true, false),
            TrackingNodeData("Out for Delivery", "Your order is out for delivery.", false, false),
            TrackingNodeData("Delivered", "Your order has been delivered.", false, true)
        )
        
        binding.rvTrackingNodes.apply {
            layoutManager = LinearLayoutManager(this@OrderTrackingActivity)
            adapter = TrackingAdapter(list)
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}
