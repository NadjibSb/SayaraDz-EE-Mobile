package sayaradz.ui.fragment.annonce
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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.smarteist.autoimageslider.DefaultSliderView
import com.smarteist.autoimageslider.IndicatorAnimations
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderLayout
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import sayaradz.authentification.R
import sayaradz.authentification.databinding.AnnonceFragmentBinding
import sayaradz.dataClasses.Car
import sayaradz.ui.fragment.adapter.ListAdapter

class AnnonceFragment : Fragment() {


    val TAG = "TAG-AnnonceFragment"
    lateinit var binding: AnnonceFragmentBinding
    private lateinit var annonceViewModel: AnnonceViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.annonce_fragment, container, false)
        var args=AnnonceFragmentArgs.fromBundle(arguments!!)
        var annonceViewModelFactory =  AnnonceViewModelFactory( args.annonceId )
        Log.i("IDENTIFIER",args.annonceId)
        annonceViewModel = ViewModelProviders.of(this,annonceViewModelFactory).get(AnnonceViewModel::class.java)
        annonceViewModel.annonce.observe(this, Observer { an ->
            
           /* Log.i("3onwanAvant",binding.details.title.text.toString())
            Log.i("3onwanApre",an.title)*/
            binding.details.title.text=an.title
             binding.details.textDescrip.text= an.commentaires
            binding.details.textKm.text=an.kilometrage.toString()
            binding.details.textPriceMin.text = an.prix.toString()
            binding.details.textYear.text = an.date
            binding.details.versionName.text=an.versionName
            binding.details.modelName.text=an.modelName
            binding.details.marqueName.text= an.marqueName
           binding.details.textColor.text = an.color
        })
      var prixToSuggest : String? = binding.prixText.text.toString()
    // Check for the offer

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setSliderViews(binding.details.imageSlider)



    }



    private fun setSliderViews(layout: SliderLayout) {

        var sliderLayout = layout
        sliderLayout.setIndicatorAnimation(IndicatorAnimations.THIN_WORM) //set indicator animation by using SliderLayout.Animations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderLayout.setSliderTransformAnimation(SliderAnimations.FADETRANSFORMATION)
        sliderLayout.setScrollTimeInSec(2)

        for (i in 0..3) {

            val sliderView = DefaultSliderView(context)

            when (i) {
                0 -> sliderView.imageUrl = "https://www.autobip.com/storage/photos/new_car_prices/20/peugeot_208_tech_vision_1_6_hdi_92ch_2019-03-04-13-0071373.jpg"
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