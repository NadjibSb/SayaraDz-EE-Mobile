package sayaradz.ui.fragment.fichTech

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import sayaradz.api.Api
import sayaradz.dataClasses.FichTech

class FicheTechViewModel(versionID: String) : ViewModel() {


    val TAG = "FichTechViewModel"
    var token = ""


    private val _version = Api.getVersionDetails(TAG,token,versionID)
    private val id = _version.ficheTechnique_id

    private val _fichTech = Api.getFichTech(TAG, token, id)
    val fichTech: LiveData<FichTech?>
        get() = _fichTech

}
