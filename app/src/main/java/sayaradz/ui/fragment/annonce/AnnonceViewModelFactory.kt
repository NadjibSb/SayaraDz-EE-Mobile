package sayaradz.ui.fragment.annonce

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AnnonceViewModelFactory(val annonceId: String, val token: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AnnonceViewModel::class.java)) {
            return AnnonceViewModel(annonceId, token) as T
        } else
            throw IllegalArgumentException("Unknown ViewModel class")
    }

}