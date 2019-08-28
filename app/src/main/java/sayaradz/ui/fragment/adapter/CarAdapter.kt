package sayaradz.ui.fragment.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import sayaradz.authentification.R
import sayaradz.dataClasses.Car

class CarAdapter(private val cars: ArrayList<Car>, val context: Context)
    : RecyclerView.Adapter<CarAdapter.CarViewHolder>() {


    inner class CarViewHolder(val layout: View) : RecyclerView.ViewHolder(layout) {
        var nameTextView: TextView
        var image: ImageView

        init {
            nameTextView = itemView.findViewById(R.id.item_name)
            image = itemView.findViewById(R.id.img_item_logo)
        }
    }
    lateinit var carItemView : View
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val marqueItemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        carItemView=marqueItemView
        return CarViewHolder(marqueItemView)
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        var car = cars[position] ////
        holder.nameTextView.setText(car.title)
        Log.i("CAAAAR", car.title)
        Glide.with(context).load(car.imageVehicle1).into(holder.image)
        holder.itemView.setOnClickListener {

            Log.i("CLICK","item")
        }

    }

    override fun getItemCount() = cars.size
}