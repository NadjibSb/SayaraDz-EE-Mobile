package sayaradz.ui.fragment.marque

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import sayaradz.api.Api
import sayaradz.authentification.R
import sayaradz.dataClasses.Marque

class MarqueViewModel(val token: String) : ViewModel() {

    private val TAG = "MarqueViewModel"
    var marques: MutableLiveData<ArrayList<Marque>>

    init {
        marques = Api.getMarques(TAG, token)
    }

    fun getMarques(){
        marques = Api.getMarques(TAG, token)
    }

    private fun defaultList(): MutableLiveData<ArrayList<Marque>> {

        var finalList = MutableLiveData<ArrayList<Marque>>()
        var marqueList = ArrayList<Marque>()
        for (i in 0..20) {
            marqueList.add(Marque(i.toString(), "Marque $i", "${R.drawable.m_audi}"))
        }
        finalList.value = marqueList
        return finalList

    }


}