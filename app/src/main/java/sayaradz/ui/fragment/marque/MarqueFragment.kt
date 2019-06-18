package sayaradz.ui.fragment.marque

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import sayaradz.authentification.R
import sayaradz.authentification.databinding.MarqueFragmentBinding
import sayaradz.dataClasses.Marque
import sayaradz.ui.fragment.adapter.ListAdapter


class MarqueFragment : Fragment() {
    val TAG = "TAG-MarqueFragment"
    lateinit var binding : MarqueFragmentBinding

    private lateinit var marqueViewModel: MarqueViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater,R.layout.marque_fragment,container,false)
        marqueViewModel = ViewModelProviders.of(this).get(MarqueViewModel::class.java)


        marqueViewModel.getMarques("token").observe(this, Observer { marques->
            setUpRecycleView(binding.root, marques )
        })
        return binding.root
    }


    //RecycleView--------------------------------------------
    private fun setUpRecycleView(rootView: View, list: List<Marque>) {
        var recyclerView = rootView.findViewById(R.id.marqueListView) as RecyclerView
        recyclerView.adapter = ListAdapter(list, ListAdapter.ViewHolderType.MARQUE, this@MarqueFragment.context!!, "token")
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        recyclerView.itemAnimator = SlideInUpAnimator()
        recyclerView.setHasFixedSize(true)
    }

}