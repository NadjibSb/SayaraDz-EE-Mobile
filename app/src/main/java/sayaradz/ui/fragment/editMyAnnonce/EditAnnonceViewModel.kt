package sayaradz.ui.fragment.editMyAnnonce

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sayaradz.api.ServiceBuilder
import sayaradz.api.ServiceProvider
import sayaradz.dataClasses.AnnonceUpdate
import sayaradz.dataClasses.Car
import sayaradz.dataClasses.Vehicule
import sayaradz.dataClasses.VehiculeUpdate

class EditAnnonceViewModel(val annonceId: String, val token: String) : ViewModel() {

    val TAG = "EditAnnonceViewModel"

    // HERE GETTING DATA FOR AN ANNONCE CLICKED
    var annonce: MutableLiveData<Car>

    val api: ServiceProvider

    init {
        api = ServiceBuilder.buildService(ServiceProvider::class.java)
        annonce = getAnnonceDetails(annonceId, token)
    }


    private fun getAnnonceDetails(annonceId: String, idToken: String): MutableLiveData<Car> {

        val call = api.getAnnounceDetails(idToken, annonceId) // The request included the token
        var AnnounceRespond: Car? = null
        var finalAnnonce = MutableLiveData<Car>()

        call.enqueue(object : Callback<Car> {
            override fun onResponse(call: Call<Car>, response: Response<Car>) {
                Log.i(TAG, "DisplayAnnonce: call enqueue")

                if (!response.isSuccessful()) {
                    Log.i(TAG, "CODE:" + response.code().toString())
                    return
                }

                AnnounceRespond = response.body()  // Getting the list
                if (AnnounceRespond != null) {
                    Log.i(TAG, "REPONSES: HERE is THE DETAILS")

                    finalAnnonce.value = AnnounceRespond


                }
                Log.i("TAG", AnnounceRespond!!.userId)


            }


            override fun onFailure(call: Call<Car>, t: Throwable) {
                Log.i(TAG, "error CODE:" + t.message)
            }
        })
        return finalAnnonce
    }

    fun updateAnnonce(annonceId: String,vehiculeId:String, vehicule : VehiculeUpdate ,annonce: AnnonceUpdate) {

        val call0 = api.updateVehicule(token,vehiculeId,vehicule)
        call0.enqueue(object : Callback<VehiculeUpdate> {
            override fun onResponse(call: Call<VehiculeUpdate>, response: Response<VehiculeUpdate>) {

                var call1 = api.updateAnnounce(token, annonceId ,annonce)
                call1.enqueue(object : Callback<AnnonceUpdate> {
                    override fun onResponse(call: Call<AnnonceUpdate>, response: Response<AnnonceUpdate>) {
                        Log.i(TAG, "UPDATE ANNONCE")

                        if (!response.isSuccessful()) {
                            Log.i(TAG, "UPDATE ANNONCE")
                            Log.i(TAG, "CODE:" + response.code().toString() + "" + response.body().toString())

                            return
                        }

                    }

                    override fun onFailure(call: Call<AnnonceUpdate>, t: Throwable) {
                        Log.i(TAG, "error CODE:" + t.message)
                    }
                })




                Log.i(TAG, "DisplayAnnonce: call enqueue")

                if (!response.isSuccessful()) {
                    Log.i(TAG, "UPDATED")
                    Log.i(TAG, "CODE:" + response.code().toString())
                    return
                }


            }

            override fun onFailure(call: Call<VehiculeUpdate>, t: Throwable) {
                Log.i(TAG, "error CODE:" + t.message)
            }
        })

    }







}