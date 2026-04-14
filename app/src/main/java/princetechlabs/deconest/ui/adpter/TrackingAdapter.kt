package princetechlabs.deconest.ui.adpter

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import princetechlabs.deconest.databinding.ItemTrackingNodeBinding
import princetechlabs.deconest.ui.data.TrackingNodeData

class TrackingAdapter(private val nodes: List<TrackingNodeData>) : RecyclerView.Adapter<TrackingAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemTrackingNodeBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTrackingNodeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val node = nodes[position]
        holder.binding.tvNodeTitle.text = node.title
        holder.binding.tvNodeDesc.text = node.description
        
        if (node.isCompleted) {
            holder.binding.ivNodeStatus.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#4CAF50"))
        } else {
            holder.binding.ivNodeStatus.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#6B6B8A"))
        }

        if (node.isLast) {
            holder.binding.vTimeline.visibility = View.GONE
        } else {
            holder.binding.vTimeline.visibility = View.VISIBLE
        }
    }

    override fun getItemCount(): Int = nodes.size
}
