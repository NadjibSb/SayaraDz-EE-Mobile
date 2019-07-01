package sayaradz.ui.fragment.annonce

import sayaradz.ui.fragment.version.VersionViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class HomeViewModelFactory(val userId: String): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AnnonceViewModel::class.java)){
            return VersionViewModel(userId) as T
        }else
            throw IllegalArgumentException("Unknown ViewModel class")
    }
}
