package sayaradz.ui.fragment.myOffers

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import sayaradz.authentification.R

class MyOffersFragment : Fragment() {

    companion object {
        fun newInstance() = MyOffersFragment()
    }

    private lateinit var viewModel: MyOffersViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.my_offers_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MyOffersViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
