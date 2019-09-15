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
    var announceName: TextView = itemView.findViewById(R.id.item_name)
    var announceImage: ImageView = itemView.findViewById(R.id.img_item_logo)

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
        announceName.text = announce.title
        handleClick(layout, announce.id.toString(),announce.versionId.toString())
        Log.i("announce", announce.title)
        //Glide.with(context).load(announce.imageVehicle1).into(this.image)
        if (announce.imageVehicle1 != null) {
            val imageUrl = GlideUrl(announce.imageVehicle1, LazyHeaders.Builder()
                    .build())
            Glide.with(this.announceImage.context).load(imageUrl).into(this.announceImage)
        }
    }

    //TO BE MODIFIED GO TO THE layout ( ANNONCE DETAILS + faire un offre )
    private fun handleClick(view: View, annonceId: String? , versionId : String?) {
        Log.i("CAR","OnClick")
        lateinit var actionOccas : SearchFragmentDirections.ActionSearchFragmentToAnnonceFragment
        lateinit var actionNeuf : SearchFragmentDirections.ActionSearchFragmentToFichTechFragment
        if (!annonceId.isNullOrEmpty())  actionOccas=SearchFragmentDirections.actionSearchFragmentToAnnonceFragment(annonceId)
        if (!versionId.isNullOrEmpty()) actionNeuf= SearchFragmentDirections.actionSearchFragmentToFichTechFragment(versionId)
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