package princetechlabs.deconest.ui.adpter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import princetechlabs.deconest.databinding.ItemAddressBinding
import princetechlabs.deconest.ui.data.AddressData

class AddressAdapter(
    private var list: MutableList<AddressData>,
    private val onDeleteClick: (AddressData, Int) -> Unit
) : RecyclerView.Adapter<AddressAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemAddressBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemAddressBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.binding.tvAddressName.text = item.name
        holder.binding.tvAddressPhone.text = item.phone
        holder.binding.tvFullAddress.text = item.fullAddress

        holder.binding.btnDeleteAddress.setOnClickListener {
            onDeleteClick(item, holder.adapterPosition)
        }
    }

    override fun getItemCount(): Int = list.size

    fun removeAddress(position: Int) {
        list.removeAt(position)
        notifyItemRemoved(position)
    }
    
    fun updateList(newList: MutableList<AddressData>) {
        list = newList
        notifyDataSetChanged()
    }
}
