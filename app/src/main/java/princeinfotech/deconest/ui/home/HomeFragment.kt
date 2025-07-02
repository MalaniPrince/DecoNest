    package princeinfotech.deconest.ui.home

    import android.os.Bundle
    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import androidx.fragment.app.Fragment
    import androidx.recyclerview.widget.LinearLayoutManager
    import princeinfotech.deconest.databinding.FragmentFirstBinding
    import princeinfotech.deconest.ui.adpter.ItemAdapter
    import princeinfotech.deconest.ui.data.ModelClass

    class HomeFragment : Fragment() {

        private var _binding: FragmentFirstBinding? = null
        private val binding get() = _binding!!

        private lateinit var itemAdapter: ItemAdapter
        private lateinit var dataList: ArrayList<ModelClass>

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?,
        ): View {
            _binding = FragmentFirstBinding.inflate(inflater, container, false)

            dataList = ArrayList()
            dataList.add(
                ModelClass(
                    "https://as2.ftcdn.net/v2/jpg/00/29/79/51/1000_F_29795157_gLLeeTiFDsqno207woilLAq0jeOyOqdu.jpg",
                    name = "Chair"
                )
            )
            dataList.add(
                ModelClass(
                    "https://as2.ftcdn.net/v2/jpg/02/83/53/27/1000_F_283532744_Rbs039ygDyekkcCgXp7n3fwEPfB21tjJ.jpg",
                    name = "Black Chair"
                )
            )
            dataList.add(
                ModelClass(
                    "https://as2.ftcdn.net/v2/jpg/02/83/53/27/1000_F_283532785_Nt40tPnij5PzXTUERVRmHJPOFmjZ87ZM.jpg",
                    name = "Sofa"
                )
            )

            itemAdapter = ItemAdapter(requireContext(), dataList, onClickListener = {})

            binding.recyclercontent.layoutManager = LinearLayoutManager(requireContext())
            binding.recyclercontent.adapter = itemAdapter

            return binding.root
        }

        override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }
    }
