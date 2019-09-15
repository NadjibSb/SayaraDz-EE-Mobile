package sayaradz.ui.fragment.model

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
import sayaradz.authentification.databinding.ModelFragmentBinding
import sayaradz.dataClasses.Modele
import sayaradz.ui.fragment.adapter.ListAdapter
import sayaradz.ui.mainActivity.MainActivity

class ModelFragment : Fragment() {

    private val TAG = "TAG-ModelFragment"

    lateinit var binding: ModelFragmentBinding
    lateinit var args: ModelFragmentArgs
    lateinit var modelViewModel: ModelViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.model_fragment, container, false)
        args = ModelFragmentArgs.fromBundle(arguments!!)

        var viewModelFactory = ModelViewModelFactory(args.marqueId, (activity as MainActivity).getToken())
        modelViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(ModelViewModel::class.java)

        // annimation while loading data
        binding.modelListView.visibility = View.GONE
        binding.shimmerLayout.visibility = View.VISIBLE
        binding.shimmerLayout.startShimmerAnimation()

        // when data loaded => display list or empty list
        modelViewModel.models.observe(this, Observer { models ->

            binding.shimmerLayout.stopShimmerAnimation()
            binding.shimmerLayout.visibility = View.GONE
            if (models.size > 0) {
                binding.modelListView.visibility = View.VISIBLE
                setUpRecycleView(binding.root, models)
            } else {
                Log.i(TAG, "empty")
                binding.emptyListView.visibility = View.VISIBLE
            }

        })

        setupPullToRefresh()

        return binding.root
    }

    private fun setupPullToRefresh() {
        binding.swiperefresh.setOnRefreshListener {
            modelViewModel.getModels()
            modelViewModel.models.observe(this, Observer { models ->

                binding.shimmerLayout.stopShimmerAnimation()
                binding.shimmerLayout.visibility = View.GONE
                if (models.size > 0) {
                    binding.modelListView.visibility = View.VISIBLE
                    setUpRecycleView(binding.root, models)
                } else {
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
    private fun setUpRecycleView(rootView: View, list: List<Modele>) {
        var recyclerView = rootView.findViewById(R.id.modelListView) as RecyclerView
        recyclerView.adapter = ListAdapter(list, ListAdapter.ViewHolderType.MODEL, this@ModelFragment.context!!, args.marqueId)
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        recyclerView.itemAnimator = SlideInUpAnimator()
        recyclerView.setHasFixedSize(true)
    }

}