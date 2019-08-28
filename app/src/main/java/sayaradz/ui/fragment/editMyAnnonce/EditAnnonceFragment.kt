package sayaradz.ui.fragment.editMyAnnonce

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.edit_annonce_fragment.*
import sayaradz.authentification.R
import sayaradz.authentification.databinding.EditAnnonceFragmentBinding

class EditAnnonceFragment : Fragment() {

    val TAG = "TAG-EditAnnonceFragment"
    private lateinit var binding: EditAnnonceFragmentBinding
    lateinit var mesAnnoncesViewModel: EditAnnonceViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.edit_annonce_fragment, container, false)
        mesAnnoncesViewModel = ViewModelProviders.of(this)
                .get(EditAnnonceViewModel::class.java)

 val action = EditAnnonceFragmentDirections.actionEditAnnonceFragmentToMesAnnoncesFragment()
        var btnConfirm = binding.root.findViewById<FloatingActionButton>(R.id.btn_confirm_edition)
      btnConfirm.setOnClickListener {
              v: View ->
              v.findNavController().navigate(action)
          }

        return binding.root
    }

}
