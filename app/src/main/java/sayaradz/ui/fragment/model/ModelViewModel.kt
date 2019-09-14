package sayaradz.ui.fragment.model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sayaradz.api.ServiceBuilder
import sayaradz.api.ServiceProvider
import sayaradz.dataClasses.Modele
import sayaradz.ui.MainActivityViewModel

class ModelViewModel(val marqueId: String) : ViewModel() {

    val TAG = "ModelViewModel"
    var models: MutableLiveData<ArrayList<Modele>>
    val api: ServiceProvider
    var token = MainActivityViewModel.token

    init {
        api = ServiceBuilder.buildService(ServiceProvider::class.java)
        models = getModels(token, marqueId)
    }

    private fun defaultList(): MutableLiveData<ArrayList<Modele>> {

        var modelList = ArrayList<Modele>()
        var finalList = MutableLiveData<ArrayList<Modele>>()
        for (i in 0..20) {
            //modelList.add(Modele("$i", "Modele $i", "Marque $marqueId", arrayListOf()//))//, "${R.drawable.m_volkswagen}"))

        }
        finalList.value = modelList
        return finalList

    }


    private fun getModels(idToken: String, marqueId: String): MutableLiveData<ArrayList<Modele>> {

        val call = api.getModelsByMarque(idToken, marqueId) // The request included the token
        var modeleRespond: List<Modele>?
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