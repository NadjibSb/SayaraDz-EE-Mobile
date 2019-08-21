package sayaradz.ui.fragment.adapter


import android.content.Context
import android.view.MenuItem
import androidx.appcompat.widget.PopupMenu
import sayaradz.authentification.R
import sayaradz.dataClasses.Car


class CustomMenuItem (private val it : Car, private val cont : Context) : PopupMenu.OnMenuItemClickListener {

        override fun onMenuItemClick(menuItem: MenuItem): Boolean {
            when (menuItem.itemId) {

                R.id.btn_edit -> {
                    //Implement
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


    }
