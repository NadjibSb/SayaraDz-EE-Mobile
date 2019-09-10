package sayaradz.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.facebook.login.LoginManager
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GetTokenResult

class MainActivityViewModel : ViewModel() {

    val TAG = "TAG-MainActivityVM"
    companion object {
       lateinit var token: String
    }


    fun isAuth(): LiveData<Boolean> {
        val user = FirebaseAuth.getInstance()?.currentUser
        val isAuth = MutableLiveData<Boolean>()
        user?.getIdToken(true)
                ?.addOnCompleteListener(object : OnCompleteListener<GetTokenResult> {
                    override fun onComplete(task: Task<GetTokenResult>) {
                        if (task.isSuccessful()) {
                            token = task.getResult()!!.getToken()!!
                            Log.i(TAG, "TOKEN CORRECT: $token")
                            isAuth.value = true

                        } else {
                            isAuth.value = false
                        }
                    }
                })
        return isAuth
    }
    fun getToken(): String {

        val user = FirebaseAuth.getInstance()?.currentUser
        user?.getIdToken(true)
                ?.addOnCompleteListener(object : OnCompleteListener<GetTokenResult> {
                    override fun onComplete(task: Task<GetTokenResult>) {
                        if (task.isSuccessful()) {
                            token = task.getResult()!!.getToken()!!  // Having the token
                            Log.i("MainActivity", "TOKEN CORRECT: $token")
                        }
                    }
                })

        return token
    }



    fun signOut() {
        FirebaseAuth.getInstance()?.signOut()
        LoginManager.getInstance().logOut()
    }
}