package sayaradz.authentification

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GetTokenResult
import android.view.Menu
import android.widget.Toast
import com.facebook.login.LoginManager


class MainActivity : AppCompatActivity() {

    val TAG = "TAG-MainActivity"

    var firebaseAuth: FirebaseAuth? = null
    lateinit var idToken: String   // the access token


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.TopToolbar))

        firebaseAuth = FirebaseAuth.getInstance()
        val user = firebaseAuth?.currentUser
        ///**THE TOKEN + REQUEST **///
        user?.getIdToken(true)
                ?.addOnCompleteListener(object : OnCompleteListener<GetTokenResult> {
                    override fun onComplete(task: Task<GetTokenResult>) {
                        if (task.isSuccessful()) {

                            idToken = task.getResult()!!.getToken()!!  // Having the token

                            Log.i(TAG, "TOKEN CORRECT: $idToken")
                            setUpBottomNavigationBar(idToken)

                        } else {
                            // Handle error -> task.getException();
                            startActivity(Intent(this@MainActivity,CreateAccountActivity::class.java))
                        }
                    }
                })
    }


    private fun setUpBottomNavigationBar(token: String) {
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
              Log.i(TAG, "TOKEN TO SEND: $token")
                        fragment.arguments = attachArgs("TOKEN", token)
                        replaceFragment(fragment)
                        return true
                    }
                })
        bottomNavigationView.selectedItemId = R.id.nav_home
    }

    private fun attachArgs(tag: String, data: String): Bundle {
        var bundle = Bundle()
        bundle.putString(tag, data)
        return bundle
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_top_toolbar,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        Toast.makeText(this,"logout",Toast.LENGTH_SHORT).show()
        firebaseAuth?.signOut()
        LoginManager.getInstance().logOut()
        startActivity(Intent(this@MainActivity,CreateAccountActivity::class.java))
        return super.onOptionsItemSelected(item)
    }

    /*/// this is the profile fragement in ur version ..
        Log.i(TAG, "User account ID ${user?.uid}")
        Log.i(TAG, "Display Name : ${user?.displayName}")
        Log.i(TAG, "Email : ${user?.email}")
        Log.i(TAG, "Photo URL : ${user?.photoUrl}")
        Log.i(TAG, "Provider ID : ${user?.providerId}")*/
}
