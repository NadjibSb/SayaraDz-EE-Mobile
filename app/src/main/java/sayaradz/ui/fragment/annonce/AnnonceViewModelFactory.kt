package sayaradz.ui.fragment.annonce

import sayaradz.ui.fragment.version.VersionViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class AnnonceViewModelFactory(): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AnnonceViewModel::class.java)){
            return AnnonceViewModel() as T
        }else
            throw IllegalArgumentException("Unknown ViewModel class")
    }
}
