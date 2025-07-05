package princetechlabs.deconest.ui.adpter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import princetechlabs.deconest.R

class ImageSliderAdapter(
    private val context: Context,
    private var imageList: ArrayList<String>,
) : PagerAdapter() {

    override fun getCount(): Int = imageList.size

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view === `object`

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val layoutId =
            R.layout.item_image_slider

        val view = LayoutInflater.from(context).inflate(layoutId, container, false)
        val ivImage = view.findViewById<ImageView>(R.id.ImageSlide)

        Glide.with(context)
            .load(imageList[position])
            .placeholder(R.drawable.image_warning)
            .into(ivImage)

        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}