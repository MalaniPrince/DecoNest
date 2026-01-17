package princetechlabs.deconest.ui.adpter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import princetechlabs.deconest.data.CategoryTwo
import princetechlabs.deconest.databinding.ItemCategoryTwoBinding

class CategoryAdapterTwo(var list: List<CategoryTwo>) : RecyclerView.Adapter<CategoryAdapterTwo.CategoryAdapterViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryAdapterViewHolder {

        var binding = ItemCategoryTwoBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CategoryAdapterViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CategoryAdapterViewHolder,
        position: Int
    ) {
        val item = list[position]
        holder.binding.titletwo.text = item.twotitle
        holder.binding.plussigned.isVisible = item.moreimage
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class CategoryAdapterViewHolder(var binding: ItemCategoryTwoBinding) : RecyclerView.ViewHolder(binding.root){

    }

}