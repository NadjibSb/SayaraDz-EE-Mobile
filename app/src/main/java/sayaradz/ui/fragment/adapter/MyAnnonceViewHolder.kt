package sayaradz.ui.fragment.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatImageButton
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import sayaradz.authentification.R
import sayaradz.dataClasses.Car
import sayaradz.ui.fragment.myAnnonce.MyAnnonceFragment
import sayaradz.ui.fragment.myAnnonce.MyAnnonceFragmentDirections
import sayaradz.ui.fragment.myAnnonce.MyAnnonceViewModel


class MyAnnonceViewHolder private constructor(val layout: View, context: Context) : RecyclerView.ViewHolder(layout) {
    var announceName: TextView
    var announceImage: ImageView
    var btnEdit: AppCompatImageButton
    var btnDelete: AppCompatImageButton
    var context = context
    var token = MyAnnonceViewModel.token

    init {
        announceName = itemView.findViewById(R.id.item_name)
        announceImage = itemView.findViewById(R.id.img_item_logo)
        btnEdit = itemView.findViewById(R.id.edit_button)
        btnDelete = itemView.findViewById(R.id.delete_button)
    }

    companion object {
        fun creat(parent: ViewGroup, context: Context): MyAnnonceViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.my_annonce_item, parent, false)
            return MyAnnonceViewHolder(itemView, context)
        }
    }

    fun bind(announce: Car) {
        announceName.setText(announce.title)
        handleClick(layout, announce.id.toString())

        Log.i("announce", announce.title)
        //Glide.with(context).load(announce.imageVehicle1).into(this.image)
        if (announce.imageVehicle1 != null) {
            val imageUrl = GlideUrl(announce.imageVehicle1, LazyHeaders.Builder()
                    .build())
            Glide.with(this.announceImage.context).load(imageUrl).into(this.announceImage)
        }


        btnEdit.setOnClickListener { v: View ->
            val action = MyAnnonceFragmentDirections.actionMyAnnonceFragmentToEditAnnonceFragment(announce.id.toString())
            Log.i("MENU_ITEM", announce.id.toString())
            v.findNavController().navigate(action)
        }
        btnDelete.setOnClickListener {

            //Show Dialog to confirm
            showDialog(layout, context, announce.id.toString())
        }

    }


    //GO TO THE layout ( MY ANNONCE DETAILS + BTN MODIFY)
    private fun handleClick(view: View, annonceId: String) {
        val action = MyAnnonceFragmentDirections.actionMyAnnonceFragmentToViewAnnonceFragment(annonceId)
        view.setOnClickListener { v: View ->
            v.findNavController().navigate(action)
        }
    }

    private fun showDialog(rootView: View, c: Context, annonceId: String) {
        val builder = AlertDialog.Builder(c)

        // Set the alert dialog title
        builder.setTitle("Supression Annonce")

        // Display a message on alert dialog
        builder.setMessage("Voulez vraiment supprimer cette annonce?")

        // Set a positive button and its click listener on alert dialog
        builder.setPositiveButton("Oui") { dialog, which ->
            // Do something when user press the positive button
            Toast.makeText(c, "Ok, Annonce deleted", Toast.LENGTH_SHORT).show()

            // Send the query of delete and display the new list

            //MyAnnonceViewModel.newAnnonces=
            MyAnnonceViewModel.fill(MyAnnonceViewModel.getNewAnnonces(token, annonceId), MyAnnonceFragment.contextMyAn, token)


        }


        // Display a negative button on alert dialog
        builder.setNegativeButton("Non") { dialog, which ->
            Toast.makeText(c, "You are not agree.", Toast.LENGTH_SHORT).show()
        }


        // Display a neutral button on alert dialog
        builder.setNeutralButton("Annuler") { _, _ ->
            Toast.makeText(c, "You cancelled the dialog.", Toast.LENGTH_SHORT).show()
        }

        // Finally, make the alert dialog using builder
        val dialog: AlertDialog = builder.create()

        // Display the alert dialog on app interface
        dialog.show()
    }
    /**NOT USED**/

    /* fun showPopupMenu(view: View, item: Car) {
          // inflate menu
          val popup = PopupMenu(view.context, view)
          val inflater = popup.menuInflater
          inflater.inflate(R.menu.card_menu, popup.menu)
          popup.setOnMenuItemClickListener(CustomMenuItem(item,layout))
          popup.show()
      }*/

}