package sayaradz.ui.fragment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import sayaradz.authentification.R
import sayaradz.convertLongToDateString
import sayaradz.dataClasses.OfferToGet
import sayaradz.dataClasses.OfferToPost
import sayaradz.inflateImageFromUrl

class OfferViewHolder private constructor(val layout: View) : RecyclerView.ViewHolder(layout) {
    private var amount: TextView = itemView.findViewById(R.id.offer_price)
    private var annonceImage: ImageView = itemView.findViewById(R.id.annonce_img)

    companion object {
        fun creat(parent: ViewGroup): OfferViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.my_offers_list_item, parent, false)
            return OfferViewHolder(itemView)
        }
    }

    fun bind(offer: OfferToGet) {

        amount.text = offer.prix.toString() + "millions"
        annonceImage.setImageResource(R.drawable.ic_tag)

    }


    private fun handleClick(view: View) {
        /*val action = VersionFragmentDirections.actionVersionFragmentToFicheTechFragment()
        view.setOnClickListener { v: View ->
            v.findNavController().navigate(action)
        }*/
    }
}