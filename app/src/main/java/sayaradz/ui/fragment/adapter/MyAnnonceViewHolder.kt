package sayaradz.ui.fragment.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.PopupMenu
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import sayaradz.authentification.R
import sayaradz.dataClasses.Car
import sayaradz.ui.fragment.myAnnonce.MyAnnonceFragmentDirections


class MyAnnonceViewHolder private constructor(val layout: View) : RecyclerView.ViewHolder(layout) {
    var announceName: TextView
    var announceImage: ImageView
    var  btnMenu : AppCompatImageButton

    init {
        announceName = itemView.findViewById(R.id.item_name)
        announceImage = itemView.findViewById(R.id.img_item_logo)
         btnMenu = itemView.findViewById(R.id.menu_button)
    }

    companion object {
        fun creat(parent: ViewGroup):MyAnnonceViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.my_annonce_item, parent, false)
            return MyAnnonceViewHolder(itemView)
        }
    }

    fun bind(announce: Car) {
        announceName.setText(announce.title)
        handleClick(layout, announce.id.toString())

        Log.i("announce", announce.title)
        //Glide.with(context).load(announce.imageVehicle1).into(this.image)
        if (announce.imageVehicle1 != null ) {
            val imageUrl = GlideUrl(announce.imageVehicle1, LazyHeaders.Builder()
                    .build())
            Glide.with(this.announceImage.context).load(imageUrl).into(this.announceImage)
        }


        btnMenu.setOnClickListener {
            showPopupMenu(btnMenu,announce)
        }

    }

    fun showPopupMenu(view: View, item: Car) {
        // inflate menu
        val popup = PopupMenu(view.context, view)
        val inflater = popup.menuInflater
        inflater.inflate(R.menu.card_menu, popup.menu)
        popup.setOnMenuItemClickListener(CustomMenuItem(item,layout))
        popup.show()
    }

    //GO TO THE layout ( MY ANNONCE DETAILS + BTN MODIFY)
    private fun handleClick(view: View, annonceId: String) {
        val action = MyAnnonceFragmentDirections.actionMyAnnonceFragmentToViewAnnonceFragment(annonceId)
       view.setOnClickListener { v: View ->
            v.findNavController().navigate(action)
        }
    }
}