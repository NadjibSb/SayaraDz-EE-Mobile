package sayaradz.ui.fragment.adapter

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
import sayaradz.ui.fragment.version.VersionFragmentDirections

class VersionViewHolder private constructor(val layout: View) : RecyclerView.ViewHolder(layout) {
    var versionName: TextView = itemView.findViewById(R.id.item_name)
    var versionImage: ImageView = itemView.findViewById(R.id.img_item_logo)

    companion object {
        fun creat(parent: ViewGroup): VersionViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_item, parent, false)
            return VersionViewHolder(itemView)
        }
    }

    fun bind(version: Version) {
        versionName.text = version.name
        handleClick(layout, version.id)

        val imageUrl = GlideUrl(version.imageUrl, LazyHeaders.Builder()
                .build())
        Glide.with(versionImage.context)
                .load(imageUrl)
                .apply(RequestOptions().placeholder(R.drawable.ic_image_loading).error(R.drawable.ic_image_error))
                .into(versionImage)
        //versionImage.setImageResource(R.drawable.a3_sedan)
    }

    private fun handleClick(view: View, versionID: String) {
        val action = VersionFragmentDirections.actionVersionFragmentToFicheTechFragment(versionID)
        view.setOnClickListener { v: View ->
            v.findNavController().navigate(action)
        }
    }
}