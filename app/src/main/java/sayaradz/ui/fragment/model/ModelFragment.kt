package sayaradz.ui.fragment.model

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
import sayaradz.authentification.databinding.ModelFragmentBinding
import sayaradz.dataClasses.Modele
import sayaradz.ui.fragment.adapter.ListAdapter

class ModelFragment : Fragment() {

    lateinit var binding: ModelFragmentBinding
    lateinit var args: ModelFragmentArgs
    lateinit var modelViewModel: ModelViewModel



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.model_fragment, container, false)
        args = ModelFragmentArgs.fromBundle(arguments!!)

        var viewModelFactory = ModelViewModelFactory(args.marqueId)
        modelViewModel = ViewModelProviders.of(this,viewModelFactory)
                .get(ModelViewModel::class.java)

        modelViewModel.models.observe(this, Observer { modeles ->
            setUpRecycleView(binding.root, modeles)

        })
        return binding.root
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