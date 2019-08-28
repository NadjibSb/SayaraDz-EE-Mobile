package sayaradz.ui.fragment.addAnnonce

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import sayaradz.dataClasses.Modele
import java.util.ArrayList

class AddAnnonceViewModelFactory(val modelList : List<Modele>) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddAnnonceViewModel::class.java)) {
            return AddAnnonceViewModel() as T
        } else
            throw IllegalArgumentException("Unknown ViewModel class")
    }
}