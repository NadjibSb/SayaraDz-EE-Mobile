package sayaradz.ui

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import sayaradz.dataClasses.Marque
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.load.model.GlideUrl
import sayaradz.authentification.R


class MarqueAdapter(val marques: List<Marque>, val context: Context, val token: String)
    : RecyclerView.Adapter<MarqueAdapter.MarqueViewHolder>() {

    private var mMarques: List<Marque>

    init {
        mMarques = marques
    }


    class MarqueViewHolder(val layout: View) : RecyclerView.ViewHolder(layout) {
        var marqueName: TextView
        var marqueImage: ImageView

        init {
            marqueName = itemView.findViewById(R.id.marqueName)
            marqueImage = itemView.findViewById(R.id.logoImg)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarqueViewHolder {
        val marqueItemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.marque_list_item, parent, false)
        return MarqueViewHolder(marqueItemView)
    }


    override fun onBindViewHolder(holder: MarqueViewHolder, position: Int) {
        var marque = mMarques[position]
        holder.marqueName.setText(marque.NomMarque)
        handleClick(holder.layout,marque.IdMarque)

        Log.i("marque", marque.NomMarque)
        /*val imageUrl = GlideUrl(marque.Image, LazyHeaders.Builder()
                .addHeader("Authorization", token)
                .build())
        Glide.with(context).load(imageUrl).into(holder.image)*/
        holder.marqueImage.setImageResource(R.drawable.m_audi)
    }

    private fun handleClick(view: View, marqueId: String){
        val action = MarqueFragmentDirections.actionMarqueFragmentToModelFragment(marqueId)
        view.setOnClickListener {v:View ->
            v.findNavController().navigate(action)
        }
    }

    override fun getItemCount() = mMarques.size
}