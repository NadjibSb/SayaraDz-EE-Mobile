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
import sayaradz.authentification.R
import sayaradz.dataClasses.Modele
import sayaradz.ui.fragment.addAnnonce.AddAnnonceFragment
import sayaradz.ui.fragment.addAnnonce.AddAnnonceFragment.Companion.btnAddPhoto
import sayaradz.ui.fragment.addAnnonce.AddAnnonceFragment.Companion.ecvModel
import sayaradz.ui.fragment.addAnnonce.AddAnnonceFragment.Companion.ecvOthers
import sayaradz.ui.fragment.addAnnonce.AddAnnonceFragment.Companion.ecvVersion
import sayaradz.ui.fragment.addAnnonce.AddAnnonceViewModel
import sayaradz.ui.fragment.model.ModelFragmentDirections
import sayaradz.ui.mainActivity.MainActivity

class ModelViewHolder private constructor(val layout: View) : RecyclerView.ViewHolder(layout) , LifecycleOwner {
    override fun getLifecycle(): Lifecycle {
        return AddAnnonceFragment.lifecycleRegistry
    }

    var modelName: TextView
    var modelImage: ImageView

    init {
        modelName = itemView.findViewById(R.id.item_name)
        modelImage = itemView.findViewById(R.id.img_item_logo)
    }

    companion object {
        lateinit var itemViewmodel : View
        fun creat(parent: ViewGroup): ModelViewHolder {
            val modelItemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_item, parent, false)
            itemViewmodel=modelItemView
            return ModelViewHolder(modelItemView)
        }
    }

    fun bind(modele: Modele) {
        modelName.setText(modele.name)
        handleClick(layout, modele.id)
        if(AddAnnonceFragment.active)
       itemViewmodel.setOnClickListener {
           var token =  AddAnnonceFragment.token
           var context = AddAnnonceFragment.contextAdd
           if (modele.id!= null) {
               AddAnnonceFragment.modeleId = modele.id
               Log.i("TEST" , "Correct ID"+ modele.id)
               // ABOUT  THE MODEL ASSOCIATED LIST
               var listVersion= AddAnnonceViewModel.getVersions(token,AddAnnonceFragment.modeleId)
               listVersion.observe(this, Observer { versions ->
                   AddAnnonceFragment.rvVersion.adapter = ListAdapter(versions, ListAdapter.ViewHolderType.VERSION, context,token)
               })
               AddAnnonceFragment.setUpRecycleView(AddAnnonceFragment.rvVersion ,context)

               // ABOUT THE CARD VIEW
             ecvVersion.visibility=View.VISIBLE
               ecvOthers.visibility=View.GONE
               btnAddPhoto.visibility=View.GONE
               ecvModel.collapse()
           } else
               Log.i("TEST" , "NULL ID")
       }




        Log.i("modele", modele.name)
        /*  val imageUrl = GlideUrl(modele.imageUrl, LazyHeaders.Builder()
                .build())
        Glide.with(this.modelImage.context).load(imageUrl).into(this.modelImage)*/
        modelImage.setImageResource(R.drawable.a3_sedan)
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