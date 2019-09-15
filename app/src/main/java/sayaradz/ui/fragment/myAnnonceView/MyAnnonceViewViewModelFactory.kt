package sayaradz.ui.fragment.myAnnonceView

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MyAnnonceViewViewModelFactory (val annonceId: String , val token : String ) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyAnnonceViewViewModel::class.java)) {
            return MyAnnonceViewViewModel(annonceId, token ) as T
        } else
            throw IllegalArgumentException("Unknown ViewModel class")
    }
}