package sayaradz.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import sayaradz.authentification.R
import sayaradz.dataClasses.Marque
import sayaradz.dataClasses.Model

class ModelFragment:Fragment() {

    companion object {
        fun getInstance() = SearchFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater?.inflate(R.layout.model_fragment, container, false)
        val args = ModelFragmentArgs.fromBundle(arguments!!)
        Toast.makeText(context,"Marque id: ${args.marqueId}",Toast.LENGTH_SHORT).show()


        setUpRecycleView(rootView, defaultList() )
        return rootView
    }

    //RecycleView--------------------------------------------
    private fun setUpRecycleView(rootView: View, list: List<Model>) {
        var recyclerView = rootView.findViewById(R.id.modelListView) as RecyclerView
        recyclerView.adapter = ModelAdapter(list, this@ModelFragment.context!!)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.itemAnimator = SlideInUpAnimator()
        recyclerView.setHasFixedSize(true)
    }

    private fun defaultList():ArrayList<Model>{

        var modelList  = ArrayList<Model>()
        for (i in 0..20){
            modelList.add(Model("$i","Model $i","${R.drawable.m_volkswagen}"))
        }
        return modelList

    }
}