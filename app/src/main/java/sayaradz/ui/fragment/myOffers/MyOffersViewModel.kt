package sayaradz.ui.fragment.myOffers

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import sayaradz.dataClasses.Offer

class MyOffersViewModel : ViewModel() {

    var offers: MutableLiveData<ArrayList<Offer>>

    init {
        offers = defaultList()
    }

    private fun defaultList(): MutableLiveData<ArrayList<Offer>> {

        var finalList = MutableLiveData<ArrayList<Offer>>()
        var list = ArrayList<Offer>()
        for (i in 0..3) {
            list.add(Offer(
                    "Clio 4 2017",
                    System.currentTimeMillis(),
                    "https://www.paycar.fr/wp-content/uploads/voiture-occasion-espagne.jpg",
                    950000
            ))
        }
        finalList.value = list
        return finalList
    }
}
