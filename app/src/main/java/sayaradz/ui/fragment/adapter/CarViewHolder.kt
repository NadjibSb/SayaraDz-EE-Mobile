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
import sayaradz.dataClasses.Car
import sayaradz.ui.fragment.marque.MarqueFragmentDirections

class CarViewHolder private constructor(val layout: View) : RecyclerView.ViewHolder(layout) {
    var announceName: TextView
    var announceImage: ImageView

    init {
        announceName = itemView.findViewById(R.id.item_name)
        announceImage = itemView.findViewById(R.id.img_item_logo)
    }

    companion object {
        fun creat(parent: ViewGroup): CarViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_item, parent, false)
            return CarViewHolder(itemView)
        }
    }

    fun bind(announce: Car) {
        announceName.setText(announce.title)
        handleClick(layout, announce.id.toString())

        Log.i("announce", announce.title)
        /*val imageUrl = GlideUrl(marque.imageUrl, LazyHeaders.Builder()
                .addHeader("Authorization", token)
                .build())
        Glide.with(context).load(imageUrl).into(holder.image)*/
        announceImage.setImageResource(R.drawable.m_audi)
    }

    private fun handleClick(view: View, marqueId: String) {
        val action = MarqueFragmentDirections.actionMarqueFragmentToModelFragment(marqueId)
        view.setOnClickListener { v: View ->
            v.findNavController().navigate(action)
        }
    }
}