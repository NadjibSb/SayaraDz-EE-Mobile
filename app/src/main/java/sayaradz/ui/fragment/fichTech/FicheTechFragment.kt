package sayaradz.ui.fragment.fichTech

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.smarteist.autoimageslider.DefaultSliderView
import com.smarteist.autoimageslider.IndicatorAnimations
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderLayout
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import sayaradz.authentification.R
import sayaradz.authentification.databinding.FicheTechFragmentBinding
import sayaradz.dataClasses.Marque
import sayaradz.ui.fragment.adapter.ColorAdapter
import sayaradz.ui.fragment.adapter.ListAdapter


class FicheTechFragment : Fragment() {

    companion object {
        fun newInstance() = FicheTechFragment()
    }

    private lateinit var viewModel: FicheTechViewModel
    private lateinit var binding: FicheTechFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fiche_tech_fragment, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(FicheTechViewModel::class.java)
        setSliderViews(binding.imageSlider)


        val mobileArray = arrayListOf("#A14AAF", "#2BBB19", "#123456", "B42381", "F65F13")
        setUpRecycleView(mobileArray)

    }
    //RecycleView--------------------------------------------
    private fun setUpRecycleView(list: ArrayList<String>) {
        var recyclerView = binding.colorsListView
        recyclerView.adapter = ColorAdapter(this.context!!,list)
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
        recyclerView.itemAnimator = SlideInUpAnimator()
        recyclerView.setHasFixedSize(true)
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
