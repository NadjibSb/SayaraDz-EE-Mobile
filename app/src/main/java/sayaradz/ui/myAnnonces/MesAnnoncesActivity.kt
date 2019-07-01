package sayaradz.ui.myAnnonces

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import kotlinx.android.synthetic.main.activity_mes_annonces.*
import sayaradz.authentification.R
import sayaradz.dataClasses.Car
import sayaradz.ui.addAnnonce.AddAnnonceActivity
import sayaradz.ui.fragment.adapter.ListAdapter


const val API_URL = "https://sayaradz-ee-backend.herokuapp.com/"

class MesAnnoncesActivity : AppCompatActivity() {

    val TAG = "TAG-MesAnnoncesActivity"
    /* private lateinit var binding: BINDING NOT FOUND
     lateinit var mesAnnoncesActivityViewModel: MesAnnoncesActivityViewModel*/


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /* binding = DataBindingUtil.setContentView(this,R.layout.activity_mes_annonces)
         mesAnnoncesActivityViewModel = ViewModelProviders.of(this)
                 .get(MesAnnoncesActivityViewModel::class.java)

          mesAnnoncesActivityViewModel.annonces.observe(this, Observer { annonces->
             setUpRecycleView(binding.root, annonces )
         })

         //Setuo UI elements
         setSupportActionBar(binding.TopToolbar) */
        setContentView(R.layout.activity_mes_annonces)
        setSupportActionBar(TopToolbar)
        fab.setOnClickListener {
            startActivity(Intent(this@MesAnnoncesActivity, AddAnnonceActivity::class.java))
        }

    }


    /**
     ****************Menu Methods
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_top_toolbar, menu)
        return true
    }

    /*  PROBLEM IN BINDING

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
         mesAnnoncesActivityViewModel.signOut()
         Toast.makeText(this, "Sign out", Toast.LENGTH_SHORT).show()
         startActivity(Intent(this@MesAnnoncesActivity, CreateAccountActivity::class.java))
         return super.onOptionsItemSelected(item)
     }*/
    //RecycleView--------------------------------------------
    private fun setUpRecycleView(rootView: View, list: List<Car>) {
        var recyclerView = rootView.findViewById(R.id.annonceListView) as RecyclerView
        recyclerView.adapter = ListAdapter(list, ListAdapter.ViewHolderType.CAR, this@MesAnnoncesActivity, "token")
        recyclerView.layoutManager = GridLayoutManager(this, 1)
        recyclerView.itemAnimator = SlideInUpAnimator()
        recyclerView.setHasFixedSize(true)
    }
}