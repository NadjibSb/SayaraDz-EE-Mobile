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
import com.bumptech.glide.request.RequestOptions
import sayaradz.authentification.R
import sayaradz.dataClasses.Marque
import sayaradz.ui.fragment.marque.MarqueFragmentDirections


class MarqueViewHolder private constructor(val layout: View) : RecyclerView.ViewHolder(layout) {
    var marqueName: TextView
    var marqueImage: ImageView

    init {
        marqueName = itemView.findViewById(R.id.item_name)
        marqueImage = itemView.findViewById(R.id.img_item_logo)
    }

    companion object {
        fun creat(parent: ViewGroup): MarqueViewHolder {
            val marqueItemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_item, parent, false)
            return MarqueViewHolder(marqueItemView)
        }
    }

    fun bind(marque: Marque) {
        marqueName.text = marque.name
        if (marque.name.length>14) marqueName.textSize= 16.0F
        handleClick(layout, marque.id)

        val imageUrl = GlideUrl(marque.imageUrl, LazyHeaders.Builder()
                .build())
        Glide.with(this.marqueImage.context)
                .load(imageUrl)
                .apply(RequestOptions().placeholder(R.drawable.ic_image_loading).error(R.drawable.ic_image_error))
                .into(this.marqueImage)
        //marqueImage.setImageResource(R.drawable.m_audi)
    }

    private fun handleClick(view: View, marqueId: String) {
        val action = MarqueFragmentDirections.actionMarqueFragmentToModelFragment(marqueId)
        view.setOnClickListener { v: View ->
            v.findNavController().navigate(action)
        }
    }
}