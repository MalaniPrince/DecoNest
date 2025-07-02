package princeinfotech.deconest.ui.adpter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import princeinfotech.deconest.R
import princeinfotech.deconest.databinding.ItemFileBinding
import princeinfotech.deconest.ui.data.ModelClass


class ItemAdapter(
    val context: Context,
    private final var itemList: ArrayList<ModelClass>,
    private final val onClickListener: View.OnClickListener
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemFileBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(itemList[position], onClickListener)
    }

    override fun getItemCount(): Int = itemList.size

    class OnClickListener(val clickListener: (itemData: ModelClass, clickType: Int) -> Unit) {
        fun onClick(itemData: ModelClass, clickType: Int) = clickListener(itemData, clickType)
    }

    inner class ItemViewHolder(private val binding: ItemFileBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(dataModal: ModelClass, onClickListener: View.OnClickListener) {
            binding.textItemName.text = dataModal.name

            Glide.with(context)
                .load(dataModal.image)
                .centerCrop()
                .placeholder(R.drawable.image1)
                .into(binding.imageItem)

        }
    }
}