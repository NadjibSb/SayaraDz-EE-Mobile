package sayaradz.services

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import sayaradz.authentification.R
import sayaradz.services.Marque

class CarAdapter(private val cars: ArrayList<Car>, val context: Context)
    : RecyclerView.Adapter<CarAdapter.CarViewHolder>() {


    inner class CarViewHolder(val layout: View) : RecyclerView.ViewHolder(layout) {
        var nameTextView: TextView
        var image: ImageView

        init {
            nameTextView = itemView.findViewById(R.id.marqueName)
            image = itemView.findViewById(R.id.logoImg)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val marqueItemView = LayoutInflater.from(parent.context).inflate(R.layout.marque_list_item, parent, false)
        return CarViewHolder(marqueItemView)
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        var car = cars[position] ////
        holder.nameTextView.setText(car.carTitle)
        Log.i("CAAAAR", car.carTitle)
        Glide.with(context).load(car.carImage).into(holder.image)
    }

    override fun getItemCount() = cars.size
}