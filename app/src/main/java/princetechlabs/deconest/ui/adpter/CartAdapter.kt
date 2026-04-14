package princetechlabs.deconest.ui.adpter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import princetechlabs.deconest.R
import princetechlabs.deconest.databinding.ItemCartProductBinding
import princetechlabs.deconest.ui.data.CartItem
import princetechlabs.deconest.ui.utils.CartRepository

class CartAdapter(
    private val cartItems: MutableList<CartItem>,
    private val onCartChanged: () -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    inner class CartViewHolder(val binding: ItemCartProductBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemCartProductBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = cartItems[position]
        holder.binding.tvCartProductName.text = item.name
        holder.binding.tvCartProductCategory.text = item.category
        holder.binding.tvQuantity.text = item.quantity.toString()
        holder.binding.tvCartProductPrice.text = "₹${String.format("%,d", item.price * item.quantity)}"

        Glide.with(holder.binding.root.context)
            .load(item.imageUrl)
            .centerCrop()
            .placeholder(R.drawable.image1)
            .into(holder.binding.ivCartProduct)

        holder.binding.btnIncrease.setOnClickListener {
            val anim = AnimationUtils.loadAnimation(it.context, R.anim.pop_in)
            holder.binding.tvQuantity.startAnimation(anim)
            item.quantity++
            holder.binding.tvQuantity.text = item.quantity.toString()
            holder.binding.tvCartProductPrice.text = "₹${String.format("%,d", item.price * item.quantity)}"
            CartRepository.notifyListeners()
            onCartChanged()
        }

        holder.binding.btnDecrease.setOnClickListener {
            if (item.quantity > 1) {
                val anim = AnimationUtils.loadAnimation(it.context, R.anim.pop_in)
                holder.binding.tvQuantity.startAnimation(anim)
                item.quantity--
                holder.binding.tvQuantity.text = item.quantity.toString()
                holder.binding.tvCartProductPrice.text = "₹${String.format("%,d", item.price * item.quantity)}"
                CartRepository.notifyListeners()
                onCartChanged()
            }
        }

        holder.binding.btnRemoveCart.setOnClickListener {
            val anim = AnimationUtils.loadAnimation(it.context, R.anim.slide_out_right)
            holder.binding.root.startAnimation(anim)
            holder.binding.root.postDelayed({
                val pos = holder.adapterPosition
                if (pos != RecyclerView.NO_POSITION && pos < cartItems.size) {
                    cartItems.removeAt(pos)
                    notifyItemRemoved(pos)
                    notifyItemRangeChanged(pos, cartItems.size)
                    CartRepository.notifyListeners()
                    onCartChanged()
                }
            }, 200)
        }
    }

    override fun getItemCount(): Int = cartItems.size

    fun getTotalPrice(): Int = cartItems.sumOf { it.price * it.quantity }
}
