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
import sayaradz.dataClasses.Modele
import sayaradz.ui.fragment.model.ModelFragmentDirections

class ModelViewHolder private constructor(val layout: View) : RecyclerView.ViewHolder(layout) {
    var modelName: TextView = itemView.findViewById(R.id.item_name)
    var modelImage: ImageView = itemView.findViewById(R.id.img_item_logo)

    companion object {
        fun creat(parent: ViewGroup): ModelViewHolder {
            val marqueItemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_item, parent, false)
            return ModelViewHolder(marqueItemView)
        }
    }

    fun bind(modele: Modele) {
        modelName.setText(modele.name)
        handleClick(layout, modele.code)

        val imageUrl = GlideUrl(modele.imageUrl, LazyHeaders.Builder()
                .build())
        Glide.with(this.modelImage.context)
                .load(imageUrl)
                .apply(RequestOptions().placeholder(R.drawable.ic_image_loading).error(R.drawable.ic_image_error))
                .into(this.modelImage)
        //modelImage.setImageResource(R.drawable.a3_sedan)
    }

    private fun handleClick(view: View, modelId: String) {
        val action = ModelFragmentDirections.actionModelFragmentToVersionFragment(modelId)
        view.setOnClickListener { v: View ->
            v.findNavController().navigate(action)
        }
    }
}