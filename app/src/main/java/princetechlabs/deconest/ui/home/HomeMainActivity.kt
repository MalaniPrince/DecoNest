package princetechlabs.deconest.ui.home


    import android.os.Bundle
    import android.os.Handler
    import android.os.Looper
    import androidx.appcompat.app.AppCompatActivity
    import androidx.viewpager2.widget.ViewPager2
    import princetechlabs.deconest.R
    import princetechlabs.deconest.ui.adpter.ImageSliderAdapter
    // ✅ make sure it's adapter, not adpter
    import princetechlabs.deconest.ui.utils.MasterDataUtils

    class HomeMainActivity : AppCompatActivity() {

        private lateinit var viewPager: ViewPager2
        private lateinit var imageList: List<String> // URLs
        private var currentPage = 0
        private val handler = Handler(Looper.getMainLooper())
        private val delay: Long = 3000 // 3 seconds

        private val runnable = object : Runnable {
            override fun run() {
                if (imageList.isNotEmpty()) {
                    currentPage = (currentPage + 1) % imageList.size
                    viewPager.setCurrentItem(currentPage, true)
                    handler.postDelayed(this, delay)
                }
            }
        }

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.fragment_first)

            val imageList = MasterDataUtils.viewPagerImage(this)

            viewPager = findViewById(R.id.ViewPager)
            viewPager.adapter = ImageSliderAdapter(imageList) // ✅ Make sure this accepts List<String>

            handler.postDelayed(runnable, delay)
        }

        override fun onPause() {
            super.onPause()
            handler.removeCallbacks(runnable)
        }

        override fun onResume() {
            super.onResume()
            handler.postDelayed(runnable, delay)
        }
    }