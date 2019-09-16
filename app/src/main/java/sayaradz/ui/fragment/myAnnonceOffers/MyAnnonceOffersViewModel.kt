package sayaradz.ui.fragment.myOffers

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sayaradz.api.Api
import sayaradz.dataClasses.OfferToGet


class MyAnnonceOffersViewModel (token : String , annonceId: String ) : ViewModel() {

    var offers: MutableLiveData<ArrayList<OfferToGet>>
    val TAG = "TAG-OfferViewModel"

    init {
       // offers = defaultList()
        offers = getAnnonceOffres(TAG,token,annonceId)
    }

    private fun defaultList(): MutableLiveData<ArrayList<OfferToGet>> {

        var finalList = MutableLiveData<ArrayList<OfferToGet>>()
        var list = ArrayList<OfferToGet>()
        for (i in 0..3) {
            list.add(OfferToGet(
                    950000 ,"Clio 4 2017",
                    false
            ))
        }
        finalList.value = list
        return finalList
    }
    fun getAnnonceOffres(TAG: String, idToken: String,annonceId :String): MutableLiveData<ArrayList<OfferToGet>> {


        val call = Api.api.getMyAnnonceOffers(idToken,annonceId) // The request included the token
        var offerRespond: List<OfferToGet>?
        var offerList = ArrayList<OfferToGet>()
        var finalList = MutableLiveData<ArrayList<OfferToGet>>()

        call.enqueue(object : Callback<List<OfferToGet>> {
            override fun onResponse(call: Call<List<OfferToGet>>, response: Response<List<OfferToGet>>) {
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
                    offerList.sortBy { it.prix }
                    finalList.value = offerList
                    //Log.i("TAG",offerList.toString())
                }
            }

            override fun onFailure(call: Call<List<OfferToGet>>, t: Throwable) {
                Log.i(TAG, "error CODE:" + t.message)
            }
        })
        return finalList
    }
}
