package sayaradz.ui.fragment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import sayaradz.authentification.R
import sayaradz.convertLongToDateString
import sayaradz.dataClasses.Offer
import sayaradz.inflateImageFromUrl
import sayaradz.ui.fragment.version.VersionFragmentDirections

class OfferViewHolder private constructor(val layout: View) : RecyclerView.ViewHolder(layout) {
    private var title: TextView = itemView.findViewById(R.id.annonce_title)
    private var date: TextView = itemView.findViewById(R.id.annonce_date)
    private var amount: TextView = itemView.findViewById(R.id.annonce_offer)
    private var annonceImage: ImageView = itemView.findViewById(R.id.annonce_img)

    companion object {
        fun creat(parent: ViewGroup): OfferViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.my_offers_list_item, parent, false)
            return OfferViewHolder(itemView)
        }
    }

    fun bind(offer: Offer) {
        title.text = offer.title
        date.text = convertLongToDateString(offer.date)
        amount.text = offer.amount.toString() + " DA"
        inflateImageFromUrl(
                offer.img,
                annonceImage,
                R.drawable.ic_image,
                R.drawable.ic_image,
                "")
    }


    private fun handleClick(view: View) {
        val action = VersionFragmentDirections.actionVersionFragmentToFicheTechFragment()
        view.setOnClickListener { v: View ->
            v.findNavController().navigate(action)
        }
    }
}