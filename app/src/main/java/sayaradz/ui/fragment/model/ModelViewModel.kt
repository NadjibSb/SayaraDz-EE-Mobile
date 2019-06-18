package sayaradz.ui.fragment.model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import sayaradz.api.JsonPlaceHolderApi
import sayaradz.dataClasses.Modele
import sayaradz.ui.mainActivity.API_URL

class ModelViewModel(val marqueId: String) : ViewModel() {

    val TAG = "ModelViewModel"
    var models: MutableLiveData<ArrayList<Modele>>

    init {
        models = getModels("token")
    }

    private fun defaultList(): MutableLiveData<ArrayList<Modele>> {

        var modelList = ArrayList<Modele>()
        var finalList = MutableLiveData<ArrayList<Modele>>()
        for (i in 0..20) {
            modelList.add(Modele("$i","Modele $i"))//, "${R.drawable.m_volkswagen}"))
        }
        finalList.value = modelList
        return finalList

    }


    fun getModels(idToken: String): MutableLiveData<ArrayList<Modele>> {

        val retrofit = Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        Log.i(TAG, "DisplayModelList")

        val jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi::class.java)
        val call = jsonPlaceHolderApi.getModels(idToken, marqueId) // The request included the token
        var modeleRespond: List<Modele>? = null
        var modelList = ArrayList<Modele>()
        var finalList = MutableLiveData<ArrayList<Modele>>()

        call.enqueue(object : Callback<List<Modele>> {
            override fun onResponse(call: Call<List<Modele>>, response: Response<List<Modele>>) {
                Log.i(TAG, "DisplayModelList: call enqueue")

                if (!response.isSuccessful()) {
                    Log.i(TAG, "CODE:" + response.code().toString())
                    return
                }

                modeleRespond = response.body()  // Getting the list
                if (modeleRespond != null) {
                    Log.i(TAG, "REPONSES: HERE is ALL THE MODELS OF $marqueId MARQUE:")
                    for (m in modeleRespond!!) {
                        var content = ""
                        content += "ID: " + m.id + "\n"
                        content += "Name: " + m.name + "\n"
                        //content += "Image: " + m.imageUrl
                        Log.i(TAG, "\n=========\n$content")
                        modelList.add(m)
                    }
                    finalList.value = modelList
                    //Log.i("TAG",marqueList.toString())
                }
            }

            override fun onFailure(call: Call<List<Modele>>, t: Throwable) {
                Log.i(TAG, "error CODE:" + t.message)
            }
        })
        return finalList
    }
}