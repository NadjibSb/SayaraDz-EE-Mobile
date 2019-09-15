package sayaradz.ui.fragment.myOffers

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sayaradz.api.Api
import sayaradz.dataClasses.OfferToPost

class MyOffersViewModel (token : String ) : ViewModel() {

    var offers: MutableLiveData<ArrayList<OfferToPost>>
    val TAG = "TAG-OfferViewModel"

    init {
        offers = defaultList()
       // offers = getOffres(TAG,token)
    }

    private fun defaultList(): MutableLiveData<ArrayList<OfferToPost>> {

        var finalList = MutableLiveData<ArrayList<OfferToPost>>()
        var list = ArrayList<OfferToPost>()
        for (i in 0..3) {
            list.add(OfferToPost(
                    "Clio 4 2017",
                    System.currentTimeMillis(),
                    "https://www.paycar.fr/wp-content/uploads/voiture-occasion-espagne.jpg",
                    950000
            ))
        }
        finalList.value = list
        return finalList
    }
    fun getOffres(TAG: String, idToken: String): MutableLiveData<ArrayList<OfferToPost>> {


        val call = Api.api.getMyOffers(idToken) // The request included the token
        var offerRespond: List<OfferToPost>?
        var offerList = ArrayList<OfferToPost>()
        var finalList = MutableLiveData<ArrayList<OfferToPost>>()

        call.enqueue(object : Callback<List<OfferToPost>> {
            override fun onResponse(call: Call<List<OfferToPost>>, response: Response<List<OfferToPost>>) {
                Log.i(TAG, "DisplayOfferList: call enqueue")

                if (!response.isSuccessful) {
                    Log.i(TAG, "CODE:" + response.code().toString())
                    return
                }

                offerRespond = response.body()  // Getting the list
                if (offerRespond != null) {
                    Log.i(TAG, "REPONSES: HERE is ALL THE BRANDS:")
                    for (m in offerRespond!!) {
                        var content = ""
                        content += " $m \n"
                        Log.i(TAG, "\n=========\n$content")
                        offerList.add(m)
                    }
                    offerList.sortBy { it.title }
                    finalList.value = offerList
                    //Log.i("TAG",offerList.toString())
                }
            }

            override fun onFailure(call: Call<List<OfferToPost>>, t: Throwable) {
                Log.i(TAG, "error CODE:" + t.message)
            }
        })
        return finalList
    }
}
