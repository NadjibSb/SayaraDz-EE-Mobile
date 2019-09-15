package sayaradz.ui.fragment.myOffers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import sayaradz.ui.fragment.myAnnonceView.MyAnnonceViewViewModel

class MyOffersViewModelFactory (val token : String ) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyAnnonceViewViewModel::class.java)) {
            return MyOffersViewModel(token) as T
        } else
            throw IllegalArgumentException("Unknown ViewModel class")
    }
}