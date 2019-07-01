package sayaradz.ui.addAnnonce

import android.graphics.ColorSpace
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import kotlinx.android.synthetic.main.activity_mes_annonces.*
import sayaradz.authentification.R
import sayaradz.dataClasses.Car
import sayaradz.dataClasses.Marque
import sayaradz.dataClasses.Modele
import sayaradz.dataClasses.Version
import sayaradz.ui.fragment.adapter.CarAdapter
import sayaradz.ui.fragment.adapter.ListAdapter
import java.util.*

val  marqueList : List<Marque>  = listOf(Marque("1","Renault"," ") , Marque("2","Peugeout",""), Marque("3","Ford",""), Marque("4","Mercedes",""),Marque("1","Renault"," ") , Marque("2","Peugeout",""), Marque("3","Ford",""), Marque("4","Mercedes",""))//For test
val colors = listOf<String>("white","blue","red")
val modelList: List<Modele>  = listOf(Modele("1","301","1", colors),Modele("2","308","2", colors),Modele("3","301","3", colors))//For test
val  versionList: List<Version>  = listOf(Version("1","v1","301",""),Version("2","v2","308",""),Version("3","v3","308",""))//For test
class AddAnnonceActivity : AppCompatActivity() {
    lateinit var rvMarque: RecyclerView
    lateinit var rvModel: RecyclerView
    lateinit var rvVersion : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_annonce)
        setSupportActionBar(TopToolbar)
        rvMarque = findViewById <View>(R.id.marqueListView) as RecyclerView
        rvModel=findViewById <View>(R.id.modelListView) as RecyclerView
        rvVersion=findViewById <View>(R.id.versionListView) as RecyclerView
        rvMarque.adapter = ListAdapter(marqueList, ListAdapter.ViewHolderType.MARQUE, this@AddAnnonceActivity, "token")
        rvModel.adapter = ListAdapter(modelList, ListAdapter.ViewHolderType.MODEL, this@AddAnnonceActivity, "token")
        rvVersion.adapter = ListAdapter(versionList, ListAdapter.ViewHolderType.VERSION, this@AddAnnonceActivity, "token")
        setUpRecycleView(rvMarque)
        setUpRecycleView(rvModel)
        setUpRecycleView(rvVersion)



    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_top_toolbar, menu)
        return true
    }

    private fun setUpRecycleView(rv : RecyclerView) {
        rv.layoutManager = GridLayoutManager(this, 3)
        rv.itemAnimator = SlideInUpAnimator()
        rv.setHasFixedSize(true)
    }

}
