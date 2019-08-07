package sayaradz.ui.fragment.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import sayaradz.api.Api
import sayaradz.dataClasses.Modele

class ModelViewModel(val marqueId: String) : ViewModel() {

    val TAG = "ModelViewModel"
    var models: MutableLiveData<ArrayList<Modele>>

    init {
        models = Api.getModels(TAG, "token", marqueId)
    }

    private fun defaultList(): MutableLiveData<ArrayList<Modele>> {

        var modelList = ArrayList<Modele>()
        var finalList = MutableLiveData<ArrayList<Modele>>()
        for (i in 0..20) {
            //modelList.add(Modele("$i", "Modele $i", "Marque $marqueId", arrayListOf()))//, "${R.drawable.m_volkswagen}"))

        }
        finalList.value = modelList
        return finalList

    }


}