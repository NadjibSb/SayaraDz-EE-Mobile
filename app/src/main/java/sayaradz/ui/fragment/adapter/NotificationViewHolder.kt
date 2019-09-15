package sayaradz.ui.fragment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import sayaradz.authentification.R
import sayaradz.convertLongToDateString
import sayaradz.dataClasses.Notification
import sayaradz.inflateImageFromUrl
import sayaradz.ui.fragment.version.VersionFragmentDirections

class NotificationViewHolder private constructor(val layout: View) : RecyclerView.ViewHolder(layout) {
    private var notifTitle: TextView = itemView.findViewById(R.id.notif_title)
    private var notifDate: TextView = itemView.findViewById(R.id.notif_date)
    private var notifContent: TextView = itemView.findViewById(R.id.notif_content)
    private var notifOwnerImg: ImageView = itemView.findViewById(R.id.notif_owner)
    private var notifImg: ImageView = itemView.findViewById(R.id.notif_img)

    companion object {
        fun creat(parent: ViewGroup): NotificationViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.notification_list_item, parent, false)
            return NotificationViewHolder(itemView)
        }
    }

    fun bind(notification: Notification) {
        notifTitle.text = notification.ownerName + ": " + notification.type
        notifDate.text = convertLongToDateString(notification.date)
        notifContent.text = notification.content
        inflateImageFromUrl(
                notification.ownerImg,
                notifOwnerImg,
                R.drawable.ic_account_circle_black_24dp,
                R.drawable.ic_account_circle_black_24dp,
                "")
        inflateImageFromUrl(
                notification.img,
                notifImg,
                R.drawable.ic_image,
                R.drawable.ic_image,
                "")
    }


    private fun handleClick(view: View) {
        /*val action = VersionFragmentDirections.actionVersionFragmentToFicheTechFragment()
        view.setOnClickListener { v: View ->
            v.findNavController().navigate(action)
        }*/
    }
}