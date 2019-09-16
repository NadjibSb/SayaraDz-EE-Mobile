package sayaradz.ui.fragment.myAnnonceOffers

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
import sayaradz.authentification.databinding.MyAnnonceOffersFragmentBinding
import sayaradz.authentification.databinding.MyOffersFragmentBinding
import sayaradz.dataClasses.OfferToGet
import sayaradz.dataClasses.OfferToPost
import sayaradz.ui.fragment.adapter.ListAdapter
import sayaradz.ui.fragment.myOffers.MyAnnonceOffersViewModel
import sayaradz.ui.fragment.myOffers.MyAnnonceOffersViewModelFactory
import sayaradz.ui.mainActivity.MainActivity

class MyAnnonceOffersFragment : Fragment() {

    companion object {
        fun newInstance() = MyAnnonceOffersFragment()
    }

    private lateinit var viewModel: MyAnnonceOffersViewModel
    private lateinit var binding: MyAnnonceOffersFragmentBinding
    var annonceId=""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.my_annonce_offers_fragment, container, false)
       var args=MyAnnonceOffersFragmentArgs.fromBundle(arguments!!)
        annonceId = args.annonceId
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        var factory= MyAnnonceOffersViewModelFactory((activity as MainActivity).getToken(),annonceId)
        viewModel = ViewModelProviders.of(this, factory).get(MyAnnonceOffersViewModel::class.java)

        viewModel.offers.observe(this, Observer { list->
            setUpRecycleView(binding.root, list)
        })
    }

    //RecycleView--------------------------------------------
    private fun setUpRecycleView(rootView: View, list: List<OfferToGet>) {
        var recyclerView = binding.offersList
        recyclerView.adapter = ListAdapter(
                list,
                ListAdapter.ViewHolderType.ANNONCE_OFFERS,
                this@MyAnnonceOffersFragment.context!!,
                "token")
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.itemAnimator = SlideInUpAnimator()
        recyclerView.setHasFixedSize(true)
    }



}
