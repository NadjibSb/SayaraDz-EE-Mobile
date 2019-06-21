package sayaradz.ui.fragment.annonce
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
import sayaradz.dataClasses.Car
import sayaradz.ui.fragment.adapter.ListAdapter


class AnnonceFragment : Fragment() {
    val TAG = "TAG-AnnonceFragment"
    lateinit var binding: AnnonceFragmentBinding
    private lateinit var annonceViewModel: AnnonceViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.annonce_fragment, container, false)
        annonceViewModel = ViewModelProviders.of(this).get(AnnonceViewModel::class.java)


        annonceViewModel.annonces.observe(this, Observer { annonces->
            setUpRecycleView(binding.root, annonces )
        })
        return binding.root
    }


    //RecycleView--------------------------------------------
    private fun setUpRecycleView(rootView: View, list: List<Car>) {
        var recyclerView = rootView.findViewById(R.id.annonceListView) as RecyclerView
        recyclerView.adapter = ListAdapter(list, ListAdapter.ViewHolderType.CAR, this@AnnonceFragment.context!!,"token")
        recyclerView.layoutManager = GridLayoutManager(context, 1)
        recyclerView.itemAnimator = SlideInUpAnimator()
        recyclerView.setHasFixedSize(true)
    }
}