package princetechlabs.deconest.ui.home

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import princetechlabs.deconest.R
import princetechlabs.deconest.databinding.ActivityProductDetailBinding
import princetechlabs.deconest.ui.adpter.ReviewAdapter
import princetechlabs.deconest.ui.data.ProductData
import princetechlabs.deconest.ui.data.ReviewData
import princetechlabs.deconest.ui.utils.CartRepository
import princetechlabs.deconest.ui.utils.RecentlyViewedRepository
import princetechlabs.deconest.ui.utils.WishlistRepository

class ProductDetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_NAME         = "extra_name"
        const val EXTRA_IMAGE        = "extra_image"
        const val EXTRA_CATEGORY     = "extra_category"
        const val EXTRA_PRICE        = "extra_price"
        const val EXTRA_RATING       = "extra_rating"
        const val EXTRA_REVIEW_COUNT = "extra_review_count"
    }

    private lateinit var binding: ActivityProductDetailBinding
    private var quantity = 1
    private lateinit var product: ProductData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)

        val name        = intent.getStringExtra(EXTRA_NAME) ?: ""
        val imageUrl    = intent.getStringExtra(EXTRA_IMAGE) ?: ""
        val category    = intent.getStringExtra(EXTRA_CATEGORY) ?: ""
        val price       = intent.getStringExtra(EXTRA_PRICE) ?: ""
        val rating      = intent.getFloatExtra(EXTRA_RATING, 4.2f)
        val reviewCount = intent.getIntExtra(EXTRA_REVIEW_COUNT, 128)

        product = ProductData(name, imageUrl, category, price, rating, reviewCount)
        RecentlyViewedRepository.add(product)

        binding.tvDetailName.text = name
        binding.tvDetailCategory.text = category
        binding.tvDetailPrice.text = price
        binding.tvDetailDescription.text = getDescription(category)

        // Real rating display
        binding.ratingBarDetail.rating = rating
        binding.tvDetailRatingCount.text = "$rating ★ ($reviewCount reviews)"

        // MRP crossed out with discount + savings
        val priceInt = price.filter { it.isDigit() }.toIntOrNull() ?: 0
        if (priceInt > 0) {
            val mrpInt = (priceInt * 1.25).toInt()
            val saving = mrpInt - priceInt
            binding.tvDetailMrp.text = "M.R.P: ₹${String.format("%,d", mrpInt)}"
            binding.tvDetailMrp.paintFlags = binding.tvDetailMrp.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            binding.tvDetailMrp.visibility = View.VISIBLE
            binding.tvDetailDiscount.text = "25% off"
            binding.tvDetailDiscount.visibility = View.VISIBLE
            binding.tvDetailSaving.text = "You save ₹${String.format("%,d", saving)}"
            binding.tvDetailSaving.visibility = View.VISIBLE
        }

        Glide.with(this)
            .load(imageUrl)
            .centerCrop()
            .placeholder(R.drawable.image1)
            .error(R.drawable.image1)
            .into(binding.ivProductDetailImage)

        updateWishlistIcon()

        binding.btnBack.setOnClickListener { finish() }

        binding.btnWishlist.setOnClickListener {
            WishlistRepository.toggle(product)
            updateWishlistIcon()
            val anim = AnimationUtils.loadAnimation(this, R.anim.heart_pulse)
            binding.btnWishlist.startAnimation(anim)
            val msg = if (WishlistRepository.isWishlisted(product))
                getString(R.string.toast_added_to_wishlist)
            else getString(R.string.toast_removed_from_wishlist)
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }

        binding.btnDetailIncrease.setOnClickListener {
            if (quantity < 10) {
                quantity++
                binding.tvDetailQty.text = quantity.toString()
            }
        }
        binding.btnDetailDecrease.setOnClickListener {
            if (quantity > 1) {
                quantity--
                binding.tvDetailQty.text = quantity.toString()
            }
        }

        updateCartButtonState()

        binding.btnAddToCart.setOnClickListener {
            repeat(quantity) { CartRepository.addProduct(product) }
            updateCartButtonState()
            val anim = AnimationUtils.loadAnimation(this, R.anim.pop_in)
            binding.btnAddToCart.startAnimation(anim)
            Toast.makeText(this, getString(R.string.toast_added_to_cart), Toast.LENGTH_SHORT).show()
        }

        binding.btnBuyNow.setOnClickListener {
            repeat(quantity) { CartRepository.addProduct(product) }
            startActivity(Intent(this, CheckoutActivity::class.java))
        }

        binding.btnShare.setOnClickListener {
            ShareCompat.IntentBuilder(this)
                .setType("text/plain")
                .setText("Check out ${product.name} on DecoNest!\nPrice: ${product.price}\nCategory: ${product.category}\nRating: $rating ⭐")
                .setChooserTitle("Share via")
                .startChooser()
        }

        setupReviews(rating, reviewCount)
    }

    private fun updateCartButtonState() {
        val inCart  = CartRepository.items.any { it.name == product.name }
        val cartQty = CartRepository.items.find { it.name == product.name }?.quantity ?: 0
        if (inCart) {
            binding.btnAddToCart.text = "In Cart ($cartQty) ✓"
            binding.btnAddToCart.setBackgroundResource(R.drawable.bg_gradient_button)
        } else {
            binding.btnAddToCart.text = getString(R.string.btn_add_to_cart)
            binding.btnAddToCart.setBackgroundResource(R.drawable.bg_gradient_accent)
        }
    }

    private fun updateWishlistIcon() {
        val wishlisted = WishlistRepository.isWishlisted(product)
        binding.btnWishlist.alpha = if (wishlisted) 1.0f else 0.6f
        binding.btnWishlist.setColorFilter(
            if (wishlisted) android.graphics.Color.parseColor("#E67E22")
            else android.graphics.Color.WHITE
        )
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    private fun getDescription(category: String): String = when (category) {
        "Furniture"   -> "Premium quality furniture crafted with solid wood and fine fabric. Designed for lasting comfort and elegant aesthetics that complement any home style.\n\n• Material: Solid Sheesham Wood\n• Assembly: Easy, tool-free setup\n• Warranty: 2 Years"
        "Luxury"      -> "Exquisite luxury piece with the finest materials. A statement of sophistication and refined taste, crafted for those who appreciate true elegance.\n\n• Handcrafted finish\n• Premium upholstery\n• Warranty: 3 Years"
        "Living Room" -> "Transform your living space with this stunning piece. A perfect blend of style and functionality that makes every moment at home more enjoyable.\n\n• Space-saving design\n• Easy to clean surface\n• Warranty: 1 Year"
        "Bed Room"    -> "Create your perfect bedroom retreat. Premium materials and thoughtful design ensure maximum comfort and durability for years to come.\n\n• Anti-scratch surface\n• Moisture resistant\n• Warranty: 2 Years"
        "Mattresses"  -> "Experience superior sleep comfort every night. Advanced foam technology provides optimal support and pressure relief for deep, restful sleep.\n\n• Orthopedic support\n• Anti-microbial fabric\n• Trial: 100-night free trial"
        "Kitchen"     -> "High-grade kitchen accessory built to last. Food-safe materials and ergonomic design make cooking a pleasure every day.\n\n• Food-safe certified\n• Dishwasher safe\n• BPA Free"
        else          -> "High quality product designed to enhance your home decor. Durable, stylish and functional for modern living.\n\n• Premium build quality\n• Easy maintenance\n• Warranty: 1 Year"
    }

    private fun setupReviews(rating: Float, reviewCount: Int) {
        val reviews = mutableListOf(
            ReviewData("Priya S.", "Absolutely love it! Exactly as described. Very happy with the purchase.", 5.0f),
            ReviewData("Rahul M.", "Good quality. Assembly was easy. Looks great in my living room.", 4.0f),
            ReviewData("Sneha K.", "Excellent product for the price. Highly recommended!", 5.0f),
            ReviewData("Amit V.", "Nice product, delivery was fast. Quality is good.", 4.0f)
        )

        val reviewAdapter = ReviewAdapter(reviews)
        binding.rvReviews.layoutManager = LinearLayoutManager(this)
        binding.rvReviews.adapter = reviewAdapter
        binding.rvReviews.isNestedScrollingEnabled = false

        binding.btnSubmitReview.setOnClickListener {
            val text = binding.etReviewText.text.toString().trim()
            if (text.isNotEmpty()) {
                reviewAdapter.addReview(ReviewData("You", text, 5.0f))
                binding.etReviewText.text.clear()
                Toast.makeText(this, "Review posted! Thank you ✓", Toast.LENGTH_SHORT).show()
                binding.rvReviews.smoothScrollToPosition(0)
            } else {
                Toast.makeText(this, "Please write a review first", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
