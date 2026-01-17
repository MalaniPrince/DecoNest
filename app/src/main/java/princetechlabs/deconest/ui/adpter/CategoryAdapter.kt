package princetechlabs.deconest.ui.adpter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import princetechlabs.deconest.data.CategoryData
import princetechlabs.deconest.databinding.ItemCategoryBinding

class CategoryAdapter(var list: List<CategoryData>) :
    RecyclerView.Adapter<CategoryAdapter.CategoryAdapterViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAdapterViewHolder {
        var binding =
            ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryAdapterViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CategoryAdapterViewHolder,
        position: Int
    ) {
        holder.binding.Title.text = list[position].title
        holder.binding.desc.text = list[position].desc
        holder.binding.image.setImageResource(list[position].image)

        holder.binding.TwoRecycler.layoutManager =
            LinearLayoutManager(holder.itemView.context)
        holder.binding.TwoRecycler.adapter =
            CategoryAdapterTwo(list[position].tworecyclelist)

        holder.binding.TwoRecycler.visibility =
            if (list[position].expnad) {
                View.VISIBLE
            } else{
                View.GONE
            }

        holder.binding.imgArrow.rotation =
            if ( list[position].expnad){
                180f
            } else{
                0f
            }

        holder.itemView.setOnClickListener {
            list[position].expnad = !list[position].expnad
            notifyItemChanged(position)
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }


    inner class CategoryAdapterViewHolder(var binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }
}