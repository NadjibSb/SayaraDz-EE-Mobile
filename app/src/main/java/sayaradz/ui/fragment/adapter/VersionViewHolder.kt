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
import sayaradz.dataClasses.Version
import sayaradz.ui.fragment.addAnnonce.AddAnnonceFragment
import sayaradz.ui.fragment.addAnnonce.AddAnnonceFragment.Companion.btnAddPhoto
import sayaradz.ui.fragment.addAnnonce.AddAnnonceFragment.Companion.ecvOthers
import sayaradz.ui.fragment.addAnnonce.AddAnnonceFragment.Companion.ecvVersion
import sayaradz.ui.fragment.addAnnonce.AddAnnonceFragment.Companion.versionId
import sayaradz.ui.fragment.version.VersionFragmentDirections

class VersionViewHolder private constructor(val layout: View) : RecyclerView.ViewHolder(layout) {
    var versionName: TextView = itemView.findViewById(R.id.item_name)
    var versionImage: ImageView = itemView.findViewById(R.id.img_item_logo)

    companion object {
        lateinit var itemViewversion: View
        fun creat(parent: ViewGroup): VersionViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_item, parent, false)
            itemViewversion = itemView
            return VersionViewHolder(itemView)
        }
    }

    fun bind(version: Version) {
        versionName.setText(version.name)
        handleClick(layout, version.code)
        if (AddAnnonceFragment.active)
            layout.setOnClickListener {

                versionId = version.name //
                Log.i("TEST", version.id + "" + version.name)
                ecvOthers.visibility = View.VISIBLE
                if (ecvOthers.visibility == View.VISIBLE) {
                    btnAddPhoto.visibility = View.VISIBLE
                }

                ecvVersion.collapse()
            }


        versionName.text = version.name

        val imageUrl = GlideUrl(version.image1, LazyHeaders.Builder()
                .build())
        Glide.with(versionImage.context)
                .load(imageUrl)
                .apply(RequestOptions().placeholder(R.drawable.ic_image_loading).error(R.drawable.ic_image_error))
                .into(versionImage)
        //versionImage.setImageResource(R.drawable.a3_sedan)
    }

    private fun handleClick(view: View, versionId: String) {
        if (!AddAnnonceFragment.active) {
            val action = VersionFragmentDirections.actionVersionFragmentToFicheTechFragment(versionId)
            view.setOnClickListener { v: View ->
                v.findNavController().navigate(action)
            }
        }
    }
}