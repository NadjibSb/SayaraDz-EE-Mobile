package sayaradz.ui.fragment.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import sayaradz.dataClasses.Car
import sayaradz.dataClasses.Marque
import sayaradz.dataClasses.Modele
import sayaradz.dataClasses.Version


class ListAdapter(val list: List<out Any>, val viewHolderType: ViewHolderType, val context: Context, val token: String)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    enum class ViewHolderType {
        MARQUE,
        MODEL,
        VERSION,
        CAR
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewHolderType) {
            ViewHolderType.MARQUE -> MarqueViewHolder.creat(parent)
            ViewHolderType.MODEL -> ModelViewHolder.creat(parent)
            ViewHolderType.VERSION -> VersionViewHolder.creat(parent)
            ViewHolderType.CAR -> CarViewHolder.creat(parent)
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
        }
    }

    override fun getItemCount() = list.size


}