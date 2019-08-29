package sayaradz.ui.fragment.myAnnonceView

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MyAnnonceViewViewModelFactory (val annonceId: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyAnnonceViewViewModel::class.java)) {
            return MyAnnonceViewViewModel(annonceId) as T
        } else
            throw IllegalArgumentException("Unknown ViewModel class")
    }
}