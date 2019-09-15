package sayaradz.ui.fragment.version

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class VersionViewModelFactory(val modelId: String, val token: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VersionViewModel::class.java)) {
            return VersionViewModel(modelId, token) as T
        } else
            throw IllegalArgumentException("Unknown ViewModel class")
    }
}