package princetechlabs.deconest.ui.utils

import android.content.Context
import princetechlabs.deconest.ui.data.ModelClass
import java.util.ArrayList

object MasterDataUtils {
    fun MasterDataList(context: Context): ArrayList<ModelClass> {

        val dataList = ArrayList<ModelClass>()
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
        return dataList
    }

    fun viewPagerImage(context: Context): ArrayList<String> {

        val dataList1 = ArrayList<String>()
        dataList1.add(
                "https://as2.ftcdn.net/v2/jpg/00/29/79/51/1000_F_29795157_gLLeeTiFDsqno207woilLAq0jeOyOqdu.jpg",

        )
        dataList1.add(
                "https://as2.ftcdn.net/v2/jpg/02/83/53/27/1000_F_283532744_Rbs039ygDyekkcCgXp7n3fwEPfB21tjJ.jpg",

        )
        dataList1.add(
                "https://as2.ftcdn.net/v2/jpg/02/83/53/27/1000_F_283532785_Nt40tPnij5PzXTUERVRmHJPOFmjZ87ZM.jpg",


        )
        return dataList1
    }
}