package sayaradz.authentification

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
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
import android.graphics.drawable.Drawable
import com.squareup.okhttp.ResponseBody
import android.graphics.Bitmap
import java.io.File.separator
import android.graphics.BitmapFactory
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.URL


class MarqueFragment:Fragment() {

    lateinit var token: String
    companion object {
        val url="http://3e37268f.ngrok.io/"
        fun getInstance() = MarqueFragment()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView= inflater.inflate(R.layout.marque_fragment, container, false)
        token = this.arguments.getString("Token")
        Log.i("TOKEN",token)

        Log.i("TOKEN","blabla")
        DisplayMarqueList(rootView,token)



        var mMarqueList=ArrayList<Marque>() //= constructList()
        var mMarques: List<Marque>
        //mMarques = retrofit("aaaaa")!!

/*        val url="http://3e37268f.ngrok.io/api/"    // the url of service
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
        Log.i("TOKEN",token)*/


        return rootView
    }



    private fun DisplayMarqueList(rootView: View,idToken: String){
           // the url of service

        val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        Log.i("TOKEN","111111")

        val jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi::class.java)
        val call = jsonPlaceHolderApi.getMarques(idToken) // The request included the token
        var marques: List<Marque>? = null
        var mMarqueList=ArrayList<Marque>()

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
                        mMarqueList.add(m)
                        //getMarqueImage(token,m.Image)

                        setUpRecycleView(rootView, mMarqueList)
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
            holder.image.setImageDrawable(LoadImageFromWeb(marque.Image))
        }

        override fun getItemCount() = mMarques.size

        fun LoadImageFromWeb(url: String): Drawable? {
            try {
                Log.i("image", url)
                val input = URL(url).getContent() as InputStream
                return Drawable.createFromStream(input, url)
            } catch (e: Exception) {
                return null
            }

        }
    }
/*
    private fun getMarqueImage(token : String, imgUrl : String){
        // the url of service

        val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        Log.i("TOKEN","111111")

        var splits = imgUrl.split("/")
        var imgName = splits[splits.size-1]
        Log.i("TOKEN",imgName)


        val jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi::class.java)
        val call = jsonPlaceHolderApi.getImage(token,imgName)// The request included the token

        Log.i("TOKEN",call.toString())

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Log.i("TOKEN","22222")
                if (!response.isSuccessful()) {
                    //tvResult.setText("Code: "+response.code()); // Displayin the code of teh response
                    Log.i("CODE",response.code().toString())
                    return
                }
                if(response.body()!=null){
                    var fileDownloaded = DownloadImage(response.body()!!,imgName)
                    Log.i("REPONSES","HERE is ALL THE BRANDS FROM OUR SERVER: $fileDownloaded")
                }

            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                //tvResult.setText(t.message);  // ERROR CODE
                Log.i("TOKEN","3333333333")
            }
        })
        //
        Log.i("TOKEN","444444444")
        Log.i("TOKEN",token)
    }

    private fun DownloadImage(body: ResponseBody, imgName: String): Boolean {

        try {
            Log.d("DownloadImage", "Reading and writing file")
            var `in`: InputStream? = null
            var out: FileOutputStream? = null

            try {
                `in` = body!!.byteStream()
                out = FileOutputStream("./$imgName")
                var c: Int

                c = `in`!!.read()
                while (c != -1) {
                    out!!.write(c)
                    c = `in`!!.read()
                }
            } catch (e: IOException) {
                Log.d("DownloadImage", e.toString())
                return false
            } finally {
                `in`?.close()
                if (out != null) {
                    out!!.close()
                }
            }
*//*
            val width: Int
            val height: Int
            val image = findViewById(R.id.imageViewId) as ImageView
            val bMap = BitmapFactory.decodeFile(getExternalFilesDir(null) + File.separator + "AndroidTutorialPoint.jpg")
            width = 2 * bMap.width
            height = 6 * bMap.height
            val bMap2 = Bitmap.createScaledBitmap(bMap, width, height, false)
            image.setImageBitmap(bMap2)*//*

            return true

        } catch (e: IOException) {
            Log.d("DownloadImage", e.toString())
            return false
        }

    }*/


}