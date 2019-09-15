package sayaradz.ui.fragment.version

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import sayaradz.api.Api
import sayaradz.dataClasses.Version

class VersionViewModel(val modeleId: String, val token: String) : ViewModel() {

    val TAG = "VersionViewModel"
    var versions: MutableLiveData<ArrayList<Version>>

    init {
        versions = Api.getVersions(TAG, token, modeleId)
    }

    fun getVersions(){
        versions = Api.getVersions(TAG, token, modeleId)
    }

    private fun defaultList(): MutableLiveData<ArrayList<Version>> {

        var versionList = ArrayList<Version>()
        var finalList = MutableLiveData<ArrayList<Version>>()
        for (i in 0..20) {
            //versionList.add(Version("$i", "Version $i", "${R.drawable.a3_sedan}","url"))
        }
        finalList.value = versionList
        return finalList

    }


}