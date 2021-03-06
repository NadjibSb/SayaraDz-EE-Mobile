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
import sayaradz.dataClasses.Modele
import sayaradz.ui.fragment.addAnnonce.AddAnnonceFragment
import sayaradz.ui.fragment.addAnnonce.AddAnnonceFragment.Companion.btnAddPhoto
import sayaradz.ui.fragment.addAnnonce.AddAnnonceFragment.Companion.ecvModel
import sayaradz.ui.fragment.addAnnonce.AddAnnonceFragment.Companion.ecvOthers
import sayaradz.ui.fragment.addAnnonce.AddAnnonceFragment.Companion.ecvVersion
import sayaradz.ui.fragment.addAnnonce.AddAnnonceViewModel
import sayaradz.ui.fragment.model.ModelFragmentDirections

class ModelViewHolder private constructor(val layout: View) : RecyclerView.ViewHolder(layout), LifecycleOwner {
    override fun getLifecycle(): Lifecycle {
        return AddAnnonceFragment.lifecycleRegistry
    }

    var modelName: TextView = itemView.findViewById(R.id.item_name)
    var modelImage: ImageView = itemView.findViewById(R.id.img_item_logo)

    companion object {
        lateinit var itemViewmodel: View
        fun creat(parent: ViewGroup): ModelViewHolder {
            val modelItemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_item, parent, false)
            itemViewmodel = modelItemView
            return ModelViewHolder(modelItemView)
        }
    }

    fun bind(modele: Modele) {
        modelName.setText(modele.name)
        handleClick(layout, modele.pk)   // they change it not id !!
        if (AddAnnonceFragment.active)
            layout.setOnClickListener {
                var token = AddAnnonceFragment.token
                var context = AddAnnonceFragment.contextAdd
                if (modele.code != null) {
                    AddAnnonceFragment.modeleId = modele.refId  // RefId
                    Log.i("TEST", "Correct ID" + modele.name)
                    // ABOUT  THE MODEL ASSOCIATED LIST
                    var listVersion = AddAnnonceViewModel.getVersions(token, modele.pk)
                    listVersion.observe(this, Observer { versions ->
                        AddAnnonceFragment.rvVersion.adapter = ListAdapter(versions, ListAdapter.ViewHolderType.VERSION, context, token)
                    })
                    AddAnnonceFragment.setUpRecycleView(AddAnnonceFragment.rvVersion, context)

                    // ABOUT THE CARD VIEW
                    ecvVersion.visibility = View.VISIBLE
                    ecvOthers.visibility = View.GONE
                    btnAddPhoto.visibility = View.GONE
                    ecvModel.collapse()
                } else
                    Log.i("TEST", "NULL ID")
            }




        Log.i("modele", modele.name)

        val imageUrl = GlideUrl(modele.imageUrl, LazyHeaders.Builder()
                .build())

        Glide.with(this.modelImage.context)
                .load(imageUrl)
                .apply(RequestOptions().placeholder(R.drawable.ic_image_loading).error(R.drawable.ic_image_error))
                .into(this.modelImage)
        //modelImage.setImageResource(R.drawable.a3_sedan)
    }

    private fun handleClick(view: View, modelId: String) {
        if (!AddAnnonceFragment.active) {
            val action = ModelFragmentDirections.actionModelFragmentToVersionFragment(modelId)
            view.setOnClickListener { v: View ->
                v.findNavController().navigate(action)
            }

        }
    }
}