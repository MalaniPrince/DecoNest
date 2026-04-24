package princetechlabs.deconest.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import princetechlabs.deconest.R
import princetechlabs.deconest.databinding.ItemProductGridBinding
import princetechlabs.deconest.ui.data.Product

class CategoryProductsAdapter(
    private val onProductClick: (Product) -> Unit
) : ListAdapter<Product, CategoryProductsAdapter.ViewHolder>(ProductDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemProductGridBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemProductGridBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.tvGridProductName.text = product.name
            binding.tvGridProductPrice.text = "₹ ${String.format("%,.2f", product.price)}"
            binding.ratingBarGridProduct.rating = product.rating

            Glide.with(binding.root.context)
                .load(product.imageUrl)
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.error_image)
                .into(binding.ivGridProduct)

            binding.root.setOnClickListener {
                onProductClick(product)
            }
        }
    }

    class ProductDiffCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }
}
