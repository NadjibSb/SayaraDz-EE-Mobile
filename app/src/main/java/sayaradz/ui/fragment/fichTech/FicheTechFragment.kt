package sayaradz.ui.fragment.fichTech

import android.os.Bundle
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
import androidx.recyclerview.widget.LinearLayoutManager
import com.smarteist.autoimageslider.DefaultSliderView
import com.smarteist.autoimageslider.IndicatorAnimations
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderLayout
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fiche_tech_fragment, container, false)

        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(FicheTechViewModel::class.java)
        binding.viewModel = viewModel

        updateUI()

        val mobileArray = arrayListOf("Bleu", "Rouge", "Noir", "Gris", "Blanc", "bordeaux")
        setUpRecycleView(mobileArray)
    }


    private fun updateUI() {
        viewModel.versionNew.observe(this, Observer { version ->
            //setup the version images for the slider
            setSliderViews(binding.imageSlider, version.imgs)
            fillVersionOptions(version)
        })
        viewModel.fichTech.observe(this, Observer { ficheTech ->
            //filling all the informations in fiche tech
            fillFichTech(ficheTech)
        })
    }

    private fun fillVersionOptions(version: VersionNew) {
        val container = binding.optionCheckboxContainer
        for (option in version.options) {
            var cb = CheckBox(context)
            cb.setText(option.name)
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
        }

    }

    //RecycleView--------------------------------------------
    private fun setUpRecycleView(list: ArrayList<String>) {
        var recyclerView = binding.colorsListView
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

        for (url in imgUrls) {
            val sliderView = DefaultSliderView(context)
            sliderView.imageUrl = url
            sliderView.setImageScaleType(ImageView.ScaleType.CENTER_CROP)
            //at last add this view in your layout :
            sliderLayout.addSliderView(sliderView)
        }

    }

}
