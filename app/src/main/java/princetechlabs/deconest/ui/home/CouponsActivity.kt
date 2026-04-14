package princetechlabs.deconest.ui.home

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import princetechlabs.deconest.R
import princetechlabs.deconest.databinding.ActivityCouponsBinding

data class CouponData(
    val code: String,
    val discountPercent: Int,
    val expiryDate: String,
    val description: String
)

class CouponsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCouponsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCouponsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)

        binding.btnCouponsBack.setOnClickListener { finish() }

        val coupons = listOf(
            CouponData("SAVE10", 10, "30 Apr 2026", "Get 10% off on all orders above ₹1,000"),
            CouponData("SAVE20", 20, "15 May 2026", "Get 20% off on furniture and living room items"),
            CouponData("FIRST50", 50, "31 May 2026", "50% off on your first order — new users only!"),
            CouponData("FESTIVE30", 30, "25 May 2026", "Festive season special — 30% off sitewide")
        )

        binding.rvCoupons.layoutManager = LinearLayoutManager(this)
        binding.rvCoupons.adapter = CouponAdapter(coupons) { code ->
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            clipboard.setPrimaryClip(ClipData.newPlainText("coupon", code))
            Toast.makeText(this, "Code \"$code\" copied!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}
