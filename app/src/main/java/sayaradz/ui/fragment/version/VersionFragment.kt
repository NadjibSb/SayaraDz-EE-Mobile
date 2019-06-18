package sayaradz.ui.fragment.version

import android.os.Bundle
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

class VersionFragment : Fragment() {

    lateinit var binding: VersionFragmentBinding
    lateinit var args: VersionFragmentArgs
    lateinit var viewModel: VersionViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.version_fragment, container, false)
        args = VersionFragmentArgs.fromBundle(arguments!!)
        Toast.makeText(context, "Modele id: ${args.modelId}", Toast.LENGTH_SHORT).show()

        var viewModelFactory = VersionViewModelFactory(args.modelId)
        viewModel = ViewModelProviders.of(this,viewModelFactory)
                .get(VersionViewModel::class.java)

        viewModel.versions.observe(this, Observer { versions ->
            //setUpRecycleView(binding.root,versions)
        })


        return binding.root
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