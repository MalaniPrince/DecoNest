package princetechlabs.deconest.ui.adpter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import princetechlabs.deconest.R
import princetechlabs.deconest.ui.data.BannerData

class ImageSliderAdapter(
    private val context: Context,
    private val banners: List<BannerData>
) : PagerAdapter() {

    override fun getCount(): Int = banners.size

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view === `object`

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(context).inflate(R.layout.item_image_slider, container, false)
        val banner = banners[position]

        val ivImage    = view.findViewById<AppCompatImageView>(R.id.ImageSlide)
        val tvTitle    = view.findViewById<TextView>(R.id.tvBannerTitle)
        val tvSubtitle = view.findViewById<TextView>(R.id.tvBannerSubtitle)
        val tvBadge    = view.findViewById<TextView>(R.id.tvBannerBadge)

        Glide.with(context)
            .load(banner.imageUrl)
            .centerCrop()
            .placeholder(R.drawable.image1)
            .into(ivImage)

        tvTitle.text = banner.title
        tvSubtitle.text = banner.subtitle
        tvBadge.text = banner.badge

        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}
