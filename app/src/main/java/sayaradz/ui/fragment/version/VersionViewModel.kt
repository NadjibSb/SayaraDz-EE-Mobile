package sayaradz.ui.fragment.version

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import sayaradz.authentification.R
import sayaradz.dataClasses.Version

class VersionViewModel: ViewModel() {

    var versions: MutableLiveData<ArrayList<Version>>

    init {
        versions = defaultList()
    }

    private fun defaultList(): MutableLiveData<ArrayList<Version>> {

        var modelList = ArrayList<Version>()
        var finalList = MutableLiveData<ArrayList<Version>>()
        for (i in 0..20) {
            modelList.add(Version("$i", "Version $i", "${R.drawable.m_volkswagen}"))
        }
        finalList.value = modelList
        return finalList

    }
}