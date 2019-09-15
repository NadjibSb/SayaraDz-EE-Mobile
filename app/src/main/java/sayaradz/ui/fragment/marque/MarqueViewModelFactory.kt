package sayaradz.ui.fragment.marque

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MarqueViewModelFactory(val token: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MarqueViewModel::class.java)) {
            return MarqueViewModel(token) as T
        } else
            throw IllegalArgumentException("Unknown ViewModel class")
    }
}