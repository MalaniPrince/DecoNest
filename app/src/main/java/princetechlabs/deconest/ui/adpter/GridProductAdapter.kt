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
import princetechlabs.deconest.databinding.ItemProductGridBinding
import princetechlabs.deconest.ui.data.ProductData
import princetechlabs.deconest.ui.utils.CartRepository
import princetechlabs.deconest.ui.utils.WishlistRepository

class GridProductAdapter(
    private var productList: List<ProductData>,
    private val onProductClick: (ProductData) -> Unit
) : RecyclerView.Adapter<GridProductAdapter.GridViewHolder>() {

    inner class GridViewHolder(val binding: ItemProductGridBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridViewHolder {
        val binding = ItemProductGridBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return GridViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GridViewHolder, position: Int) {
        val product = productList[position]
        holder.binding.tvGridProductName.text = product.name
        holder.binding.tvGridProductCategory.text = product.category
        holder.binding.tvGridProductPrice.text = product.price

        // Real rating
        holder.binding.ratingBarGridProduct.rating = product.rating

        // MRP crossed out (25% more than selling price)
        val priceInt = product.price.filter { it.isDigit() }.toIntOrNull() ?: 0
        val mrpInt = (priceInt * 1.25).toInt()
        holder.binding.tvGridProductMrp.text = "₹${String.format("%,d", mrpInt)}"
        holder.binding.tvGridProductMrp.paintFlags =
            holder.binding.tvGridProductMrp.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

        // Discount badge
        holder.binding.tvGridDiscountBadge.text = "25% OFF"

        // Smart badge
        val badge = when {
            product.isTrending           -> "🔥 HOT"
            product.isNew                -> "NEW"
            product.category == "Luxury" -> "PREMIUM"
            product.rating >= 4.7f       -> "TOP RATED"
            else                         -> ""
        }
        if (badge.isNotEmpty()) {
            holder.binding.tvGridBadge.text = badge
            holder.binding.tvGridBadge.visibility = View.VISIBLE
        } else {
            holder.binding.tvGridBadge.visibility = View.GONE
        }

        Glide.with(holder.binding.root.context)
            .load(product.imageUrl)
            .centerCrop()
            .placeholder(R.drawable.image1)
            .error(R.drawable.image1)
            .into(holder.binding.ivGridProduct)

        // Card click
        holder.itemView.setOnClickListener { onProductClick(product) }

        // Wishlist state
        updateWishlistIcon(holder, product)

        holder.binding.btnGridWishlist.setOnClickListener {
            val anim = AnimationUtils.loadAnimation(it.context, R.anim.heart_pulse)
            holder.binding.btnGridWishlist.startAnimation(anim)
            WishlistRepository.toggle(product)
            updateWishlistIcon(holder, product)
            val msg = if (WishlistRepository.isWishlisted(product)) "Added to Wishlist ♥" else "Removed from Wishlist"
            Toast.makeText(holder.binding.root.context, msg, Toast.LENGTH_SHORT).show()
        }

        // Quick Add to Cart
        holder.binding.btnGridAddToCart.setOnClickListener {
            val anim = AnimationUtils.loadAnimation(it.context, R.anim.pop_in)
            holder.binding.btnGridAddToCart.startAnimation(anim)
            CartRepository.addProduct(product)
            Toast.makeText(holder.binding.root.context, "Added to cart!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateWishlistIcon(holder: GridViewHolder, product: ProductData) {
        val isWishlisted = WishlistRepository.isWishlisted(product)
        holder.binding.btnGridWishlist.setColorFilter(
            if (isWishlisted) android.graphics.Color.parseColor("#E67E22")
            else android.graphics.Color.WHITE
        )
        holder.binding.btnGridWishlist.alpha = if (isWishlisted) 1.0f else 0.80f
    }

    override fun getItemCount(): Int = productList.size

    fun updateList(newList: List<ProductData>) {
        productList = newList
        notifyDataSetChanged()
    }
}
