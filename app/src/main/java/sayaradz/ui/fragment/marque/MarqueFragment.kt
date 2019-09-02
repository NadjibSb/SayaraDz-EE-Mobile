package sayaradz.ui.fragment.marque

import android.os.Bundle
import android.util.Log
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
import sayaradz.authentification.databinding.MarqueFragmentBinding
import sayaradz.dataClasses.Marque
import sayaradz.ui.fragment.adapter.ListAdapter


class MarqueFragment : Fragment() {
    private val TAG = "TAG-MarqueFragment"
    lateinit var binding: MarqueFragmentBinding

    private lateinit var marqueViewModel: MarqueViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.marque_fragment, container, false)
        //(activity as MainActivity).actionBar.title = "Marques"
        marqueViewModel = ViewModelProviders.of(this).get(MarqueViewModel::class.java)

        // loading view
        binding.marqueListView.visibility = View.GONE
        binding.shimmerLayout.visibility = View.VISIBLE
        binding.shimmerLayout.startShimmerAnimation()

        // when data loaded
        marqueViewModel.marques.observe(this, Observer { marques ->

            binding.shimmerLayout.stopShimmerAnimation()
            binding.shimmerLayout.visibility = View.GONE

            if (marques.size >0){ // if there is data to display
                binding.marqueListView.visibility = View.VISIBLE
                setUpRecycleView(binding.root, marques)
            }else{ // if the list is empty
                Log.i(TAG, "empty")
                binding.emptyListView.visibility = View.VISIBLE
            }
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