package princetechlabs.deconest.ui.adpter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import princetechlabs.deconest.R
import princetechlabs.deconest.databinding.ItemProductCardBinding
import princetechlabs.deconest.ui.data.ProductData

class ProductAdapter(
    private var productList: List<ProductData>
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

        Glide.with(holder.binding.root.context)
            .load(product.imageUrl)
            .centerCrop()
            .placeholder(R.drawable.image1)
            .into(holder.binding.ivProduct)
    }

    override fun getItemCount(): Int = productList.size

    fun updateList(newList: List<ProductData>) {
        productList = newList
        notifyDataSetChanged()
    }
}
