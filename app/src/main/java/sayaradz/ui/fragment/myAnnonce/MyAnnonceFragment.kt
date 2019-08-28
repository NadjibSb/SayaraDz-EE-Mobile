package sayaradz.ui.fragment.myAnnonce

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import kotlinx.android.synthetic.main.add_annonce_fragment.*
import kotlinx.android.synthetic.main.my_annonce_fragment.*
import sayaradz.authentification.R
import sayaradz.authentification.databinding.MyAnnonceFragmentBinding
import sayaradz.dataClasses.Car
import sayaradz.ui.fragment.adapter.ListAdapter

class MyAnnonceFragment : Fragment() {



    val TAG = "TAG-MyAnnonceFragment"
    private lateinit var binding: MyAnnonceFragmentBinding
    lateinit var mesAnnoncesViewModel: MyAnnonceViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.my_annonce_fragment, container, false)
        mesAnnoncesViewModel = ViewModelProviders.of(this)
                .get(MyAnnonceViewModel::class.java)

        mesAnnoncesViewModel.annonces.observe(this, Observer { annonces ->
            setUpRecycleView(binding.root, annonces)
        })

        val action = MyAnnonceFragmentDirections.actionMyAnnonceFragmentToAddAnnonceFragment()
       val btn= binding.root.findViewById<View>(R.id.btn_add_anc)
        btn.setOnClickListener {
          v : View ->
            v.findNavController().navigate(action)

        }
        return binding.root
    }




    //RecycleView--------------------------------------------
    private fun setUpRecycleView(rootView: View, list: List<Car>) {
        var recyclerView = rootView.findViewById(R.id.annonceListView) as RecyclerView
        recyclerView.adapter = ListAdapter(list, ListAdapter.ViewHolderType.MyAnnonce, this@MyAnnonceFragment.context!!, "token")
        recyclerView.layoutManager = GridLayoutManager( context,1)
        recyclerView.itemAnimator = SlideInUpAnimator()
        recyclerView.setHasFixedSize(true)
    }



}

