package sayaradz.ui.fragment.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ModelViewModelFactory(val marqueId: String, val token: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ModelViewModel::class.java)) {
            return ModelViewModel(marqueId,token) as T
        } else
            throw IllegalArgumentException("Unknown ViewModel class")
    }
}