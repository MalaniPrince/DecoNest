package princeinfotech.deconest.ui.home

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import princeinfotech.deconest.R
import princeinfotech.deconest.databinding.*

class HomeMainActivity : AppCompatActivity() {

    // private lateinit var appBarConfiguration: AppBarConfiguration
    //private lateinit var binding: ActivityMainBinding

    lateinit var itemAdapter: ItemAdapter
    lateinit var dataList: ArrayList<ModelClass>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    val recyclerView :RecyclerView=findViewById<RecyclerView>(R.id.recyclercontent)

        dataList=ArrayList()
        dataList.add(ModelClass(R.drawable.image1, name = "Chair"))
        dataList.add(ModelClass(R.drawable.image2, name = "Black Chair"))
        dataList.add(ModelClass(R.drawable.image3, name = "sofa"))
        dataList.add(ModelClass(R.drawable.image4, name = "Simple Chair"))
        dataList.add(ModelClass(R.drawable.image5, name = "Sofa Chair"))
        dataList.add(ModelClass(R.drawable.image6, name = "green color Sofa"))
        dataList.add(ModelClass(R.drawable.image7, name = "yellow color Sofa"))
        dataList.add(ModelClass(R.drawable.image8, name = "Long Sofa"))



        itemAdapter= ItemAdapter(dataList,this)
        recyclerView.layoutManager=LinearLayoutManager(this)
        recyclerView.adapter=itemAdapter
    }
}
        /*binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)


    }
}

        /*val navController = findNavController(R.id.nav_host_fragment_home_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .setAnchorView(R.id.fab).show()
        }
    }

    /*override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_home_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}*/