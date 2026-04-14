package princetechlabs.deconest.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import princetechlabs.deconest.R
import princetechlabs.deconest.databinding.ActivityNotificationsBinding
import princetechlabs.deconest.ui.adpter.ItemAdapter
import princetechlabs.deconest.ui.data.ProfileItem

class NotificationsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotificationsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)

        binding.btnBackNotify.setOnClickListener { finish() }

        setupNotifications()
    }

    private fun setupNotifications() {
        val list = listOf(
            ProfileItem(R.drawable.ic_orders, "Order Shipped", "Your order #DN9928 is on the way", "#FFF3E0"),
            ProfileItem(R.drawable.ic_search, "Special Offer", "Get 20% off on all sofas today!", "#E8F5E9"),
            ProfileItem(R.drawable.ic_wallet, "Wallet Credited", "₹500 added to your DecoWallet", "#E3F2FD"),
            ProfileItem(R.drawable.ic_review, "Review Request", "How was the L-Shape Sofa?", "#FCE4EC")
        )
        
        binding.rvNotifications.layoutManager = LinearLayoutManager(this)
        binding.rvNotifications.adapter = princetechlabs.deconest.ui.adpter.ProfileAdapter(list) {
            
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}
