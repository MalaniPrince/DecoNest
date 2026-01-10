package princetechlabs.deconest.ui.adpter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import princetechlabs.deconest.databinding.ItemProfileOptionBinding
import princetechlabs.deconest.ui.data.ProfileItem

class ProfileAdapter(
    private val list: List<ProfileItem>
) : RecyclerView.Adapter<ProfileAdapter.ProfileViewHolder>() {

    inner class ProfileViewHolder(
        val binding: ItemProfileOptionBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProfileViewHolder {

        val binding = ItemProfileOptionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProfileViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ProfileViewHolder,
        position: Int
    ) {
        val item = list[position]
        holder.binding.icon.setImageResource(item.icon)
        holder.binding.title.text = item.title
        holder.binding.subtitle.text = item.subtitle
    }

    override fun getItemCount(): Int = list.size
}
