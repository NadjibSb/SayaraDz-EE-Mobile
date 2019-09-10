package sayaradz.ui.fragment.annonce
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sayaradz.api.ServiceBuilder
import sayaradz.api.ServiceProvider
import sayaradz.authentification.R
import sayaradz.dataClasses.Car
import sayaradz.ui.MainActivityViewModel

class AnnonceViewModel( val annonceId: String) : ViewModel() {

    val TAG = "AnnonceViewModel"

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

}