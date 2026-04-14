package princetechlabs.deconest.ui.adpter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import princetechlabs.deconest.databinding.ItemReviewBinding
import princetechlabs.deconest.ui.data.ReviewData

class ReviewAdapter(private val list: MutableList<ReviewData>) : RecyclerView.Adapter<ReviewAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemReviewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.binding.tvUserName.text = item.userName
        holder.binding.tvReviewText.text = item.reviewText
        holder.binding.rbRating.rating = item.rating
    }

    override fun getItemCount(): Int = list.size

    fun addReview(review: ReviewData) {
        list.add(0, review)
        notifyItemInserted(0)
    }
}
