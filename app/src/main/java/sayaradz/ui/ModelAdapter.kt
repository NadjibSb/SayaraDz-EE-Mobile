package sayaradz.ui

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import sayaradz.authentification.R
import sayaradz.dataClasses.Modele
import sayaradz.ui.fragment.model.ModelFragmentDirections

class ModelAdapter(val modeles: List<Modele>, val context: Context, val marqueId: String)
    :RecyclerView.Adapter<ModelAdapter.ModelViewHolder>() {

    private var mModeles: List<Modele>
    init{
        mModeles = modeles
    }

    class ModelViewHolder(val layout: View): RecyclerView.ViewHolder(layout) {
        var modelName: TextView
        var modelImage: ImageView
        init {
            modelName = itemView.findViewById(R.id.modelName)
            modelImage = itemView.findViewById(R.id.model_main_img)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModelAdapter.ModelViewHolder {
        val modelItemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.model_list_item, parent, false)
        return ModelAdapter.ModelViewHolder(modelItemView)
    }


    override fun onBindViewHolder(holder: ModelAdapter.ModelViewHolder, position: Int) {
        var model = mModeles[position]
        holder.modelName.setText(model.name)
        handleClick(holder.layout,marqueId,model.id)

        Log.i("marque", model.name)
        /*val imageUrl = GlideUrl(marque.imageUrl, LazyHeaders.Builder()
                .addHeader("Authorization", token)
                .build())
        Glide.with(context).load(imageUrl).into(holder.image)*/
        holder.modelImage.setImageResource(R.drawable.m_volkswagen)
    }

    private fun handleClick(view: View, marqueId: String, modelId: String){
        val action = ModelFragmentDirections.actionModelFragmentToVersionFragment(modelId)
        view.setOnClickListener {v:View ->
            v.findNavController().navigate(action)
        }
    }

    override fun getItemCount() = mModeles.size
}