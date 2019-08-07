package sayaradz.ui.fragment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import sayaradz.authentification.R

class ColorAdapter(private val context: Context,
                   private val data: ArrayList<String>) : RecyclerView.Adapter<ColorAdapter.ColorViewHolder>() {

    var mSelectedItem = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorViewHolder {
        return ColorViewHolder.creat(parent)
    }

    override fun onBindViewHolder(holder: ColorViewHolder, position: Int) {
        var item = data[position]
        holder.bind(item)
    }

    override fun getItemCount() = data.size



    class ColorViewHolder private constructor(val layout: View) : RecyclerView.ViewHolder(layout) {
        var textView: RadioButton = itemView.findViewById(R.id.circle)
        init {
            val l = View.OnClickListener { view ->

            }
        }

        companion object {
            fun creat(parent: ViewGroup): ColorViewHolder {
                val colorItemView = LayoutInflater.from(parent.context)
                        .inflate(R.layout.color_list_item, parent, false)
                return ColorViewHolder(colorItemView)
            }
        }


        fun bind(item: String) {
            textView.text = item
            textView.setOnClickListener {
                
            }
        }

    }



}