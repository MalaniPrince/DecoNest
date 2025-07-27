package princetechlabs.deconest.ui.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import princetechlabs.deconest.ui.adpter.ImageSliderAdapter
import princetechlabs.deconest.ui.adpter.ItemAdapter
import princetechlabs.deconest.ui.data.ModelClass
import princetechlabs.deconest.ui.utils.MasterDataUtils


class HomeFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    private lateinit var itemAdapter: ItemAdapter
    private lateinit var imageSliderAdapter: ImageSliderAdapter
    private lateinit var itemList: ArrayList<ModelClass>

    private lateinit var viewPager: ViewPager

    private var currentPage = 0
    private val handler = Handler(Looper.getMainLooper())
    private val delay: Long = 3000 // 3 seconds
    private val runnable: Runnable = object : Runnable {
        override fun run() {
            val totalItems = imageSliderAdapter.count
            currentPage = (currentPage + 1) % totalItems
            binding.viewPager.setCurrentItem(currentPage, true)
            handler.postDelayed(this, delay)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())


        itemAdapter =
            ItemAdapter(
                requireContext(),
                itemList = MasterDataUtils.MasterDataList(requireContext()),
                ItemAdapter.OnClickListener { itemData, clickType ->
                })

        imageSliderAdapter = ImageSliderAdapter(
            requireContext(),MasterDataUtils.viewPagerImage(requireContext())
        )



        binding.recyclerView.adapter = itemAdapter
        binding.viewPager.adapter = imageSliderAdapter

    }

    override fun onResume() {
        super.onResume()
        handler.postDelayed(runnable, delay)
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}