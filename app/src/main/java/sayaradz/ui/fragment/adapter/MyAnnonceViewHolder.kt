package sayaradz.ui.fragment.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.PopupMenu
import androidx.core.app.NavUtils
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import sayaradz.authentification.R
import sayaradz.dataClasses.Car
import sayaradz.ui.fragment.marque.MarqueFragmentDirections

class MyAnnonceViewHolder private constructor(val layout: View, val context : Context) : RecyclerView.ViewHolder(layout) {
    var announceName: TextView
    var announceImage: ImageView
    var  btnMenu : AppCompatImageButton

    init {
        announceName = itemView.findViewById(R.id.item_name)
        announceImage = itemView.findViewById(R.id.img_item_logo)
         btnMenu = itemView.findViewById(R.id.menu_button)
    }

    companion object {
        fun creat(parent: ViewGroup , context: Context ):MyAnnonceViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.my_annonce_item, parent, false)
            return MyAnnonceViewHolder(itemView,context)
        }
    }

    fun bind(announce: Car) {
        announceName.setText(announce.title)
        handleClick(layout, announce.id.toString())

        Log.i("announce", announce.title)
        //Glide.with(context).load(announce.imageVehicle1).into(this.image)
        val imageUrl = GlideUrl(announce.imageVehicle1, LazyHeaders.Builder()
                .build())
        Glide.with(this.announceImage.context).load(imageUrl).into(this.announceImage)


        btnMenu.setOnClickListener {
            showPopupMenu(btnMenu,announce,context)
        }

    }

    fun showPopupMenu(view: View, item: Car , context: Context) {
        // inflate menu
        val popup = PopupMenu(view.context, view)
        val inflater = popup.menuInflater
        inflater.inflate(R.menu.card_menu, popup.menu)
        popup.setOnMenuItemClickListener(CustomMenuItem(item,context))
        popup.show()
    }

    //TO BE MODIFIED GO TO THE layout ( MY ANNONCE DETAILS + BTN MODIFY)
    private fun handleClick(view: View, marqueId: String) {
        val action = MarqueFragmentDirections.actionMarqueFragmentToModelFragment(marqueId)
        view.setOnClickListener { v: View ->
            v.findNavController().navigate(action)
        }
    }
}