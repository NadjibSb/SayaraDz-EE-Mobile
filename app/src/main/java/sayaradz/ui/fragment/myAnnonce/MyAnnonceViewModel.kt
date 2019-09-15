package sayaradz.ui.fragment.myAnnonce

import android.content.Context
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.facebook.login.LoginManager
import com.google.firebase.auth.FirebaseAuth
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sayaradz.api.ServiceBuilder
import sayaradz.api.ServiceProvider
import sayaradz.dataClasses.Car
import sayaradz.ui.fragment.adapter.ListAdapter
import java.util.*

class MyAnnonceViewModel(var token: String) : ViewModel(), LifecycleOwner {
    override fun getLifecycle(): Lifecycle {
        return MyAnnonceFragment.lifecycleRegistry
    }

    val TAG = "MesAnnoncesActivityVM"

    companion object {
        val TAG = "DELETE"
        lateinit var newAnnonces: MutableLiveData<ArrayList<Car>>
        lateinit var context: Context
        var token = ""
        lateinit var life: LifecycleOwner
        fun fill(list: MutableLiveData<ArrayList<Car>>, context: Context, t: String) {
            token = t
            newAnnonces = list
            newAnnonces.observe(life, Observer { annonces ->
                Log.i("SIZE AFTER CLEAR", annonces.size.toString())
                MyAnnonceFragment.recyclerView.adapter = ListAdapter(annonces, ListAdapter.ViewHolderType.MyAnnonce, context, token)
                MyAnnonceFragment.recyclerView.adapter!!.notifyDataSetChanged()
            })



        }

        var api = ServiceBuilder.buildService(ServiceProvider::class.java)


        fun getNewAnnonces(idToken: String, annonceId: String): MutableLiveData<ArrayList<Car>> {

            // delete annonces
            val call0 = api.deleteAnnounce(idToken, annonceId)

            call0.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    Log.i(TAG, "DisplayAnnonce: call enqueue")

                    if (!response.isSuccessful()) {
                        Log.i(TAG, "DELETED")
                        Log.i(TAG, "CODE:" + response.code().toString())
                        return
                    }


                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.i(TAG, "error CODE:" + t.message)
                }
            })


            // get new Annonces
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
                        annonceList.clear()
                        Log.i(TAG, "REPONSES: HERE is ALL THE Announcement OF current User")
                        for (m in AnnounceRespond!!) {
                            var content = ""
                            content += "ID: " + m.id + "\n"
                            content += "Name: " + m.title + "\n"
                            Log.i(TAG, "\n=========\n$content")
                            annonceList.add(m)
                        }
                        finalList.value = annonceList

                    }
                }

                override fun onFailure(call: Call<List<Car>>, t: Throwable) {
                    Log.i(TAG, "error CODE:" + t.message)
                }
            })
            return finalList
        }

        fun getAnnonces(idToken: String): MutableLiveData<ArrayList<Car>> {

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
                        // annonceList.clear()
                        Log.i(TAG, "REPONSES: HERE is ALL THE Announcement OF current User")
                        for (m in AnnounceRespond!!) {
                            var content = ""
                            content += "ID: " + m.id + "\n"
                            content += "Name: " + m.title + "\n"
                            Log.i(TAG, "\n=========\n$content")
                            annonceList.add(m)
                        }
                        finalList.value = annonceList

                    }
                }

                override fun onFailure(call: Call<List<Car>>, t: Throwable) {
                    Log.i(TAG, "error CODE:" + t.message)
                }
            })
            return finalList
        }


    }

    var annonces: MutableLiveData<ArrayList<Car>>

    init {
        api = ServiceBuilder.buildService(ServiceProvider::class.java)
        annonces = getAnnonces(token)
        life = this
        fill(annonces, MyAnnonceFragment.contextMyAn, token)
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
                    // annonceList.clear()
                    Log.i(TAG, "REPONSES: HERE is ALL THE Announcement OF current User")
                    for (m in AnnounceRespond!!) {
                        var content = ""
                        content += "ID: " + m.id + "\n"
                        content += "Name: " + m.title + "\n"
                        Log.i(TAG, "\n=========\n$content")
                        annonceList.add(m)
                    }
                    finalList.value = annonceList

                }
            }

            override fun onFailure(call: Call<List<Car>>, t: Throwable) {
                Log.i(TAG, "error CODE:" + t.message)
            }
        })
        return finalList
    }


    fun signOut() {
        FirebaseAuth.getInstance()?.signOut()
        LoginManager.getInstance().logOut()
    }


}