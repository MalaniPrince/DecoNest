package princetechlabs.deconest.ui.adpter

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import princetechlabs.deconest.R
import princetechlabs.deconest.databinding.ItemCategoryTabBinding

class CategoryTabAdapter(
    private val tabs: List<String>,
    private var selectedIndex: Int = 0,
    private val onTabClick: (Int) -> Unit
) : RecyclerView.Adapter<CategoryTabAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemCategoryTabBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCategoryTabBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context = holder.binding.root.context
        holder.binding.tvCategoryTab.text = tabs[position]

        if (position == selectedIndex) {
            holder.binding.tabIndicator.visibility = View.VISIBLE
            holder.binding.tvCategoryTab.setTextColor(
                ContextCompat.getColor(context, R.color.color_tab_active)
            )
            holder.binding.tvCategoryTab.setTypeface(null, Typeface.BOLD)
        } else {
            holder.binding.tabIndicator.visibility = View.GONE
            holder.binding.tvCategoryTab.setTextColor(
                ContextCompat.getColor(context, R.color.color_gray_text)
            )
            holder.binding.tvCategoryTab.setTypeface(null, Typeface.NORMAL)
        }

        // Click listener on root layout - reliable click handling
        holder.binding.root.setOnClickListener {
            val pos = holder.adapterPosition
            if (pos == RecyclerView.NO_POSITION) return@setOnClickListener
            val oldIndex = selectedIndex
            selectedIndex = pos
            notifyItemChanged(oldIndex)
            notifyItemChanged(pos)
            onTabClick(pos)
        }
    }

    override fun getItemCount(): Int = tabs.size
}
