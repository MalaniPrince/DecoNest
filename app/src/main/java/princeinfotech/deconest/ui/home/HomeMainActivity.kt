package princeinfotech.deconest.ui.home

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import princeinfotech.deconest.R
import princeinfotech.deconest.ui.adpter.ItemAdapter
import princeinfotech.deconest.ui.data.ModelClass

class HomeMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // This must have FrameLayout with id = fragment_container

        // Load HomeFragment when MainActivity starts
        //findNavController(R.id.nav_host_fragment).navigate(R.id.action_to_homeFragment)

    }
}