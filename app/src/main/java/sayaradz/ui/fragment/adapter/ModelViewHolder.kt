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
import sayaradz.dataClasses.Modele
import sayaradz.ui.fragment.model.ModelFragmentDirections

class ModelViewHolder private constructor(val layout: View) : RecyclerView.ViewHolder(layout) {
    var modelName: TextView
    var modelImage: ImageView

    init {
        modelName = itemView.findViewById(R.id.item_name)
        modelImage = itemView.findViewById(R.id.img_item_logo)
    }

    companion object {
        fun creat(parent: ViewGroup): ModelViewHolder {
            val marqueItemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_item, parent, false)
            return ModelViewHolder(marqueItemView)
        }
    }

    fun bind(modele: Modele) {
        modelName.setText(modele.name)
        handleClick(layout, modele.id)

        Log.i("modele", modele.name)
      /*  val imageUrl = GlideUrl(modele.imageUrl, LazyHeaders.Builder()
                .build())
        Glide.with(this.modelImage.context).load(imageUrl).into(this.modelImage)*/
        modelImage.setImageResource(R.drawable.m_audi)
    }

    private fun handleClick(view: View, modelId: String) {
        val action = ModelFragmentDirections.actionModelFragmentToVersionFragment(modelId)
        view.setOnClickListener { v: View ->
            v.findNavController().navigate(action)
        }
    }
}