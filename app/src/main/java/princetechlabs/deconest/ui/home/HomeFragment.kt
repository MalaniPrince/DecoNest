    package princetechlabs.deconest.ui.home

    import android.os.Bundle
    import android.os.Handler
    import android.os.Looper
    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import androidx.fragment.app.Fragment
    import androidx.recyclerview.widget.LinearLayoutManager
    import androidx.viewpager2.widget.ViewPager2
    import princetechlabs.deconest.databinding.FragmentFirstBinding
    import princetechlabs.deconest.ui.adpter.ImageSliderAdapter
    import princetechlabs.deconest.ui.adpter.ItemAdapter
    import princetechlabs.deconest.ui.data.ModelClass
    import princetechlabs.deconest.ui.utils.MasterDataUtils

    class HomeFragment : Fragment() {

        private var _binding: FragmentFirstBinding? = null
        private val binding get() = _binding!!

        private lateinit var viewPager: ViewPager2
        private lateinit var dataList1: ArrayList<String>
        private lateinit var itemAdapter: ItemAdapter
        private val handler = Handler(Looper.getMainLooper())
        private val delay: Long = 3000
        private var currentPage = 0

        private val runnable = object : Runnable {
            override fun run() {
                if (dataList1.isNotEmpty()) {
                    currentPage = (currentPage + 1) % dataList1.size
                    viewPager.setCurrentItem(currentPage, true)
                    handler.postDelayed(this, delay)
                }
            }
        }


        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?,
        ): View {
            val dataList: ArrayList<ModelClass> = MasterDataUtils.MasterDataList(requireContext())
            dataList1 = MasterDataUtils.viewPagerImage(requireContext())
            _binding = FragmentFirstBinding.inflate(inflater, container, false)
            itemAdapter = ItemAdapter(requireContext(), dataList, onClickListener = {})
            binding.recyclercontent.layoutManager = LinearLayoutManager(requireContext())
           binding.recyclercontent.adapter = itemAdapter
            //binding.ViewPager.adapter = ImageSliderAdapter(requireContext(),dataList1)
            //val imageList = MasterDataUtils.viewPagerImage(requireContext())
            binding.ViewPager.adapter = ImageSliderAdapter(requireContext(),dataList1)
            handler.postDelayed(runnable, delay)
            return binding.root



        }

        override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
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
