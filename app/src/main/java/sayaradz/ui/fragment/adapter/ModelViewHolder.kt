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
import sayaradz.dataClasses.Modele
import sayaradz.ui.fragment.model.ModelFragmentDirections
import sayaradz.ui.mainActivity.MainActivity

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
        modelImage.setImageResource(R.drawable.a3_sedan)
    }

    private fun handleClick(view: View, modelId: String) {
        if (MainActivity.active) {
            val action = ModelFragmentDirections.actionModelFragmentToVersionFragment(modelId)
            view.setOnClickListener { v: View ->
                v.findNavController().navigate(action)
            }
        } else {


        }
    }
}