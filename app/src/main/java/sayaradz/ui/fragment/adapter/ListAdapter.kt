package sayaradz.ui.fragment.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import sayaradz.dataClasses.*


class ListAdapter(val list: List<Any>,
                  private val viewHolderType: ViewHolderType,
                  val context: Context,
                  val token: String)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    enum class ViewHolderType {
        MARQUE,
        MODEL,
        VERSION,
        CAR,
        MyAnnonce,
        NOTIFICATION,
        OFFER
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewHolderType) {
            ViewHolderType.MARQUE -> MarqueViewHolder.creat(parent)
            ViewHolderType.MODEL -> ModelViewHolder.creat(parent)
            ViewHolderType.VERSION -> VersionViewHolder.creat(parent)
            ViewHolderType.CAR -> CarViewHolder.creat(parent)
            ViewHolderType.MyAnnonce -> MyAnnonceViewHolder.creat(parent, context)
            ViewHolderType.NOTIFICATION -> NotificationViewHolder.creat(parent)
            ViewHolderType.OFFER -> OfferViewHolder.creat(parent)
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (viewHolderType) {
            ViewHolderType.MARQUE -> {
                var marque = list[position] as Marque
                holder as MarqueViewHolder
                holder.bind(marque)
            }
            ViewHolderType.MODEL -> {
                var model = list[position] as Modele
                holder as ModelViewHolder
                holder.bind(model)
            }
            ViewHolderType.VERSION -> {
                var version = list[position] as Version
                holder as VersionViewHolder
                holder.bind(version)
            }
            ViewHolderType.CAR -> {
                var announce = list[position] as Car
                holder as CarViewHolder
                holder.bind(announce)
            }
            ViewHolderType.MyAnnonce -> {
                var myAnnounce = list[position] as Car
                holder as MyAnnonceViewHolder
                holder.bind(myAnnounce)
            }
            ViewHolderType.NOTIFICATION -> {
                var notif = list[position] as Notification
                holder as NotificationViewHolder
                holder.bind(notif)
            }
            ViewHolderType.OFFER -> {
                var offer = list[position] as OfferToPost
                holder as OfferViewHolder
                holder.bind(offer)
            }
        }
    }

    override fun getItemCount() = list.size


}