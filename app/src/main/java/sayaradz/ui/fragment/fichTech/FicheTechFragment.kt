package sayaradz.ui.fragment.fichTech

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
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
import sayaradz.dataClasses.Couleur
import sayaradz.dataClasses.FichTech
import sayaradz.dataClasses.Version
import sayaradz.ui.mainActivity.MainActivity


class FicheTechFragment : Fragment() {

    private val TAG = "TAG-FicheTechFragment"
    private var selectedColor = 0

    companion object {
        fun newInstance() = FicheTechFragment()
    }

    private lateinit var viewModel: FicheTechViewModel
    private lateinit var binding: FicheTechFragmentBinding
    private lateinit var args: FicheTechFragmentArgs


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fiche_tech_fragment, container, false)


        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        args = FicheTechFragmentArgs.fromBundle(arguments!!)
        val factory = FicheTechViewModelFactory(args.versionID, (activity as MainActivity).getToken())
        viewModel = ViewModelProviders.of(this, factory).get(FicheTechViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.progressLayout.visibility = View.VISIBLE
        updateUI()

    }


    private fun updateUI() {
        viewModel.version.observe(this, Observer { version ->

            binding.progressLayout.visibility = View.GONE
            if (version != null) {
                //setup the version images for the slider
                val images = arrayListOf(version.image1, version.image2, version.image3)
                setSliderViews(binding.header.imageSlider, images)

                fillVersionDetails(version)

                Log.i(TAG, "prix (api) : ${version.price}")
                Log.i(TAG, "prix (before init) : ${viewModel.price.value}")
                viewModel.initilizePrice(version.price)
                Log.i(TAG, "prix (after init) : ${viewModel.price.value}")

                viewModel.getFichTech(version.ficheTechnique_id).observe(this, Observer { ficheTech ->
                    //filling all the informations in fiche tech
                    if (ficheTech != null) {
                        fillFichTech(ficheTech)
                    }
                })
                binding.btnCommande.setOnClickListener {
                    onCommande()
                }
                binding.options.colorView.setOnClickListener {
                    fillColors(version.colors)
                }
            }
        })
    }

    private fun fillVersionDetails(version: Version) {

        binding.header.apply {
            marqueName.text = version.marque_name
            modelName.text = version.model_name
            versionName.text = version.name
        }


        val container = binding.options.optionCheckboxContainer
        for (option in version.options) {
            var cb = CheckBox(context)
            cb.text = option.name
            cb.setOnClickListener { view ->
                if ((view as CheckBox).isChecked) {
                    viewModel.updatePrice(option.price, ADD)
                    viewModel.addOption(option.code)
                } else {
                    viewModel.updatePrice(option.price, SUB)
                    viewModel.omitOption(option.code)
                }
                Toast.makeText(context, resources.getString(R.string.price_updated), Toast.LENGTH_SHORT).show()
                Log.i(TAG, "prix (updated) : ${viewModel.price.value} options ${viewModel.options}")
            }
            container.addView(cb)
        }

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

    private fun fillColors(list: List<Couleur>) {
        val colors = arrayListOf<String>()
        val regex = Regex("^#([A-Fa-f0-9]{6})", RegexOption.IGNORE_CASE)
        for (c in list) {
            val hexaCode = "#" + c.code[0] + "0" + c.code[1] + "0" + c.code[2] + "0"
            if (regex.matches(hexaCode)) colors.add(hexaCode)
            else colors.add("#AAAAAA")
        }
        Log.i(TAG, colors.toString())
        if (colors.size > 0) colorPickerDialog(colors)
    }


    private fun colorPickerDialog(colors: ArrayList<String>) {
        if (colors.size>0){
            val colorPicker = ColorPicker(this.activity)
            colorPicker.setOnChooseColorListener(object : ColorPicker.OnChooseColorListener {
                override fun onChooseColor(position: Int, color: Int) {
                    Log.i(TAG, "position $position color $color")
                    binding.options.colorView.setBackgroundColor(Color.parseColor(colors[position]))
                    selectedColor = position
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
    }


    fun onCommande() {
        Log.i(TAG, "Commande clicked")
        binding.progressLayout.visibility = View.VISIBLE
        Handler().postDelayed({
            binding.progressLayout.visibility = View.GONE
            dialogBox("Commande Effectué", "Votre commande de vehicule est bien effectué.")
        }, 4000)
        viewModel.commande(selectedColor)
    }
    private fun dialogBox(title: String, text: String){
        AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(text)
                .setPositiveButton("OK") { dialog, int ->
                    Log.i(TAG,"$dialog $int")
                }
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

        for (url in imgUrls) {
            val sliderView = DefaultSliderView(context)
            sliderView.imageUrl = url
            sliderView.setImageScaleType(ImageView.ScaleType.CENTER_CROP)
            //at last add this view in your layout :
            sliderLayout.addSliderView(sliderView)
        }
    }
}
