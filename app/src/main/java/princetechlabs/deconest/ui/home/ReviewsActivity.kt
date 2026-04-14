package princetechlabs.deconest.ui.home

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import princetechlabs.deconest.R
import princetechlabs.deconest.databinding.ActivityReviewsBinding
import princetechlabs.deconest.ui.adpter.ReviewAdapter
import princetechlabs.deconest.ui.data.ReviewData

class ReviewsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReviewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReviewsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)

        binding.btnReviewsBack.setOnClickListener { finish() }

        val sampleReviews = mutableListOf(
            ReviewData("Priyanshu", "The sofa looks exactly like in the pictures. Great quality and fast delivery!", 4.5f),
            ReviewData("Priyanshu", "Loved the Moroccan lamp. Adds a great vibe to my room. Highly recommend!", 5.0f),
            ReviewData("Priyanshu", "Good product but packaging could be better. Slight dent on arrival.", 3.5f)
        )

        if (sampleReviews.isEmpty()) {
            binding.layoutEmptyReviews.visibility = View.VISIBLE
            binding.rvReviews.visibility = View.GONE
        } else {
            binding.layoutEmptyReviews.visibility = View.GONE
            binding.rvReviews.visibility = View.VISIBLE
            binding.rvReviews.layoutManager = LinearLayoutManager(this)
            binding.rvReviews.adapter = ReviewAdapter(sampleReviews)
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}
