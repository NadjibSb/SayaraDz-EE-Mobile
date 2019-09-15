package sayaradz.ui.fragment.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.request.RequestOptions
import sayaradz.authentification.R
import sayaradz.dataClasses.Marque
import sayaradz.ui.fragment.addAnnonce.AddAnnonceFragment
import sayaradz.ui.fragment.addAnnonce.AddAnnonceFragment.Companion.btnAddPhoto
import sayaradz.ui.fragment.addAnnonce.AddAnnonceFragment.Companion.ecvMarque
import sayaradz.ui.fragment.addAnnonce.AddAnnonceFragment.Companion.ecvModel
import sayaradz.ui.fragment.addAnnonce.AddAnnonceFragment.Companion.ecvOthers
import sayaradz.ui.fragment.addAnnonce.AddAnnonceFragment.Companion.ecvVersion
import sayaradz.ui.fragment.addAnnonce.AddAnnonceViewModel
import sayaradz.ui.fragment.marque.MarqueFragmentDirections


class MarqueViewHolder private constructor(val layout: View) : RecyclerView.ViewHolder(layout), LifecycleOwner {
    var marqueName: TextView = itemView.findViewById(R.id.item_name)
    var marqueImage: ImageView = itemView.findViewById(R.id.img_item_logo)

    override fun getLifecycle(): Lifecycle {
        return AddAnnonceFragment.lifecycleRegistry
    }

    companion object {
        lateinit var itemViewmarque: View
        fun creat(parent: ViewGroup): MarqueViewHolder {
            val marqueItemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_item, parent, false)
            itemViewmarque = marqueItemView
            return MarqueViewHolder(marqueItemView)

        }
    }

    fun bind(marque: Marque) {
        marqueName.text = marque.name
        if (marque.name.length > 18) marqueName.textSize = 14.0F

        if (!AddAnnonceFragment.active) handleClick(layout, marque.id)
        if (AddAnnonceFragment.active)

            layout.setOnClickListener {
                Log.i("TEST", "Correct ID" + marque.name)
                var token = AddAnnonceFragment.token
                var context = AddAnnonceFragment.contextAdd
                if (marque.id != null) {
                    AddAnnonceFragment.marqueId = marque.id
                    Log.i("TESTMARQUE", "Correct ID" + marque.id)
                    // ABOUT  THE MODEL ASSOCIATED LIST
                    var listModel = AddAnnonceViewModel.getModels(token, marque.id)
                    listModel.observe(this, Observer { models ->
                        AddAnnonceFragment.rvModel.adapter = ListAdapter(models, ListAdapter.ViewHolderType.MODEL, context, token)
                    })
                    AddAnnonceFragment.setUpRecycleView(AddAnnonceFragment.rvModel, context)

                    // ABOUT THE CARD VIEW
                    ecvModel.visibility = View.VISIBLE
                    ecvVersion.visibility = View.GONE
                    ecvOthers.visibility = View.GONE
                    btnAddPhoto.visibility = View.GONE
                    ecvMarque.collapse()

                } else
                    Log.i("TEST", "NULL ID")
            }

        Log.i("marque", marque.name)
        val imageUrl = GlideUrl(marque.imageUrl, LazyHeaders.Builder()
                .build())
        Glide.with(this.marqueImage.context)
                .load(imageUrl)
                .apply(RequestOptions().placeholder(R.drawable.ic_image_loading).error(R.drawable.ic_image_error))
                .into(this.marqueImage)
    }

    private fun handleClick(view: View, marqueId: String) {
        if (!AddAnnonceFragment.active) {
            val action = MarqueFragmentDirections.actionMarqueFragmentToModelFragment(marqueId)
            view.setOnClickListener { v: View ->
                v.findNavController().navigate(action)
            }
        }
    }
}



