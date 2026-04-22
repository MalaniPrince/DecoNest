package princetechlabs.deconest.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import princetechlabs.deconest.R
import princetechlabs.deconest.databinding.ActivityInfoBinding

class InfoActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_TYPE = "info_type"
        const val TYPE_PRIVACY = "privacy"
        const val TYPE_HELP = "help"
        const val TYPE_ABOUT = "about"
    }

    private lateinit var binding: ActivityInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)

        binding.btnInfoBack.setOnClickListener { finish() }

        when (intent.getStringExtra(EXTRA_TYPE)) {
            TYPE_PRIVACY -> {
                binding.tvInfoTitle.text = "Privacy Policy"
                binding.tvInfoContent.text = buildPrivacyPolicy()
            }
            TYPE_HELP -> {
                binding.tvInfoTitle.text = "Help Center"
                binding.tvInfoContent.text = buildHelpCenter()
            }
            TYPE_ABOUT -> {
                binding.tvInfoTitle.text = "About DecoNest"
                binding.tvInfoContent.text = buildAbout()
            }
        }
    }

    private fun buildPrivacyPolicy(): String = """
Privacy Policy — DecoNest

Last updated: April 2026

1. Information We Collect
We collect information you provide directly, such as your name, email address, phone number, and delivery addresses when you create an account or place an order.

2. How We Use Your Information
• To process your orders and deliver products.
• To send order confirmations and delivery updates.
• To personalise your shopping experience.
• To improve our app and services.

3. Data Sharing
We do not sell, trade, or rent your personal information to third parties. We may share data with trusted service providers (payment gateways, delivery partners) solely to fulfil your orders.

4. Data Security
We implement industry-standard security measures to protect your personal data. However, no method of transmission over the internet is 100% secure.

5. Cookies
Our app may use cookies and similar tracking technologies to enhance your experience and analyse usage patterns.

6. Your Rights
You may access, update, or delete your personal data by contacting our support team. You may also opt out of promotional communications at any time.

7. Contact Us
For privacy-related inquiries, please contact:
support@deconest.in

© 2026 DecoNest. All rights reserved.
    """.trimIndent()

    private fun buildHelpCenter(): String = """
Help Center — DecoNest

Frequently Asked Questions

HOW TO PLACE AN ORDER
Browse products, tap "Add to Cart" or "Buy Now", review your cart, fill in delivery details, and confirm your order. You'll receive an order confirmation immediately.

HOW TO TRACK MY ORDER
Go to My Orders from the Account tab or Drawer menu. Tap on any order to see its current tracking status.

RETURN & REFUND POLICY
We accept returns within 7 days of delivery for damaged or defective items. Contact us with your order ID and photos of the issue.

PAYMENT METHODS
Currently we support Cash on Delivery (COD). More payment options (UPI, Cards, Wallets) coming soon.

HOW TO CANCEL AN ORDER
You can cancel an order within 2 hours of placing it. Go to My Orders, select the order, and tap Cancel.

DELIVERY TIME
Standard delivery takes 5–7 business days. Express delivery options coming soon.

CONTACT SUPPORT
• Email: support@deconest.in
• Phone: +91 81404 10677
• Hours: Mon–Sat, 10 AM – 6 PM

We're here to help make your home beautiful!
    """.trimIndent()

    private fun buildAbout(): String = """
About DecoNest

Your Premium Home Decor Destination

DecoNest is a curated home decor shopping platform designed to help you create your dream living space. From luxurious sofas to handcrafted lamps, statement rugs to modern furniture — we bring the finest home decor right to your doorstep.

OUR MISSION
To make premium home decor accessible and inspirational for every home, every style, every budget.

WHAT WE OFFER
• Furniture — Sofas, beds, dining sets, storage
• Lighting — Lamps, pendant lights, fairy lights
• Decor — Vases, wall art, mirrors, cushions
• Kitchen — Utensils, cookware, organizers
• Outdoor — Garden furniture, planters

WHY DECONEST
• Curated collection from trusted designers
• Quality verified products
• Secure & easy shopping
• Fast nationwide delivery
• Easy returns & exchanges

VERSION
DecoNest v1.0.0

DEVELOPER
PrinceTechLabs

© 2026 DecoNest. All rights reserved.
Made with love for beautiful homes.
    """.trimIndent()

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}
