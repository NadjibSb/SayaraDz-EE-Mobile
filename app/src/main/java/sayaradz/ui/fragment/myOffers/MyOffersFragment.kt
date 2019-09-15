package sayaradz.ui.fragment.myOffers

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator

import sayaradz.authentification.R
import sayaradz.authentification.databinding.MyOffersFragmentBinding
import sayaradz.dataClasses.OfferToPost
import sayaradz.ui.fragment.adapter.ListAdapter
import sayaradz.ui.mainActivity.MainActivity

class MyOffersFragment : Fragment() {

    companion object {
        fun newInstance() = MyOffersFragment()
    }

    private lateinit var viewModel: MyOffersViewModel
    private lateinit var binding: MyOffersFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.my_offers_fragment, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        var factory= MyOffersViewModelFactory((activity as MainActivity).getToken())
        viewModel = ViewModelProviders.of(this, factory).get(MyOffersViewModel::class.java)

        viewModel.offers.observe(this, Observer { list->
            setUpRecycleView(binding.root, list)
        })
    }

    //RecycleView--------------------------------------------
    private fun setUpRecycleView(rootView: View, list: List<OfferToPost>) {
        var recyclerView = binding.offersList
        recyclerView.adapter = ListAdapter(
                list,
                ListAdapter.ViewHolderType.OFFER,
                this@MyOffersFragment.context!!,
                "token")
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.itemAnimator = SlideInUpAnimator()
        recyclerView.setHasFixedSize(true)
    }



}
