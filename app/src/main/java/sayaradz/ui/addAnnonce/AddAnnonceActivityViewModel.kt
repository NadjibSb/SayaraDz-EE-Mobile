package sayaradz.ui.addAnnonce

import com.facebook.login.LoginManager
import com.google.firebase.auth.FirebaseAuth

class AddAnnonceActivityViewModel {


    val TAG = "TAG-AddAnnonceActivityVM"



    fun signOut() {
        FirebaseAuth.getInstance()?.signOut()
        LoginManager.getInstance().logOut()
    }
}