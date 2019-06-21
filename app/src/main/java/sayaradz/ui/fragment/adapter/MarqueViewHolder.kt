package sayaradz.ui.fragment.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
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
        marqueName.setText(marque.name)
        handleClick(layout, marque.id)

        Log.i("marque", marque.name)
        /*val imageUrl = GlideUrl(marque.imageUrl, LazyHeaders.Builder()
                .addHeader("Authorization", token)
                .build())
        Glide.with(context).load(imageUrl).into(holder.image)*/
        marqueImage.setImageResource(R.drawable.m_audi)
    }

    private fun handleClick(view: View, marqueId: String) {
        val action = MarqueFragmentDirections.actionMarqueFragmentToModelFragment(marqueId)
        view.setOnClickListener { v: View ->
            v.findNavController().navigate(action)
        }
    }
}