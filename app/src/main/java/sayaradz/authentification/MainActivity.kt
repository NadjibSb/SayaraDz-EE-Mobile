package sayaradz.authentification

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import android.support.design.widget.BottomNavigationView
import android.view.MenuItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sayaradz.services.JsonPlaceHolderApi
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import com.google.firebase.auth.GetTokenResult
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task


class MainActivity : AppCompatActivity() {
    val TAG = "MainActivityTesttt"
    // Firebase Auth Object.
    var firebaseAuth: FirebaseAuth? = null
    lateinit var idToken : String   // the access token



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var token="test"
        firebaseAuth = FirebaseAuth.getInstance()
        val user = firebaseAuth?.currentUser

        Log.i(TAG,firebaseAuth?.currentUser.toString())

        ///**THE TOKEN + REQUEST **///
        user?.getIdToken(true)
                ?.addOnCompleteListener(object : OnCompleteListener<GetTokenResult> {
                    override  fun onComplete(task: Task<GetTokenResult>) {
                        Log.i(TAG,"before if")
                        if (task.isSuccessful()) {
                            Log.i(TAG,"inside if")
                            token = task.getResult()!!.getToken()!!  // Having the token
                        } else {
                            // Handle error -> task.getException();
                        }
                    }
                })
        Log.i(TAG,token)
        idToken = token

        setUpBottomNavigationBar(idToken)
    }

    private fun getUserToken(): String {
        var token="test"
        firebaseAuth = FirebaseAuth.getInstance()
        val user = firebaseAuth?.currentUser

        Log.i("user",user?.email)

        ///**THE TOKEN + REQUEST **///
        user?.getIdToken(true)
                ?.addOnCompleteListener(object : OnCompleteListener<GetTokenResult> {
                    override  fun onComplete(task: Task<GetTokenResult>) {
                        Log.i(TAG,"before if")
                        if (task.isSuccessful()) {
                            Log.i(TAG,"inside if")
                            token = task.getResult()!!.getToken()!!  // Having the token
                        } else {
                            // Handle error -> task.getException();
                        }
                    }
                })
        Log.i("TOKEN2",token)
        return token

/*/// this is the profile fragement in ur version ..
        Log.i(TAG, "User account ID ${user?.uid}")
        Log.i(TAG, "Display Name : ${user?.displayName}")
        Log.i(TAG, "Email : ${user?.email}")
        Log.i(TAG, "Photo URL : ${user?.photoUrl}")
        Log.i(TAG, "Provider ID : ${user?.providerId}")*/
    }

    private fun setUpBottomNavigationBar(token: String){
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener(
                object : BottomNavigationView.OnNavigationItemSelectedListener {
                    override fun onNavigationItemSelected(item: MenuItem): Boolean {
                        val fragment: Fragment
                        when (item.getItemId()) {
                            R.id.nav_home -> fragment = MarqueFragment.getInstance()
                            R.id.nav_profile -> fragment = ProfileFragment.getInstance()
                            else -> fragment = MarqueFragment.getInstance()
                        }
                        fragment.arguments = attachArgs("Token",token)
                        replaceFragment(fragment)
                        return true
                    }
                })
        bottomNavigationView.selectedItemId = R.id.nav_home
    }

    private fun attachArgs(tag:String,data:String): Bundle {
        var bundle = Bundle()
        bundle.putString(tag,data)
        return bundle
    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.commit()
    }

}
