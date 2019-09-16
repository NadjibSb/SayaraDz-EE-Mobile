package sayaradz.ui.fragment.myOffers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import sayaradz.ui.fragment.myAnnonceView.MyAnnonceViewViewModel

class MyAnnonceOffersViewModelFactory (val token : String , val annonceId:String) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyAnnonceOffersViewModel::class.java)) {
            return MyAnnonceOffersViewModel(token,annonceId) as T
        } else
            throw IllegalArgumentException("Unknown ViewModel class")
    }
}