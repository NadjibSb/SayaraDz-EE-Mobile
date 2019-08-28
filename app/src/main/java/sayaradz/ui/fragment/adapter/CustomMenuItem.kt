package sayaradz.ui.fragment.adapter


import android.content.Context
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.PopupMenu
import androidx.navigation.findNavController
import sayaradz.authentification.R
import sayaradz.dataClasses.Car
import sayaradz.ui.fragment.myAnnonce.MyAnnonceFragmentDirections


class CustomMenuItem (private val it : Car , private val layout : View ) : PopupMenu.OnMenuItemClickListener {

        override fun onMenuItemClick(menuItem: MenuItem): Boolean {

            when (menuItem.itemId) {

                R.id.btn_edit -> {

                 edit(layout,it.id.toString())
                    return true
                }

                R.id.btn_archive -> {
                   //Implement
                    return true
                }
                R.id.btn_delete -> {
                    //Implement
                    return true
                }

            }
            return false
        }

    private fun edit(view: View, annonceId: String) {
        val action = MyAnnonceFragmentDirections.actionMyAnnonceFragmentToEditAnnonceFragment(annonceId)
        Log.i("MENU_ITEM","EDIIIIIT")
        view.setOnClickListener { v: View ->
            v.findNavController().navigate(action)
        }
    }
}

