package princetechlabs.deconest.ui.home

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import princetechlabs.deconest.R
import princetechlabs.deconest.databinding.ActivityCheckoutBinding
import princetechlabs.deconest.ui.utils.CartRepository
import princetechlabs.deconest.ui.utils.OrderRepository

class CheckoutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCheckoutBinding
    private var discountPercent = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)

        setupSummary()
        setupPromoCode()

        binding.btnCheckoutBack.setOnClickListener { finish() }

        binding.btnPlaceOrder.setOnClickListener {
            validateAndProceed()
        }
    }

    private fun validateAndProceed() {
        val name = binding.etFullName.text.toString().trim()
        val phone = binding.etPhone.text.toString().trim()
        val address = binding.etAddress.text.toString().trim()
        val city = binding.etCity.text.toString().trim()
        val pincode = binding.etPincode.text.toString().trim()

        if (name.isEmpty() || phone.isEmpty() || address.isEmpty() || city.isEmpty() || pincode.isEmpty()) {
            Toast.makeText(this, getString(R.string.toast_fill_all_fields), Toast.LENGTH_SHORT).show()
            return
        }

        if (phone.length < 10) {
            Toast.makeText(this, getString(R.string.toast_invalid_phone), Toast.LENGTH_SHORT).show()
            return
        }

        if (pincode.length != 6) {
            Toast.makeText(this, getString(R.string.toast_invalid_pincode), Toast.LENGTH_SHORT).show()
            return
        }

        val fullAddress = "$address, $city - $pincode"
        // Capture total BEFORE placeOrder clears the cart
        val subtotal = CartRepository.getTotalPrice()
        val total = (subtotal * (1.0 - discountPercent)).toInt()
        val orderId = OrderRepository.placeOrder(fullAddress, total)

        startActivity(Intent(this, OrderSuccessActivity::class.java).apply {
            putExtra("order_id", orderId)
            putExtra("order_amount", total)
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        })
        finish()
    }

    private fun setupSummary() {
        val subtotal = CartRepository.getTotalPrice()
        val discount = (subtotal * discountPercent).toInt()
        val total = subtotal - discount
        binding.tvCheckoutSubtotal.text = "₹${String.format("%,d", subtotal)}"
        binding.tvCheckoutDiscount.text = "-₹${String.format("%,d", discount)}"
        binding.tvCheckoutTotal.text = "₹${String.format("%,d", total)}"
    }

    private fun setupPromoCode() {
        binding.btnApplyPromo.setOnClickListener {
            val code = binding.etPromoCode.text.toString().trim().uppercase()
            val discount = when (code) {
                "SAVE10" -> 0.10
                "SAVE20" -> 0.20
                "FIRST50" -> 0.50
                "FESTIVE30" -> 0.30
                else -> -1.0
            }
            if (discount >= 0) {
                discountPercent = discount
                setupSummary()
                binding.btnApplyPromo.text = "Applied"
                binding.btnApplyPromo.isEnabled = false
                Toast.makeText(this, "Promo code applied! ${(discount * 100).toInt()}% off", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Invalid promo code", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}
