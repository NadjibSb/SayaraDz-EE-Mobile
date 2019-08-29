package sayaradz.ui.fragment.editMyAnnonce

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import sayaradz.authentification.R
import sayaradz.authentification.databinding.EditAnnonceFragmentBinding

class EditAnnonceFragment : Fragment() {

    val TAG = "TAG-EditAnnonceFragment"
    private lateinit var binding: EditAnnonceFragmentBinding
    lateinit var editAnnonceViewModel: EditAnnonceViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.edit_annonce_fragment, container, false)

        var args=EditAnnonceFragmentArgs.fromBundle(arguments!!)
        var editAnnonceViewModelFactory =  EditAnnonceViewModelFactory( args.annonceId )
        Log.i("IDENTIFIER",args.annonceId)
        editAnnonceViewModel = ViewModelProviders.of(this,editAnnonceViewModelFactory).get(EditAnnonceViewModel::class.java)
        editAnnonceViewModel.annonce.observe(this, Observer { an ->

           //  Log.i("3onwanAvant",binding.details.edit_text_title.text.toString())
            Log.i("3onwanApre",an.title)
            //Info static can not be updated
            binding.details.textYearValue.text=an.date
            binding.details.versionName.text=an.versionName
            binding.details.modelName.text=an.modelName
            binding.details.marqueName.text= an.marqueName

            //Info to update
            binding.details.title.hint = an.title
            binding.details.etDescrp.hint=an.commentaires
            binding.details.etColor.hint = an.color
            binding.details.etKm.hint =an.kilometrage.toString()
            binding.details.etPrice.hint = an.prix.toString()






        })


        val action = EditAnnonceFragmentDirections.actionEditAnnonceFragmentToMesAnnoncesFragment()
        var btnConfirm = binding.root.findViewById<FloatingActionButton>(R.id.btn_confirm_edition)
      btnConfirm.setOnClickListener {
              v: View ->

          // Sending Query

              v.findNavController().navigate(action)
          }

        return binding.root
    }

}
