package sayaradz.ui.fragment.marque

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import sayaradz.authentification.R
import sayaradz.authentification.databinding.MarqueFragmentBinding
import sayaradz.dataClasses.Marque
import sayaradz.ui.fragment.adapter.ListAdapter
import sayaradz.ui.mainActivity.MainActivity


class MarqueFragment : Fragment() {
    private val TAG = "TAG-MarqueFragment"
    lateinit var binding: MarqueFragmentBinding

    private lateinit var marqueViewModel: MarqueViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.marque_fragment, container, false)


        // annimation
        binding.marqueListView.visibility = View.GONE
        binding.shimmerLayout.visibility = View.VISIBLE
        binding.shimmerLayout.startShimmerAnimation()

        val ready = MutableLiveData<Boolean>()

        // wait for Token to be loaded
        (activity as MainActivity).isAuth().observe(this, Observer { isAuth ->
            if (isAuth){
                val factory = MarqueViewModelFactory((activity as MainActivity).getToken())
                marqueViewModel = ViewModelProviders.of(this,factory).get(MarqueViewModel::class.java)
                ready.value = true
            }
        })

        // Token loaded => now we can request data
        ready.observe(this, Observer { r->
            if (r){
                // when data loaded => display list or empty list
                marqueViewModel.marques.observe(this, Observer { marques ->

                    binding.shimmerLayout.stopShimmerAnimation()
                    binding.shimmerLayout.visibility = View.GONE

                    if (marques.size > 0) { // if there is data to display
                        binding.marqueListView.visibility = View.VISIBLE
                        setUpRecycleView(binding.root, marques)
                    } else { // if the list is empty
                        Log.i(TAG, "empty")
                        binding.emptyListView.visibility = View.VISIBLE
                    }
                })
            }
        })
        setupPullToRefresh()

        return binding.root
    }

    private fun setupPullToRefresh() {
        binding.swiperefresh.setOnRefreshListener {
            marqueViewModel.getMarques()
            marqueViewModel.marques.observe(this, Observer { marques ->

                binding.shimmerLayout.stopShimmerAnimation()
                binding.shimmerLayout.visibility = View.GONE
                if (marques.size > 0) { // if there is data to display
                    binding.marqueListView.visibility = View.VISIBLE
                    setUpRecycleView(binding.root, marques)
                } else { // if the list is empty
                    Log.i(TAG, "empty")
                    binding.emptyListView.visibility = View.VISIBLE
                }

                binding.swiperefresh.isRefreshing = false
            })
        }

        binding.swiperefresh.setColorSchemeResources(
                R.color.colorPrimaryLight,
                R.color.colorPrimary,
                R.color.colorPrimaryDark)
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