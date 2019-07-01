package sayaradz.ui.fragment.home
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import sayaradz.authentification.R
import sayaradz.authentification.databinding.AnnonceFragmentBinding
import sayaradz.authentification.databinding.HomeFragmentBinding
import sayaradz.dataClasses.Car
import sayaradz.ui.fragment.adapter.ListAdapter
import sayaradz.ui.fragment.annonce.HomeViewModel


class HomeFragment : Fragment() {
    val TAG = "TAG-HomeFragment"
    lateinit var binding: HomeFragmentBinding
    private lateinit var homeViewModel: HomeViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.home_fragment, container, false)
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)


       /* homeViewModel.annonces.observe(this, Observer { annonces->
            setUpRecycleView(binding.root, annonces )
        })*/
        return binding.root
    }


    //RecycleView--------------------------------------------
  /*  private fun setUpRecycleView(rootView: View, list: List<Car>) {
        var recyclerView = rootView.findViewById(R.id.annonceListView) as RecyclerView
        recyclerView.adapter = ListAdapter(list, ListAdapter.ViewHolderType.CAR, this@HomeFragment.context!!,"token")
        recyclerView.layoutManager = GridLayoutManager(context, 1)
        recyclerView.itemAnimator = SlideInUpAnimator()
        recyclerView.setHasFixedSize(true)
    }*/
}