package sayaradz.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import sayaradz.api.JsonPlaceHolderApi
import sayaradz.authentification.R
import sayaradz.dataClasses.Marque


class MarqueFragment : Fragment() {
    val TAG = "TAG-MarqueFragment"

    var token: String? =""

    companion object {
        val url = "https://sayaradz-ee-backend.herokuapp.com/"
        //val url = "http://192.168.0.36:8000/"
        fun getInstance() = MarqueFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.marque_fragment, container, false)

        token = "token" //this.arguments!!.getString("TOKEN")
        Log.i(TAG, "TOKEN RECEIVED: $token")

        //DisplayMarqueList(rootView, token!!)

        setUpRecycleView(rootView, defaultList() )

        return rootView
    }

/*
    private fun DisplayMarqueList(rootView: View, idToken: String) {

        val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        Log.i(TAG, "DisplayMarqueList")

        val jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi::class.java)
        val call = jsonPlaceHolderApi.getMarques(idToken) // The request included the token
        var marqueRespond: List<Marque>? = null
        var marqueList  = ArrayList<Marque>()

        call.enqueue(object : Callback<List<Marque>> {
            override fun onResponse(call: Call<List<Marque>>, response: Response<List<Marque>>) {
                Log.i(TAG, "DisplayMarqueList: call enqueue")

                if (!response.isSuccessful()) {
                    Log.i(TAG, "CODE:"+ response.code().toString())
                    return
                }

                marqueRespond = response.body()  // Getting the marques
                if (marqueRespond != null) {
                    Log.i(TAG, "REPONSES: HERE is ALL THE BRANDS FROM OUR SERVER:")
                    for (m in marqueRespond!!) {
                        var content = ""
                        content += "ID: " + m.IdMarque + "\n"
                        content += "Name: " + m.NomMarque + "\n"
                        content += "Name: " + m.Image
                        Log.i(TAG, "\n=> CONTENT:  $content")
                        marqueList.add(m)

                    }
                    setUpRecycleView(rootView, marqueList )
                }
            }

            override fun onFailure(call: Call<List<Marque>>, t: Throwable) {
                Log.i(TAG, "error CODE:"+t.message)
            }
        })
    }*/

    //RecycleView--------------------------------------------
    private fun setUpRecycleView(rootView: View, list: List<Marque>) {
        var recyclerView = rootView.findViewById(R.id.marqueListView) as androidx.recyclerview.widget.RecyclerView
        recyclerView.adapter = MarqueAdapter(list, this@MarqueFragment.context!!, token!!)
        recyclerView.layoutManager = androidx.recyclerview.widget.GridLayoutManager(context, 2)
        recyclerView.itemAnimator = SlideInUpAnimator()
        recyclerView.setHasFixedSize(true)
    }

    private fun defaultList():ArrayList<Marque>{

        var marqueList  = ArrayList<Marque>()
        for (i in 0..20){
            marqueList.add(Marque(i.toString(),"Marque $i",R.drawable.m_audi.toString()))
        }
        return marqueList

    }
}