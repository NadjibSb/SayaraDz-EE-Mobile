package sayaradz.ui.fragment.editMyAnnonce

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sayaradz.api.ServiceBuilder
import sayaradz.api.ServiceProvider
import sayaradz.dataClasses.AnnoncePost
import sayaradz.dataClasses.AnnonceUpdate
import sayaradz.dataClasses.Car
import sayaradz.ui.MainActivityViewModel

class EditAnnonceViewModel  ( val annonceId: String) : ViewModel() {

    val TAG = "EditAnnonceViewModel"

    // HERE GETTING DATA FOR AN ANNONCE CLICKED
    var annonce : MutableLiveData<Car>

    var token = MainActivityViewModel.token
    val api: ServiceProvider

    init {
        api = ServiceBuilder.buildService(ServiceProvider::class.java)
        annonce = getAnnonceDetails(annonceId,token)
    }


    private fun getAnnonceDetails(annonceId: String ,idToken: String): MutableLiveData<Car> {

        val call = api.getAnnounceDetails(idToken,annonceId) // The request included the token
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
                Log.i("TAG",AnnounceRespond!!.userId)


            }


            override fun onFailure(call: Call<Car>, t: Throwable) {
                Log.i(TAG, "error CODE:" + t.message)
            }
        })
        return finalAnnonce
    }

    companion object {
        val TAG = "EditAnnonceViewModel"
        val api = ServiceBuilder.buildService(ServiceProvider::class.java)
        // Update
        fun updateAnnonce ( idToken : String , annonceId: String , annonce :AnnonceUpdate )
        {

            val call0 = api.updateAnnounce(idToken,annonceId,annonce)
            call0.enqueue(object : Callback<AnnonceUpdate> {
                override fun onResponse(call: Call<AnnonceUpdate>, response: Response<AnnonceUpdate>) {
                    Log.i(TAG, "DisplayAnnonce: call enqueue")

                    if (!response.isSuccessful()) {
                        Log.i(TAG,"UPDATED")
                        Log.i(TAG, "CODE:" + response.code().toString())
                        return
                    }


                }
                override fun onFailure(call: Call<AnnonceUpdate>, t: Throwable) {
                    Log.i(TAG, "error CODE:" + t.message)
                }
            })

        }







    }

}