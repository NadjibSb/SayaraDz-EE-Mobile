package sayaradz.ui.fragment.myAnnonceView

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.my_annonce_view_fragment.*
import sayaradz.authentification.R
import sayaradz.authentification.databinding.MyAnnonceFragmentBinding
import sayaradz.authentification.databinding.MyAnnonceViewFragmentBinding
import sayaradz.ui.fragment.myAnnonce.MyAnnonceViewModel

class MyAnnonceViewFragment : Fragment () {



    val TAG = "TAG-MyAnnonceViewFragment"
    private lateinit var binding: MyAnnonceViewFragmentBinding
    lateinit var mesAnnoncesViewModel: MyAnnonceViewViewModel
    var annonceId=""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.my_annonce_view_fragment, container, false)
        mesAnnoncesViewModel = ViewModelProviders.of(this)
                .get(MyAnnonceViewViewModel::class.java)


        val args:MyAnnonceViewFragmentArgs by navArgs()
        annonceId = args.annonceId.toString()
        var btnEdit = binding.root.findViewById<FloatingActionButton>(R.id.btn_edit)
        val action = MyAnnonceViewFragmentDirections.actionViewAnnonceToEditAnnonceFragment(annonceId)
        btnEdit.setOnClickListener {
            v: View ->
            v.findNavController().navigate(action)
        }
        return binding.root
    }

}