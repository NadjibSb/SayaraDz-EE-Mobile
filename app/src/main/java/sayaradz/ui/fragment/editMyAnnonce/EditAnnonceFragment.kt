package sayaradz.ui.fragment.editMyAnnonce

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.smarteist.autoimageslider.DefaultSliderView
import com.smarteist.autoimageslider.IndicatorAnimations
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderLayout
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sayaradz.authentification.R
import sayaradz.authentification.databinding.EditAnnonceFragmentBinding
import sayaradz.dataClasses.AnnonceUpdate
import sayaradz.dataClasses.Car
import sayaradz.dataClasses.Vehicule
import sayaradz.dataClasses.VehiculeUpdate
import sayaradz.ui.mainActivity.MainActivity

class EditAnnonceFragment : Fragment() {

    val TAG = "TAG-EditAnnonceFragment"
    private lateinit var binding: EditAnnonceFragmentBinding
    lateinit var editAnnonceViewModel: EditAnnonceViewModel
    lateinit var date: String
    val M1 = "-01-01"
    lateinit var img1: String
    lateinit var img2: String
    lateinit var img3: String
    lateinit var color: String
    lateinit var descr: String
    var km = 0
    var price = 0
    var carId = ""
    lateinit var title: String


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.edit_annonce_fragment, container, false)


        var args = EditAnnonceFragmentArgs.fromBundle(arguments!!)
        var editAnnonceViewModelFactory = EditAnnonceViewModelFactory(args.annonceId, (activity as MainActivity).getToken())
        Log.i("IDENTIFIER", args.annonceId)
        editAnnonceViewModel = ViewModelProviders.of(this, editAnnonceViewModelFactory).get(EditAnnonceViewModel::class.java)
        editAnnonceViewModel.annonce.observe(this, Observer { an ->

            //  Log.i("3onwanAvant",binding.details.edit_text_title.text.toString())
            Log.i("3onwanApre", an.title)
            carId=an.idVehicule.toString()
            //Info static can not be updated
            date = an.date
            img1 = an.imageVehicle1
            img2 = an.imageVehicle2
            img3 = an.imageVehicle3
            binding.details.textYearValue.text = an.date
            binding.details.versionName.text = an.versionName
            binding.details.modelName.text = an.modelName
            binding.details.marqueName.text = an.marqueName

            //Info to update

            price = an.prix
            descr = an.commentaires
            color = an.color
            km = an.kilometrage
            title = an.title
            binding.details.title.hint = an.title
            binding.details.etDescrp.hint = an.commentaires
            binding.details.etColor.hint = an.color
            binding.details.etKm.hint = an.kilometrage.toString()
            binding.details.etPrice.hint = an.prix.toString()


        })

        lateinit var car: VehiculeUpdate
        lateinit var annonce: AnnonceUpdate
        var annnonceId = EditAnnonceFragmentArgs.fromBundle(arguments!!).annonceId
        val action = EditAnnonceFragmentDirections.actionEditAnnonceFragmentToMesAnnoncesFragment()
        var btnConfirm = binding.root.findViewById<FloatingActionButton>(R.id.btn_confirm_edition)

        btnConfirm.setOnClickListener { v: View ->
            if (binding.details.etKm.text.toString() != "") km = binding.details.etKm.text.toString().toInt()
            if (binding.details.etColor.text.toString() != "") color = binding.details.etColor.text.toString()
            if (binding.details.title.text.toString() != "") title = binding.details.title.text.toString()
            if (binding.details.etDescrp.text.toString() != "") descr = binding.details.etDescrp.text.toString()
            if (binding.details.etPrice.text.toString() != "") price = binding.details.etPrice.text.toString().toInt()

            car = VehiculeUpdate(km, date, color)
            Log.i("UPDATE", "HEERE")

            annonce = AnnonceUpdate(title, price, descr)
            setSliderViews(binding.details.imageSlider, img1)
            // Sending Query
            editAnnonceViewModel.updateAnnonce(annnonceId,carId,car,annonce)

            v.findNavController().navigate(action)
        }

        return binding.root
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
