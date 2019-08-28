package sayaradz.ui.fragment.addAnnonce

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sayaradz.api.ServiceBuilder
import sayaradz.api.ServiceProvider
import sayaradz.dataClasses.Marque
import sayaradz.dataClasses.Modele
import sayaradz.dataClasses.Version
import java.util.ArrayList

class AddAnnonceViewModel :ViewModel() {

    val TAG = "AddAnnonceViewModel"
    val token = "token"
    var marques: MutableLiveData<ArrayList<Marque>>
    lateinit  var models: MutableLiveData<ArrayList<Modele>>
    lateinit  var versions: MutableLiveData<ArrayList<Version>>
    val api: ServiceProvider

    init {
        api = ServiceBuilder.buildService(ServiceProvider::class.java)
        marques = getMarques(token)
    }



    private fun getMarques(idToken: String): MutableLiveData<ArrayList<Marque>> {


        val call = api.getMarques(idToken) // The request included the token
        var marqueRespond: List<Marque>?
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

    private fun getVersions(idToken: String, modeleId: String): MutableLiveData<ArrayList<Version>> {

        val call = api.getVersionsByModele(idToken, modeleId) // The request included the token
        var versioneRespond: List<Version>?
        var versionList = ArrayList<Version>()
        var finalList = MutableLiveData<ArrayList<Version>>()

        call.enqueue(object : Callback<List<Version>> {
            override fun onResponse(call: Call<List<Version>>, response: Response<List<Version>>) {
                Log.i(TAG, "DisplayVersionList: call enqueue")

                if (!response.isSuccessful()) {
                    Log.i(TAG, "CODE:" + response.code().toString())
                    return
                }

                versioneRespond = response.body()  // Getting the list
                if (versioneRespond != null) {
                    Log.i(TAG, "REPONSES: HERE is ALL THE VERSIONS OF $modeleId MARQUE:")
                    for (m in versioneRespond!!) {
                        var content = ""
                        content += "ID: " + m.id + "\n"
                        content += "Name: " + m.name + "\n"
                        //content += "Image: " + m.imageUrl
                        Log.i(TAG, "\n=========\n$content")
                        versionList.add(m)
                    }
                    finalList.value = versionList
                    //Log.i("TAG",marqueList.toString())
                }
            }

            override fun onFailure(call: Call<List<Version>>, t: Throwable) {
                Log.i(TAG, "error CODE:" + t.message)
            }
        })
        return finalList
    }





}