package sayaradz.ui.fragment.fichTech

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class FicheTechViewModelFactory(private val versionID: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FicheTechViewModel::class.java))
            return FicheTechViewModel(versionID) as T
        else
            throw IllegalArgumentException("Unknown ViewModel class")
    }
}