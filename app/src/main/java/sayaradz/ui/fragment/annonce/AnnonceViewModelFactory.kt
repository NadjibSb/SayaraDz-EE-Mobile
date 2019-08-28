package sayaradz.ui.fragment.annonce

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import sayaradz.ui.fragment.model.ModelViewModel

class AnnonceViewModelFactory (val annonceId: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AnnonceViewModel::class.java)) {
            return AnnonceViewModel(annonceId) as T
        } else
            throw IllegalArgumentException("Unknown ViewModel class")
    }

}