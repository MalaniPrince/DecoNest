package princetechlabs.deconest.ui.adpter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import princetechlabs.deconest.databinding.ItemCategoryGridItemBinding
import princetechlabs.deconest.ui.data.HomeCategoryItem

class CategoryGridHomeAdapter(
    private val items: List<HomeCategoryItem>
) : RecyclerView.Adapter<CategoryGridHomeAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemCategoryGridItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCategoryGridItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.binding.tvCategoryItemName.text = item.name
        holder.binding.ivCategoryItem.setImageResource(item.imageRes)
    }

    override fun getItemCount(): Int = items.size
}
