package sayaradz.ui.fragment.myAnnonce


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import sayaradz.authentification.R
import sayaradz.authentification.databinding.MyAnnonceFragmentBinding
import sayaradz.dataClasses.Car
import sayaradz.ui.MainActivityViewModel
import sayaradz.ui.fragment.adapter.ListAdapter

class MyAnnonceFragment : Fragment() {


    companion object {
        lateinit var lifecycleRegistry : LifecycleRegistry
        lateinit var recyclerView : RecyclerView
        lateinit var contextMyAn : Context
        var token= MainActivityViewModel.token
        fun life( life : LifecycleRegistry , rv : RecyclerView , context : Context)
        {   contextMyAn = context
            lifecycleRegistry=life
            recyclerView=rv
        }
    }

    val TAG = "TAG-MyAnnonceFragment"
    private lateinit var binding: MyAnnonceFragmentBinding
    lateinit var mesAnnoncesViewModel: MyAnnonceViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.my_annonce_fragment, container, false)
        var life =LifecycleRegistry(this)
        life.markState(Lifecycle.State.CREATED)
        var recyclerView =binding.root.findViewById(R.id.annonceListView) as RecyclerView
        life(life,recyclerView,this@MyAnnonceFragment.context!!)
        mesAnnoncesViewModel = ViewModelProviders.of(this)
                .get(MyAnnonceViewModel::class.java)

        MyAnnonceViewModel.newAnnonces.observe(this, Observer { annonces ->
            Log.i("SIZE" ,annonces.size.toString())
            setUpRecycleView(binding.root, annonces)
        })


      /*  mesAnnoncesViewModel.annonces.observe(this, Observer { annonces ->
            setUpRecycleView(binding.root, annonces)
        })*/

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


        recyclerView.layoutManager = GridLayoutManager( context,1)
        recyclerView.itemAnimator = SlideInUpAnimator()
        recyclerView.setHasFixedSize(true)
    }

    override fun onStart() {
        super.onStart()
        lifecycleRegistry.markState(Lifecycle.State.STARTED)

    }

    }




