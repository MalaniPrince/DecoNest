package princetechlabs.deconest.ui.adpter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import princetechlabs.deconest.R
import princetechlabs.deconest.databinding.ItemProductCardBinding
import princetechlabs.deconest.ui.data.ProductData
import princetechlabs.deconest.ui.utils.CartRepository
import princetechlabs.deconest.ui.utils.WishlistRepository

class ProductAdapter(
    private var productList: List<ProductData>,
    private val onItemClick: (ProductData) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(val binding: ItemProductCardBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductCardBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]
        holder.binding.tvProductName.text = product.name
        holder.binding.tvProductCategory.text = product.category
        holder.binding.tvProductPrice.text = product.price

        // Real rating from product data
        holder.binding.ratingBarProduct.rating = product.rating

        // MRP crossed out (25% more than selling price)
        val priceInt = product.price.filter { it.isDigit() }.toIntOrNull() ?: 0
        val mrpInt = (priceInt * 1.25).toInt()
        holder.binding.tvProductMrp.text = "₹${String.format("%,d", mrpInt)}"
        holder.binding.tvProductMrp.paintFlags =
            holder.binding.tvProductMrp.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

        // Discount badge
        holder.binding.tvDiscountBadge.text = "25% OFF"

        // Smart badge: real data-driven
        val badge = when {
            product.isTrending              -> "🔥 HOT"
            product.isNew                   -> "NEW"
            product.category == "Luxury"    -> "PREMIUM"
            product.rating >= 4.7f          -> "TOP RATED"
            else                            -> ""
        }
        if (badge.isNotEmpty()) {
            holder.binding.tvProductBadge.text = badge
            holder.binding.tvProductBadge.visibility = View.VISIBLE
        } else {
            holder.binding.tvProductBadge.visibility = View.GONE
        }

        Glide.with(holder.binding.root.context)
            .load(product.imageUrl)
            .centerCrop()
            .placeholder(R.drawable.image1)
            .error(R.drawable.image1)
            .into(holder.binding.ivProduct)

        // Card click
        holder.binding.root.setOnClickListener {
            val anim = AnimationUtils.loadAnimation(it.context, R.anim.scale_up)
            holder.binding.root.startAnimation(anim)
            onItemClick(product)
        }

        // Wishlist state
        updateWishlistIcon(holder, product)

        holder.binding.btnCardWishlist.setOnClickListener {
            val anim = AnimationUtils.loadAnimation(it.context, R.anim.heart_pulse)
            holder.binding.btnCardWishlist.startAnimation(anim)
            WishlistRepository.toggle(product)
            updateWishlistIcon(holder, product)
            val msg = if (WishlistRepository.isWishlisted(product)) "Added to Wishlist ♥" else "Removed from Wishlist"
            Toast.makeText(holder.binding.root.context, msg, Toast.LENGTH_SHORT).show()
        }

        // Quick Add to Cart
        holder.binding.btnCardAddToCart.setOnClickListener {
            val anim = AnimationUtils.loadAnimation(it.context, R.anim.pop_in)
            holder.binding.btnCardAddToCart.startAnimation(anim)
            CartRepository.addProduct(product)
            Toast.makeText(holder.binding.root.context, "Added to cart!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateWishlistIcon(holder: ProductViewHolder, product: ProductData) {
        val isWishlisted = WishlistRepository.isWishlisted(product)
        holder.binding.btnCardWishlist.setColorFilter(
            if (isWishlisted) android.graphics.Color.parseColor("#E67E22")
            else android.graphics.Color.WHITE
        )
        holder.binding.btnCardWishlist.alpha = if (isWishlisted) 1.0f else 0.80f
    }

    override fun getItemCount(): Int = productList.size

    fun updateList(newList: List<ProductData>) {
        productList = newList
        notifyDataSetChanged()
    }
}
