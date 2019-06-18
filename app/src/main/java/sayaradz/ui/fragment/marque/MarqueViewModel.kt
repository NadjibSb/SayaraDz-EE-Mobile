package sayaradz.ui.fragment.marque

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import sayaradz.api.JsonPlaceHolderApi
import sayaradz.authentification.R
import sayaradz.dataClasses.Marque
import sayaradz.ui.mainActivity.API_URL

class MarqueViewModel : ViewModel() {

    val TAG = "MarqueViewModel"
    val token = "token"
    var marques: MutableLiveData<ArrayList<Marque>>

    init {
        marques = getMarques(token)
    }

    private fun defaultList(): MutableLiveData<ArrayList<Marque>> {

        var finalList = MutableLiveData<ArrayList<Marque>>()
        var marqueList = ArrayList<Marque>()
        for (i in 0..20) {
            marqueList.add(Marque(i.toString(), "Marque $i", "${R.drawable.m_audi}"))
        }
        finalList.value = marqueList
        return  finalList

    }


    private fun getMarques(idToken: String): MutableLiveData<ArrayList<Marque>> {

        val retrofit = Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        Log.i(TAG, "DisplayMarqueList")

        val jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi::class.java)
        val call = jsonPlaceHolderApi.getMarques(idToken) // The request included the token
        var marqueRespond: List<Marque>? = null
        var marqueList = ArrayList<Marque>()
        var finalList = MutableLiveData<ArrayList<Marque>>()

        call.enqueue(object : Callback<List<Marque>> {
            override fun onResponse(call: Call<List<Marque>>, response: Response<List<Marque>>) {
                Log.i(TAG, "DisplayMarqueList: call enqueue")

                if (!response.isSuccessful()) {
                    Log.i(TAG, "CODE:" + response.code().toString())
                    return
                }

                marqueRespond = response.body()  // Getting the list
                if (marqueRespond != null) {
                    Log.i(TAG, "REPONSES: HERE is ALL THE BRANDS FROM OUR SERVER:")
                    for (m in marqueRespond!!) {
                        var content = ""
                        content += "ID: " + m.id + "\n"
                        content += "Name: " + m.name + "\n"
                        content += "Name: " + m.imageUrl
                        Log.i(TAG, "\n=========\n$content")
                        marqueList.add(m)
                    }
                    finalList.value = marqueList
                    //Log.i("TAG",marqueList.toString())
                }
            }

            override fun onFailure(call: Call<List<Marque>>, t: Throwable) {
                Log.i(TAG, "error CODE:" + t.message)
            }
        })
        return finalList
    }


}