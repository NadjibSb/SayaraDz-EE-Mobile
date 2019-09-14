package sayaradz.ui.mainActivity

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.facebook.login.LoginManager
import com.google.firebase.auth.FirebaseAuth

class MainActivityViewModel : ViewModel() {

    val TAG = "TAG-MainActivityVM"
    companion object {
        var token: String? = ""
    }

    fun isAuth(): LiveData<Boolean> {
        val user = FirebaseAuth.getInstance()?.currentUser
        val isAuth = MutableLiveData<Boolean>()
        user?.getIdToken(true)
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        token = task.result!!.token!!
                        Log.i(TAG, "TOKEN CORRECT: $token")
                        isAuth.value = true

                    } else {
                        isAuth.value = false
                    }
                }
        return isAuth
    }

    fun getToken(): String {
        if (token.equals("")) {
            val user = FirebaseAuth.getInstance()?.currentUser
            user?.getIdToken(true)
                    ?.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            token = task.result!!.token!!  // Having the token
                            Log.i(TAG, "TOKEN CORRECT: $token")
                        }
                    }
        }
        return token!!
    }

    fun signOut() {
        FirebaseAuth.getInstance()?.signOut()
        LoginManager.getInstance().logOut()
    }
}