package sayaradz.ui.fragment.myAnnonce

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MyAnnonceViewModelFactory ( val token: String) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MyAnnonceViewModel::class.java)) {
                return MyAnnonceViewModel(token) as T
            } else
                throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
