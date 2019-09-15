package sayaradz.ui.fragment.annonce

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import com.smarteist.autoimageslider.DefaultSliderView
import com.smarteist.autoimageslider.IndicatorAnimations
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderLayout
import sayaradz.authentification.R
import sayaradz.authentification.databinding.AnnonceFragmentBinding
import sayaradz.ui.mainActivity.MainActivity

class AnnonceFragment : Fragment() {


    val TAG = "TAG-AnnonceFragment"
    lateinit var binding: AnnonceFragmentBinding
    var prixMin = 0
    var img = ""
    lateinit var btnFaireOffre: Button
    lateinit var annonceViewModel: AnnonceViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.annonce_fragment, container, false)
        var args = AnnonceFragmentArgs.fromBundle(arguments!!)
        var annonceViewModelFactory = AnnonceViewModelFactory(args.annonceId, (activity as MainActivity).getToken())
        Log.i("IDENTIFIER", args.annonceId)
        annonceViewModel = ViewModelProviders.of(this, annonceViewModelFactory).get(AnnonceViewModel::class.java)
        annonceViewModel.annonce.observe(this, Observer { an ->

            /* Log.i("3onwanAvant",binding.details.title.text.toString())
             Log.i("3onwanApre",an.title)*/
            binding.details.title.text = an.title
            binding.details.textDescrip.text = an.commentaires
            binding.details.textKm.text = an.kilometrage.toString()
            binding.details.textPriceMin.text = an.prix.toString()
            binding.details.textYear.text = an.date
            binding.details.versionName.text = an.versionName
            binding.details.modelName.text = an.modelName
            binding.details.marqueName.text = an.marqueName
            binding.details.textColor.text = an.color
            prixMin = an.prix
            img = an.imageVehicle1
        })

        // Check for the offer
        btnFaireOffre = binding.btnOffre
        btnFaireOffre.setOnClickListener { v: View ->
            var prixToSuggest = binding.prix.text
            if (!prixToSuggest.isNullOrBlank()) {
                Log.i("PRICE", prixToSuggest.toString())
                if (prixToSuggest.toString().toInt() >= prixMin) showDialogOk(this@AnnonceFragment.context!!, v)
                else showDialogNotOk(this@AnnonceFragment.context!!, v)

            } else Snackbar.make(v, "Veuillez introduire un prix", Snackbar.LENGTH_SHORT).show()
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setSliderViews(binding.details.imageSlider, img)

    }


    fun showDialogOk(c: Context, v: View) {
        val builder = AlertDialog.Builder(c)
        builder.setIcon(resources.getDrawable(R.drawable.ic_assignment))
        val action = AnnonceFragmentDirections.actionAnnonceFragmentToSearchFragment()

        // Set the alert dialog title
        builder.setTitle("Confirmation offre")

        // Display a message on alert dialog
        builder.setMessage("Voulez vraiment faire cette offre ?")

        // Set a positive button and its click listener on alert dialog
        builder.setPositiveButton("Oui") { dialog, which ->
            // Do something when user press the positive button
            Toast.makeText(c, "offre faite avec succès ", Toast.LENGTH_SHORT).show()

            // Send the query of sending Offre and go to searchFrag
            v.findNavController().navigate(action)

        }
        // Display a negative button on alert dialog
        builder.setNegativeButton("Non") { dialog, which ->
            Toast.makeText(c, "Offre non envoyée", Toast.LENGTH_SHORT).show()

            //Go to searchFragment
            v.findNavController().navigate(action)

        }

        // Display a neutral button on alert dialog
        builder.setNeutralButton("Annuler") { _, _ ->
            Toast.makeText(c, "Ofrre annulée ", Toast.LENGTH_SHORT).show()
        }

        // Finally, make the alert dialog using builder
        val dialog: AlertDialog = builder.create()

        // Display the alert dialog on app interface
        dialog.show()
    }

    fun showDialogNotOk(c: Context, v: View) {
        val builder = AlertDialog.Builder(c)
        builder.setTitle("Offre non valide ")
        builder.setMessage("Veuillez entrer un prix plus grand")
        builder.setIcon(resources.getDrawable(R.drawable.ic_highlight_off_black_24dp))
        // Set a positive button and its click listener on alert dialog
        builder.setPositiveButton("OK") { dialog, which ->
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }


    private fun setSliderViews(layout: SliderLayout, imageUrl: String) {

        var sliderLayout = layout
        sliderLayout.setIndicatorAnimation(IndicatorAnimations.THIN_WORM) //set indicator animation by using SliderLayout.Animations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderLayout.setSliderTransformAnimation(SliderAnimations.FADETRANSFORMATION)
        sliderLayout.setScrollTimeInSec(2)

        for (i in 0..3) {

            val sliderView = DefaultSliderView(context)

            when (i) {
                0 -> sliderView.imageUrl = imageUrl
                1 -> sliderView.imageUrl = "https://www.autobip.com/storage/photos/new_car_prices/20/peugeot_208_tech_vision_1_6_hdi_92ch_2019-03-04-13-2892232.jpg"
                2 -> sliderView.imageUrl = "https://www.autobip.com/storage/photos/new_car_prices/20/peugeot_208_tech_vision_1_6_hdi_92ch_2019-03-04-13-0687668.jpg"
                3 -> sliderView.imageUrl = "https://www.autobip.com/storage/photos/new_car_prices/20/peugeot_208_tech_vision_1_6_hdi_92ch_2019-03-04-13-4228700.jpg"
            }

            sliderView.setImageScaleType(ImageView.ScaleType.CENTER_CROP)

            //at last add this view in your layout :
            sliderLayout.addSliderView(sliderView)
        }
    }
}