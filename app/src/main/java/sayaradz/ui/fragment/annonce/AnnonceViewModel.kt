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

class AnnonceViewModel() : ViewModel() {

    val TAG = "AnnonceViewModel"
    var annonces: MutableLiveData<ArrayList<Car>>
    var token = ""
    val api: ServiceProvider

    init {
        api = ServiceBuilder.buildService(ServiceProvider::class.java)
        annonces = getAnnonces(token)
    }

    private fun defaultList(): MutableLiveData<ArrayList<Car>> {

        var annoncesList = ArrayList<Car>()
        var finalList = MutableLiveData<ArrayList<Car>>()
        for (i in 0..20) {
            annoncesList.add(Car(i, "Anonce$i", "${R.drawable.m_audi}"))
        }
        finalList.value = annoncesList
        return finalList

    }

    private fun getAnnonces(idToken: String): MutableLiveData<ArrayList<Car>> {

        val call = api.getAnnounceByUserId(idToken) // The request included the token
        var AnnounceRespond: List<Car>? = null
        var annonceList = ArrayList<Car>()
        var finalList = MutableLiveData<ArrayList<Car>>()

        call.enqueue(object : Callback<List<Car>> {
            override fun onResponse(call: Call<List<Car>>, response: Response<List<Car>>) {
                Log.i(TAG, "DisplayAnnonceList: call enqueue")

                if (!response.isSuccessful()) {
                    Log.i(TAG, "CODE:" + response.code().toString())
                    return
                }

                AnnounceRespond = response.body()  // Getting the list
                if (AnnounceRespond != null) {
                    Log.i(TAG, "REPONSES: HERE is ALL THE Announcement OF current User")
                    for (m in AnnounceRespond!!) {
                        var content = ""
                        content += "ID: " + m.id + "\n"
                        content += "Name: " + m.title + "\n"
                        Log.i(TAG, "\n=========\n$content")
                        annonceList.add(m)
                    }
                    finalList.value = annonceList
                    //Log.i("TAG",marqueList.toString())
                }
            }

            override fun onFailure(call: Call<List<Car>>, t: Throwable) {
                Log.i(TAG, "error CODE:" + t.message)
            }
        })
        return finalList
    }

}