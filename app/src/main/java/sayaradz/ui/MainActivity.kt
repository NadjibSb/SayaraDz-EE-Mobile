package sayaradz.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.facebook.login.LoginManager
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GetTokenResult
import sayaradz.authentification.CreateAccountActivity
import sayaradz.authentification.R


class MainActivity : AppCompatActivity() {
    val TAG = "TAG-MainActivity"
    private var currentNavController: LiveData<NavController>? = null

    var firebaseAuth: FirebaseAuth? = null
    lateinit var idToken: String   // the access token


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.TopToolbar))
        checkAuth()

        //setupNavigation()
        setupBottomNavigationBar()

    }


    //-- checkout if auth and get the token ***********************************
    private fun checkAuth() {
        firebaseAuth = FirebaseAuth.getInstance()
        val user = firebaseAuth?.currentUser
        ///**THE TOKEN + REQUEST **///
        user?.getIdToken(true)
                ?.addOnCompleteListener(object : OnCompleteListener<GetTokenResult> {
                    override fun onComplete(task: Task<GetTokenResult>) {
                        if (task.isSuccessful()) {
                            idToken = task.getResult()!!.getToken()!!  // Having the token

                            Log.i(TAG, "TOKEN CORRECT: $idToken")
                            //________________setUpBottomNavigationBar(idToken)

                        } else {
                            // Handle error -> task.getException();
                            startActivity(Intent(this@MainActivity, CreateAccountActivity::class.java))
                        }
                    }
                })
    }
    //_____________________________________________________________


    //-- Menu Methods *********************************************
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_top_toolbar, menu)
        return true
    }
    //****
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        Toast.makeText(this, "logout", Toast.LENGTH_SHORT).show()
        firebaseAuth?.signOut()
        LoginManager.getInstance().logOut()
        startActivity(Intent(this@MainActivity, CreateAccountActivity::class.java))
        return super.onOptionsItemSelected(item)
    }
    //_____________________________________________________________


    //-- Setup buttom navigation**************************************
    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        setupBottomNavigationBar()
    }

    /**
     * Called on first creation and when restoring state.
     */
    private fun setupBottomNavigationBar() {
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        val navGraphIds = listOf(R.navigation.consult_graph, R.navigation.search_graph, R.navigation.compose_graph, R.navigation.profile_graph)

        // Setup the bottom navigation view with a list of navigation graphs
        val controller = bottomNavigationView.setupWithNavController(
                navGraphIds = navGraphIds,
                fragmentManager = supportFragmentManager,
                containerId = R.id.NavHostFragment,
                intent = intent
        )

        // Whenever the selected controller changes, setup the action bar.
        controller.observe(this, Observer { navController ->
            setupActionBarWithNavController(navController)
        })
        currentNavController = controller
    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }

    /**
     * Overriding popBackStack is necessary in this case if the app is started from the deep link.
     */
    override fun onBackPressed() {
        if (currentNavController?.value?.popBackStack() != true) {
            super.onBackPressed()
        }
    }
    //_____________________________________________________________
}


/*private fun setUpBottomNavigationBar(token: String) {
    val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
    bottomNavigationView.setOnNavigationItemSelectedListener(
            object : BottomNavigationView.OnNavigationItemSelectedListener {
                override fun onNavigationItemSelected(item: MenuItem): Boolean {
                    val fragment: androidx.fragment.app.Fragment
                    when (item.getItemId()) {
                        R.id.actionConsult -> fragment = MarqueFragment.getInstance()
                        R.id.actionProfile -> fragment = ProfileFragment.getInstance()
                        else -> fragment = SearchFragment.getInstance()
                    }
                    Log.i(TAG, "TOKEN TO SEND: $token")
                    fragment.arguments = attachArgs("TOKEN", token)
                    replaceFragment(fragment)
                    return true
                }
            })
    bottomNavigationView.selectedItemId = R.id.actionConsult
}

private fun attachArgs(tag: String, data: String): Bundle {
    var bundle = Bundle()
    bundle.putString(tag, data)
    return bundle
}

private fun replaceFragment(fragment: androidx.fragment.app.Fragment) {
    val fragmentTransaction = supportFragmentManager.beginTransaction()
    fragmentTransaction.replace(R.id.fragment_container, fragment)
    fragmentTransaction.commit()
}
_______________________________________________________

override fun onSupportNavigateUp() =  findNavController(R.id.NavHostFragment).navigateUp()
private fun setupNavigation() {
    val navController = Navigation.findNavController(this, R.id.NavHostFragment)
    setupActionBarWithNavController(this, navController)
    NavigationUI.setupWithNavController(bottomNavigationView, navController)
}
_______________________________________________________


  // this is the profile fragement in ur version ..
        Log.i(TAG, "User account ID ${user?.uid}")
        Log.i(TAG, "Display Name : ${user?.displayName}")
        Log.i(TAG, "Email : ${user?.email}")
        Log.i(TAG, "Photo URL : ${user?.photoUrl}")
        Log.i(TAG, "Provider ID : ${user?.providerId}")*/
