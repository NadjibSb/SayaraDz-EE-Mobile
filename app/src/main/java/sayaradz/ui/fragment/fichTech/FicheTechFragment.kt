package sayaradz.ui.fragment.fichTech

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.smarteist.autoimageslider.DefaultSliderView
import com.smarteist.autoimageslider.IndicatorAnimations
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderLayout
import petrov.kristiyan.colorpicker.ColorPicker
import sayaradz.authentification.R
import sayaradz.authentification.databinding.FicheTechFragmentBinding
import sayaradz.dataClasses.FichTech
import sayaradz.dataClasses.Version
import sayaradz.ui.mainActivity.MainActivity


class FicheTechFragment : Fragment() {

    private val TAG = "FicheTechFragment"

    companion object {
        fun newInstance() = FicheTechFragment()
    }

    private lateinit var viewModel: FicheTechViewModel
    private lateinit var binding: FicheTechFragmentBinding
    private lateinit var args: FicheTechFragmentArgs


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fiche_tech_fragment, container, false)

        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        args = FicheTechFragmentArgs.fromBundle(arguments!!)
        val factory = FicheTechViewModelFactory(args.versionID, (activity as MainActivity).getToken())
        viewModel = ViewModelProviders.of(this, factory).get(FicheTechViewModel::class.java)
        updateUI()

    }


    private fun updateUI() {
        viewModel.version.observe(this, Observer { version ->

            if (version != null) {
                //setup the version images for the slider
                val images = arrayListOf(version.image1, version.image2, version.image3)
                setSliderViews(binding.header.imageSlider, images)

                fillVersionDetails(version)

                Log.i(TAG, "prix : ${version.price}")
                viewModel.initilizePrice(version.price)

                viewModel.getFichTech(version.ficheTechnique_id).observe(this, Observer { ficheTech ->
                    //filling all the informations in fiche tech
                    if (ficheTech != null) {
                        fillFichTech(ficheTech)
                    }
                })
            }
        })

        binding.btnCommande.setOnClickListener {
            onCommande()
        }

        binding.options.colorView.setOnClickListener {
            colorPickerDialog()
        }
    }

    private fun fillVersionDetails(version: Version) {

        binding.header.apply {
            marqueName.text = version.marque_name
            modelName.text = version.model_name
            versionName.text = version.name
        }

        /*
        val container = binding.optionCheckboxContainer
        for (option in version.options) {
            var cb = CheckBox(context)
            cb.text = option.name
            cb.setOnClickListener { view ->
                if ((view as CheckBox).isChecked)
                    viewModel.updatePrice(option.price, ADD)
                else
                    viewModel.updatePrice(option.price, SUB)
                Toast.makeText(context, resources.getString(R.string.price_updated), Toast.LENGTH_SHORT).show()
            }
            container.addView(cb)
        }*/
    }

    private fun fillFichTech(ficheTech: FichTech) {

        binding.core.apply {
            motorisation.text = ficheTech?.motorisation
            nombrePortes.text = ficheTech?.nombrePortes
            boiteVitesse.text = ficheTech?.boiteVitesse
            puissanceFiscale.text = ficheTech?.puissanceFiscale
            consommation.text = ficheTech?.consommation
            dimensions.text = ficheTech?.dimensions
            transmission.text = ficheTech?.transmission
            vitesseMax.text = ficheTech?.vitesseMax
            acceleration.text = ficheTech?.acceleration
            reservoir.text = ficheTech?.capaciteReservoir
        }
    }

    fun onCommande() {
        Log.i(TAG, "Commande clicked")
        viewModel.updatePrice(100, ADD)

        Log.i(TAG, " price ${viewModel.price.value}")
    }

    private fun colorPickerDialog() {

        var colors = arrayListOf("#A05250", "#0A5250", "#00A255", "#235525")

        val colorPicker = ColorPicker(this.activity)
        colorPicker.setOnChooseColorListener(object : ColorPicker.OnChooseColorListener {
            override fun onChooseColor(position: Int, color: Int) {
                Log.i(TAG, "position $position color $color")
                binding.options.colorView.setBackgroundColor(Color.parseColor(colors[position]))
            }

            override fun onCancel() {
                // put code
            }
        })
                .setColumns(5)
                .setTitle(getString(R.string.choose_color))
                .setRoundColorButton(true)
                .setColors(colors)
                .setDefaultColorButton(Color.parseColor(colors[0]))
                .show()
    }


    private fun setSliderViews(layout: SliderLayout, imgUrls: List<String>) {

        var sliderLayout = layout
        sliderLayout.setIndicatorAnimation(IndicatorAnimations.THIN_WORM) //set indicator animation by using SliderLayout.Animations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderLayout.setSliderTransformAnimation(SliderAnimations.FADETRANSFORMATION)
        sliderLayout.scrollTimeInSec = 2


        val default = listOf(
                "https://www.autobip.com/storage/photos/new_car_prices/20/peugeot_208_tech_vision_1_6_hdi_92ch_2019-03-04-13-0071373.jpg",
                "https://www.autobip.com/storage/photos/new_car_prices/20/peugeot_208_tech_vision_1_6_hdi_92ch_2019-03-04-13-2892232.jpg",
                "https://www.autobip.com/storage/photos/new_car_prices/20/peugeot_208_tech_vision_1_6_hdi_92ch_2019-03-04-13-0687668.jpg"
        )

        for (url in default) {
            val sliderView = DefaultSliderView(context)
            sliderView.imageUrl = url
            sliderView.setImageScaleType(ImageView.ScaleType.CENTER_CROP)
            //at last add this view in your layout :
            sliderLayout.addSliderView(sliderView)
        }

    }

}
