package sayaradz.ui.fragment.version

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sayaradz.api.ServiceBuilder
import sayaradz.api.ServiceProvider
import sayaradz.authentification.R
import sayaradz.dataClasses.Version

class VersionViewModel(val modeleId: String) : ViewModel() {

    val TAG = "VersionViewModel"
    var versions: MutableLiveData<ArrayList<Version>>
    var token = ""
    val api: ServiceProvider

    init {
        api = ServiceBuilder.buildService(ServiceProvider::class.java)
        versions = defaultList()//getVersions(token, modeleId)
    }

    private fun defaultList(): MutableLiveData<ArrayList<Version>> {

        var versionList = ArrayList<Version>()
        var finalList = MutableLiveData<ArrayList<Version>>()
        for (i in 0..20) {
            versionList.add(Version("$i", "Version $i", "${R.drawable.m_volkswagen}","url"))
        }
        finalList.value = versionList
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