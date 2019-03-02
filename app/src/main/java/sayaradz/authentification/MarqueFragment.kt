package sayaradz.authentification

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.JsonToken
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import sayaradz.services.JsonPlaceHolderApi
import sayaradz.services.Marque



class MarqueFragment:Fragment() {

    lateinit var token: String
    companion object {
        fun getInstance() = MarqueFragment()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView= inflater.inflate(R.layout.marque_fragment, container, false)
        token = this.arguments.getString("Token")
        Log.i("TOKEN",token)

        Log.i("TOKEN","blabla")



        var mMarqueList=ArrayList<Marque>() //= constructList()
        var mMarques: List<Marque>
        //mMarques = retrofit("aaaaa")!!

        val url="http://7874fe86.ngrok.io/"    // the url of service
        val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        Log.i("TOKEN","111111")

        val jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi::class.java)
        val call = jsonPlaceHolderApi.getMarques(token) // The request included the token
        var marques: List<Marque>? = null

        Log.i("TOKEN",call.toString())

        call.enqueue(object : Callback<List<Marque>> {
            override fun onResponse(call: Call<List<Marque>>, response: Response<List<Marque>>) {
                Log.i("TOKEN","22222")
                if (!response.isSuccessful()) {
                    //tvResult.setText("Code: "+response.code()); // Displayin the code of teh response
                    Log.i("CODE",response.code().toString())
                    return
                }
                marques = response.body()  // Getting the marques

                if (marques != null) {
                    Log.i("REPONSES","HERE is ALL THE BRANDS FROM OUR SERVER:")
                    for (m in marques!!) {
                        var content = ""
                        content += "ID: " + m.IdMarque + "\n"
                        content += "Name: " + m.NomMarque + "\n"
                        mMarqueList.add(m)

                        Log.i("CONTENT",content)

                        //tvResult.append(content) // Displaying results temporary in text view
                    }
                    setUpRecycleView(rootView, mMarqueList)
                }
            }
            override fun onFailure(call: Call<List<Marque>>, t: Throwable) {
                //tvResult.setText(t.message);  // ERROR CODE
                Log.i("TOKEN","3333333333")
            }
        })
        //
        Log.i("TOKEN","444444444")
        Log.i("TOKEN",token)


        return rootView
    }



    private fun retrofit(idToken: String): List<Marque>?{
        val url="http://7874fe86.ngrok.io/api/"    // the url of service
        val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        Log.i("TOKEN","111111")

        val jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi::class.java)
        val call = jsonPlaceHolderApi.getMarques(idToken) // The request included the token
        var marques: List<Marque>? = null

        Log.i("TOKEN",call.toString())

        call.enqueue(object : Callback<List<Marque>> {
            override fun onResponse(call: Call<List<Marque>>, response: Response<List<Marque>>) {
                Log.i("TOKEN","22222")
                if (!response.isSuccessful()) {
                    //tvResult.setText("Code: "+response.code()); // Displayin the code of teh response
                    Log.i("CODE",response.code().toString())
                    return
                }
                marques = response.body()  // Getting the marques

                if (marques != null) {
                    Log.i("REPONSES","HERE is ALL THE BRANDS FROM OUR SERVER:")
                    for (m in marques!!) {
                        var content = ""
                        content += "ID: " + m.IdMarque + "\n"
                        content += "Name: " + m.NomMarque + "\n"
                        Log.i("CONTENT",content)

                        //tvResult.append(content) // Displaying results temporary in text view
                    }
                }
            }
            override fun onFailure(call: Call<List<Marque>>, t: Throwable) {
                //tvResult.setText(t.message);  // ERROR CODE
                Log.i("TOKEN","3333333333")
            }
        })
        //
        Log.i("TOKEN","444444444")
        Log.i("TOKEN",idToken)
        return marques
    }

    private fun constructList(): List<Marque>{
        var mMarqueList= ArrayList<Marque>()
        for(i in 0..20){
            var x=0
            when(i%5){
                0 -> x = R.drawable.m_audi
                1 -> x = R.drawable.m_renault
                2 -> x = R.drawable.m_volkswagen
                3 -> x = R.drawable.m_audi
                4 -> x = R.drawable.m_renault
            }
            var drawable = ResourcesCompat.getDrawable(resources,x,null)
            var m = Marque()
            m.IdMarque = i.toString()
            m.NomMarque = "Marque $i"
            //m.Image= drawable!!
            mMarqueList.add(m)
        }
        return mMarqueList
    }

    //RecycleView--------------------------------------------
    private fun setUpRecycleView(rootView: View, list: List<Marque>){
        var recyclerView= rootView.findViewById(R.id.marqueListView) as RecyclerView
        recyclerView.adapter = MarqueAdapter(list)
        recyclerView.layoutManager = GridLayoutManager(context,2)
        recyclerView.itemAnimator = SlideInUpAnimator()
        recyclerView.setHasFixedSize(true)
    }

    //Adapter Class------------------------------------------
    class MarqueAdapter(private val marques: List<Marque>)
        : RecyclerView.Adapter<MarqueAdapter.MarqueViewHolder>(){

        private var mMarques: List<Marque>
        init {
            mMarques = marques
        }

        class MarqueViewHolder(val layout: View):RecyclerView.ViewHolder(layout){
            var nameTextView: TextView
            var image: ImageView
            init {
                nameTextView = itemView.findViewById(R.id.marqueName)
                image = itemView.findViewById(R.id.logoImg)
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarqueViewHolder {
            val marqueItemView = LayoutInflater.from(parent.context).inflate(R.layout.marque_list_item,parent,false)
            return MarqueViewHolder(marqueItemView)
        }

        override fun onBindViewHolder(holder: MarqueViewHolder, position: Int) {
            var marque = mMarques[position]
            holder.nameTextView.setText(marque.NomMarque)
            Log.i("marque",marque.NomMarque)
            //holder.image.setImageDrawable(R.drawable.m_audi/*marque.Image*/)

        }

        override fun getItemCount() = mMarques.size
    }

}