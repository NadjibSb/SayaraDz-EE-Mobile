package sayaradz.ui.fragment.version

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import sayaradz.authentification.R
import sayaradz.authentification.databinding.VersionFragmentBinding
import sayaradz.dataClasses.Version
import sayaradz.ui.fragment.adapter.ListAdapter
import sayaradz.ui.mainActivity.MainActivity

class VersionFragment : Fragment() {

    private val TAG = "TAG-VersionFragment"

    private lateinit var binding: VersionFragmentBinding
    private lateinit var args: VersionFragmentArgs
    private lateinit var viewModel: VersionViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.version_fragment, container, false)
        args = VersionFragmentArgs.fromBundle(arguments!!)

        var viewModelFactory = VersionViewModelFactory(args.modelId, (activity as MainActivity).getToken())
        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(VersionViewModel::class.java)


        // annimation while loading data
        binding.versionListView.visibility = View.GONE
        binding.shimmerLayout.visibility = View.VISIBLE
        binding.shimmerLayout.startShimmerAnimation()

        // when data loaded => display list
        viewModel.versions.observe(this, Observer { versions ->

            binding.shimmerLayout.stopShimmerAnimation()
            binding.shimmerLayout.visibility = View.GONE

            if (versions.size >0){ // if there is data to display
                binding.versionListView.visibility = View.VISIBLE
                setUpRecycleView(binding.root, versions)
            }else{ // if the list is empty
                Log.i(TAG, "empty")
                binding.emptyListView.visibility = View.VISIBLE
            }
        })

        setupPullToRefresh()


        return binding.root
    }

    private fun setupPullToRefresh() {
        binding.swiperefresh.setOnRefreshListener {
            viewModel.getVersions()
            viewModel.versions.observe(this, Observer { versions ->

                binding.shimmerLayout.stopShimmerAnimation()
                binding.shimmerLayout.visibility = View.GONE

                if (versions.size > 0) { // if there is data to display
                    binding.versionListView.visibility = View.VISIBLE
                    setUpRecycleView(binding.root, versions)
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
    private fun setUpRecycleView(rootView: View, list: List<Version>) {
        var recyclerView = rootView.findViewById(R.id.versionListView) as RecyclerView
        recyclerView.adapter = ListAdapter(list, ListAdapter.ViewHolderType.VERSION, this@VersionFragment.context!!, args.modelId)
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        recyclerView.itemAnimator = SlideInUpAnimator()
        recyclerView.setHasFixedSize(true)
    }
}