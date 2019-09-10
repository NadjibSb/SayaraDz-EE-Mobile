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
import sayaradz.dataClasses.Version
import sayaradz.ui.fragment.addAnnonce.AddAnnonceFragment
import sayaradz.ui.fragment.addAnnonce.AddAnnonceFragment.Companion.btnAddPhoto
import sayaradz.ui.fragment.addAnnonce.AddAnnonceFragment.Companion.ecvOthers
import sayaradz.ui.fragment.addAnnonce.AddAnnonceFragment.Companion.ecvVersion
import sayaradz.ui.fragment.addAnnonce.AddAnnonceFragment.Companion.versionId
import sayaradz.ui.fragment.version.VersionFragmentDirections
import sayaradz.ui.mainActivity.MainActivity

class VersionViewHolder private constructor(val layout: View) : RecyclerView.ViewHolder(layout) {
    var versionName: TextView
    var versionImage: ImageView

    init {
        versionName = itemView.findViewById(R.id.item_name)
        versionImage = itemView.findViewById(R.id.img_item_logo)
    }

    companion object {
        lateinit var itemViewversion : View
        fun creat(parent: ViewGroup): VersionViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_item, parent, false)
            itemViewversion=itemView
            return VersionViewHolder(itemView)
        }
    }

    fun bind(version: Version) {
        versionName.setText(version.name)
        handleClick(layout, version.id)
      if (AddAnnonceFragment.active)
          layout.setOnClickListener {

              versionId=version.name //
              Log.i("TEST", version.id + ""+version.name)
              ecvOthers.visibility=View.VISIBLE
              if (ecvOthers.visibility == View.VISIBLE)
              {
                  btnAddPhoto.visibility=View.VISIBLE
              }

              ecvVersion.collapse()
          }


        /*val imageUrl = GlideUrl(marque.imageUrl, LazyHeaders.Builder()
                .addHeader("Authorization", token)
                .build())
        Glide.with(context).load(imageUrl).into(holder.image)*/
        versionImage.setImageResource(R.drawable.a3_sedan)
    }

    private fun handleClick(view: View, versionId: String) {
        if (!AddAnnonceFragment.active) {
            val action = VersionFragmentDirections.actionVersionFragmentToFicheTechFragment()
            view.setOnClickListener { v: View ->
                v.findNavController().navigate(action)
            }
        }
    }
}