package sayaradz.ui.fragment.myAnnonceView

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
import androidx.navigation.fragment.navArgs
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.smarteist.autoimageslider.DefaultSliderView
import com.smarteist.autoimageslider.IndicatorAnimations
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderLayout
import kotlinx.android.synthetic.main.my_annonce_view_fragment.*
import sayaradz.authentification.R
import sayaradz.authentification.databinding.MyAnnonceFragmentBinding
import sayaradz.authentification.databinding.MyAnnonceViewFragmentBinding
import sayaradz.ui.fragment.myAnnonce.MyAnnonceViewModel
import sayaradz.ui.mainActivity.MainActivity

class MyAnnonceViewFragment : Fragment () {



    val TAG = "TAG-MyAnnonceViewFragment"
    private lateinit var binding: MyAnnonceViewFragmentBinding
    lateinit var myAnnonceViewViewModel: MyAnnonceViewViewModel
    var annonceId=""
    var img =""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.my_annonce_view_fragment, container, false)
        var args=MyAnnonceViewFragmentArgs.fromBundle(arguments!!)
        var myannonceViewViewModelFactory =  MyAnnonceViewViewModelFactory( args.annonceId ,(activity as MainActivity).getToken() )
        Log.i("IDENTIFIER",args.annonceId)
        myAnnonceViewViewModel = ViewModelProviders.of(this,myannonceViewViewModelFactory).get(MyAnnonceViewViewModel::class.java)
        myAnnonceViewViewModel.annonce.observe(this, Observer { an ->
            Log.i("3onwanAvant",binding.details.title.text.toString())
            Log.i("3onwanApre",an.title)
            binding.details.title.text=an.title
            binding.details.title.text=an.title
            binding.details.textDescrip.text= an.commentaires
            binding.details.textKm.text=an.kilometrage.toString()
            binding.details.textPriceMin.text = an.prix.toString()
            binding.details.textYear.text = an.date
            binding.details.versionName.text=an.versionName
            binding.details.modelName.text=an.modelName
            binding.details.marqueName.text= an.marqueName
            binding.details.textColor.text = an.color
             img=an.imageVehicle1


        })
        setSliderViews(binding.details.imageSlider,img)

        //val args:MyAnnonceViewFragmentArgs by navArgs()
        //annonceId = args.annonceId
        var btnEdit = binding.root.findViewById<FloatingActionButton>(R.id.btn_edit)
        val action = MyAnnonceViewFragmentDirections.actionViewAnnonceToEditAnnonceFragment(args.annonceId)
        btnEdit.setOnClickListener {
            v: View ->
            v.findNavController().navigate(action)
        }
        return binding.root
    }
    private fun setSliderViews(layout: SliderLayout , imageUrl : String) {

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

