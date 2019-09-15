package sayaradz.ui.fragment.addAnnonce

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sayaradz.api.Api
import sayaradz.api.ServiceBuilder
import sayaradz.api.ServiceProvider
import sayaradz.dataClasses.*
import java.util.*

class AddAnnonceViewModel(val token: String) : ViewModel() {

    val TAG = "AddAnnonceViewModel"
    var marques: MutableLiveData<ArrayList<Marque>>
    val api: ServiceProvider

    init {
        api = ServiceBuilder.buildService(ServiceProvider::class.java)
        marques = Api.getMarques(TAG, token)
    }


    // Creation
    fun createAnnonce(idToken: String, image: MultipartBody.Part, vehicule: Vehicule, titre: String, prix: Int, commentaires: String) {


        val call0 = api.createVehicule(idToken, image, convert(vehicule.kilometrage.toString()), convert(vehicule.date), convert(vehicule.versionPk), convert(vehicule.color), convert(vehicule.modelPk.toString()))
        call0.enqueue(object : Callback<Car> {
            override fun onResponse(call: Call<Car>, response: Response<Car>) {
                Log.i(TAG, "CREATE VEHICULE")
                var car = response.body()!!
                var call1 = api.createAnnounce(idToken, convert(titre), convert(car.id.toString()), convert(prix.toString()), convert(commentaires))
                Log.i("CAAAR", car.id.toString())
                call1.enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        Log.i(TAG, "CREATE ANNONCE")

                        if (!response.isSuccessful()) {
                            Log.i(TAG, "CREATED ANNONCE")
                            Log.i(TAG, "CODE:" + response.code().toString() + "" + response.body().toString())

                            return
                        }

                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Log.i(TAG, "error CODE:" + t.message)
                    }
                })



                if (!response.isSuccessful()) {
                    Log.i(TAG, "CREATED VEHICULE")
                    Log.i("QUERY", vehicule.date + "" + vehicule.versionPk)
                    Log.i(TAG, "CODE:" + response.code().toString())
                    return
                }


            }

            override fun onFailure(call: Call<Car>, t: Throwable) {
                Log.i(TAG, "error CODE:" + t.message)
            }
        })


    }


    fun convert(a: String): RequestBody {
        return RequestBody.create(MediaType.parse("text/plain"), a)
    }


    // Adding Companion object
    companion object {
        val TAG = "AddAnnonceViewModel"
        val api = ServiceBuilder.buildService(ServiceProvider::class.java)

        fun getModels(idToken: String, marqueId: String): MutableLiveData<ArrayList<Modele>> {

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
                            content += "$m \n"
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

        fun getVersions(idToken: String, modeleId: String): MutableLiveData<ArrayList<Version>> {

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


}