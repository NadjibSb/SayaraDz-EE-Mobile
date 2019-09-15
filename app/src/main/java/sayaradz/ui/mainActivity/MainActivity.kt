package sayaradz.ui.mainActivity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import sayaradz.authentification.CreateAccountActivity
import sayaradz.authentification.R
import sayaradz.authentification.databinding.ActivityMainBinding
import sayaradz.ui.setupWithNavController


class MainActivity : AppCompatActivity() {
    companion object {
        var active = false
    }
    val TAG = "TAG-MainActivity"
    private lateinit var binding: ActivityMainBinding
    private var currentNavController: LiveData<NavController>? = null
    private lateinit var mainActivityViewModel: MainActivityViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
        mainActivityViewModel.isAuth().observe(this, Observer { isAuth ->
            if (!isAuth)
                Toast.makeText(this, "Not connected", Toast.LENGTH_SHORT).show()
            //startActivity(Intent(this@MainActivity, CreateAccountActivity::class.java))
        })

        //Setup UI elements
        setSupportActionBar(binding.TopToolbar)
        setupBottomNavigationBar()
        //setupNavigation()
    }

    override fun onStart() {
        super.onStart()
        active=true
        mainActivityViewModel.isAuth().observe(this, Observer { isAuth ->
            if (!isAuth)
                Toast.makeText(this, "Not connected", Toast.LENGTH_SHORT).show()
                //startActivity(Intent(this@MainActivity, CreateAccountActivity::class.java))
        })
    }

    override fun onStop() {
        super.onStop()
        active=false
    }

    /**
     ****************Menu Methods
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_top_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        mainActivityViewModel.signOut()
        Toast.makeText(this, "Sign out", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this@MainActivity, CreateAccountActivity::class.java))
        return super.onOptionsItemSelected(item)
    }
    /**_____________________________________________________________**/


    /**
     ****************Setup buttom navigation
     */
    private fun setupBottomNavigationBar() {
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        val navGraphIds = listOf(R.navigation.new_car_graph, R.navigation.search_graph, R.navigation.home_graph, R.navigation.profile_graph)

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

    override fun onBackPressed() {
        if (currentNavController?.value?.popBackStack() != true) {
            super.onBackPressed()
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        setupBottomNavigationBar()
    }

    /**_____________________________________________________________**/

    fun getToken(): String {
        return mainActivityViewModel.getToken()
    }

    fun isAuth(): LiveData<Boolean> {
        return mainActivityViewModel.isAuth()
    }
}


/*private fun setUpBottomNavigationBar(token: String) {
    val bottomNavigationView = findViewById<BottomNavigationView>(R.code.bottomNavigationView)
    bottomNavigationView.setOnNavigationItemSelectedListener(
            object : BottomNavigationView.OnNavigationItemSelectedListener {
                override fun onNavigationItemSelected(item: MenuItem): Boolean {
                    val fragment: androidx.fragment.app.Fragment
                    when (item.getItemId()) {
                        R.code.actionConsult -> fragment = MarqueFragment.getInstance()
                        R.code.actionProfile -> fragment = ProfileFragment.getInstance()
                        else -> fragment = SearchFragment.getInstance()
                    }
                    Log.i(TAG, "TOKEN TO SEND: $token")
                    fragment.arguments = attachArgs("TOKEN", token)
                    replaceFragment(fragment)
                    return true
                }
            })
    bottomNavigationView.selectedItemId = R.code.actionConsult
}

private fun attachArgs(tag: String, data: String): Bundle {
    var bundle = Bundle()
    bundle.putString(tag, data)
    return bundle
}

private fun replaceFragment(fragment: androidx.fragment.app.Fragment) {
    val fragmentTransaction = supportFragmentManager.beginTransaction()
    fragmentTransaction.replace(R.code.fragment_container, fragment)
    fragmentTransaction.commit()
}
_______________________________________________________

override fun onSupportNavigateUp() =  findNavController(R.code.NavHostFragment).navigateUp()
private fun setupNavigation() {
    val navController = Navigation.findNavController(this, R.code.NavHostFragment)
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
