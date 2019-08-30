package sayaradz.ui.fragment.fichTech

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.smarteist.autoimageslider.DefaultSliderView
import com.smarteist.autoimageslider.IndicatorAnimations
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderLayout
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import kotlinx.android.synthetic.main.fiche_tech_header.view.*
import kotlinx.android.synthetic.main.fiche_tech_options.view.*
import sayaradz.authentification.R
import sayaradz.authentification.databinding.FicheTechFragmentBinding
import sayaradz.dataClasses.FichTech
import sayaradz.dataClasses.VersionNew
import sayaradz.ui.fragment.adapter.ColorAdapter


class FicheTechFragment : Fragment() {

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
        Toast.makeText(context,"version id ${args.versionID}",Toast.LENGTH_SHORT)
        val factory = FicheTechViewModelFactory(args.versionID)
        viewModel = ViewModelProviders.of(this,factory).get(FicheTechViewModel::class.java)
        updateUI()

    }


    private fun updateUI() {
        viewModel.versionNew.observe(this, Observer { version ->
            //setup the version images for the slider
            if (version != null){
                setSliderViews(binding.header.imageSlider, version.imgs)
                //fillVersionOptions(version)
            }
        })
        viewModel.fichTech.observe(this, Observer { ficheTech ->
            //filling all the informations in fiche tech
            if (ficheTech != null){
                fillFichTech(ficheTech)
            }
        })

        //link colors
        val mobileArray = arrayListOf("Bleu", "Rouge", "Noir", "Gris", "Blanc", "bordeaux")
        setUpRecycleView(mobileArray)
    }

    private fun fillVersionOptions(version: VersionNew) {
        val container = LinearLayout(context) //= binding.optionCheckboxContainer
        for (option in version.options) {
            var cb = CheckBox(context)
            cb.text = option.name
            cb.setOnClickListener { view ->
                if ((view as CheckBox).isChecked)
                    viewModel.updatePrice(option.tarif, ADD)
                else
                    viewModel.updatePrice(option.tarif, SUB)
                Toast.makeText(context, resources.getString(R.string.price_updated), Toast.LENGTH_SHORT).show()
            }
            container.addView(cb)
        }
    }

    private fun fillFichTech(ficheTech: FichTech) {
        /*
        binding.apply {
            marqueName.text = ficheTech.marque
            modelName.text = ficheTech.modele
            versionName.text = ficheTech.version
            description.text = ficheTech.description
            motorisation.text = ficheTech.motorisation
            boiteVitesse.text = ficheTech.boiteVitesse
            transmission.text = ficheTech.transmission
            puissance.text = ficheTech.puissance
            consomation.text = ficheTech.consomation
            reservoir.text = ficheTech.reservoir
            vitesseMax.text = ficheTech.vitesseMax
            acceleration.text = ficheTech.acceleration
            dimmension.text = ficheTech.dimmension
            nbrPlaces.text = ficheTech.nbrPlaces
            nbrPortes.text = ficheTech.nbrPortes
        }*/

        //link data
        viewModel.fichTech.observe(viewLifecycleOwner, Observer { f ->
            binding.core.apply {
                motorisation.text = f?.motorisation
                nombrePortes.text = f?.nombrePortes
                boiteVitesse.text = f?.boiteVitesse
                puissanceFiscale.text = f?.puissanceFiscale
                consommation.text = f?.consommation
                dimensions.text = f?.dimensions
                transmission.text = f?.transmission
                vitesseMax.text = f?.vitesseMax
                acceleration.text = f?.acceleration
                reservoir.text = f?.capaciteReservoir
            }
        })
    }


    //RecycleView--------------------------------------------
    private fun setUpRecycleView(list: ArrayList<String>) {
        var recyclerView = binding.options.colorsListView
        recyclerView.adapter = ColorAdapter(this.context!!, list)
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.itemAnimator = SlideInUpAnimator()
        recyclerView.setHasFixedSize(true)
    }


    private fun setSliderViews(layout: SliderLayout, imgUrls: List<String>) {

        var sliderLayout = layout
        sliderLayout.setIndicatorAnimation(IndicatorAnimations.THIN_WORM) //set indicator animation by using SliderLayout.Animations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderLayout.setSliderTransformAnimation(SliderAnimations.FADETRANSFORMATION)
        sliderLayout.setScrollTimeInSec(2)


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
