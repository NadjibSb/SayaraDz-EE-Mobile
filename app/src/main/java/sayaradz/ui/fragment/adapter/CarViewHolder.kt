package sayaradz.ui.fragment.adapter
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import sayaradz.authentification.R
import sayaradz.dataClasses.Car
import sayaradz.ui.fragment.search.SearchFragment.Companion.mTypeSelected
import sayaradz.ui.fragment.search.SearchFragmentDirections

class CarViewHolder private constructor(val layout: View) : RecyclerView.ViewHolder(layout) {
    var announceName: TextView
    var announceImage: ImageView

    init {
        announceName = itemView.findViewById(R.id.item_name)
        announceImage = itemView.findViewById(R.id.img_item_logo)
    }

    companion object {
        lateinit var carItem : View
        fun creat(parent: ViewGroup): CarViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_item, parent, false)
            carItem=itemView
            return CarViewHolder(itemView)
        }
    }

    fun bind(announce: Car) {
        announceName.setText(announce.title)
        handleClick(layout, announce.id.toString())
        Log.i("announce", announce.title)
        //Glide.with(context).load(announce.imageVehicle1).into(this.image)
        if (announce.imageVehicle1 != null) {
            val imageUrl = GlideUrl(announce.imageVehicle1, LazyHeaders.Builder()
                    .build())
            Glide.with(this.announceImage.context).load(imageUrl).into(this.announceImage)
        }
    }

    //TO BE MODIFIED GO TO THE layout ( ANNONCE DETAILS + faire un offre )
    private fun handleClick(view: View, annonceId: String) {
        Log.i("CAR","OnClick")
        val actionOccas = SearchFragmentDirections.actionSearchFragmentToAnnonceFragment(annonceId)
      val actionNeuf =SearchFragmentDirections.actionSearchFragmentToFichTechFragment()
                view.setOnClickListener { v: View ->
                    if (mTypeSelected == "occasion") {
                        Log.i("CAR","Occasion")
                         v.findNavController().navigate(actionOccas)

                    } else {

                        v.findNavController().navigate(actionNeuf)
                        Log.i("CAR","Neuf")
                    }

                }
    }
}