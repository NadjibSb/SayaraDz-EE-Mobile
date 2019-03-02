package sayaradz.authentification

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class CreateAccountActivity : AppCompatActivity(), View.OnClickListener {

    val TAG = "CreateAccountActivity"

    //Init views
    lateinit var googleSignInButton: SignInButton
    lateinit var facebookSignInButton: LoginButton

    //Request codes
    val GOOGLE_LOG_IN_RC = 1
    val FACEBOOK_LOG_IN_RC = 2

    // Google API Client object.
    var googleApiClient: GoogleApiClient? = null

    // Firebase Auth Object.
    var firebaseAuth: FirebaseAuth? = null
    //Facebook Callback manager
    var callbackManager: CallbackManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)
        Log.i(TAG, "test")

        googleSignInButton = findViewById<View>(R.id.google_sign_in_button) as SignInButton
        facebookSignInButton = findViewById<View>(R.id.facebook_sign_in_button) as LoginButton

        googleSignInButton.setOnClickListener(this@CreateAccountActivity)

        firebaseAuth = FirebaseAuth.getInstance()

        // Configure Google Sign In
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.request_client_id))
                .requestEmail()
                .build()

        // Creating and Configuring Google Api Client.
        googleApiClient = GoogleApiClient.Builder(this@CreateAccountActivity)
                .enableAutoManage(this@CreateAccountActivity  /* OnConnectionFailedListener */) {}
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build()


        //FaceBook Sign IN

        //Facebook Callback manager creation
        callbackManager = CallbackManager.Factory.create();

        facebookSignInButton.setReadPermissions("email")
        facebookSignInButton.setReadPermissions("user_friends");
        facebookSignInButton.setReadPermissions("public_profile");
        facebookSignInButton.setReadPermissions("user_birthday");

// Callback registration
        facebookSignInButton.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                // App code
                handleFacebookAccessToken(loginResult.accessToken);
            }

            override fun onCancel() {
                // App code
            }

            override fun onError(exception: FacebookException) {
                // App code

                Log.i(TAG, "FACEBOOK ERROR.")
            }
        })

    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.google_sign_in_button -> {
                Log.i(TAG, "Trying Google LogIn.")
                googleLogin()
            }

        }
    }

    private fun googleLogin() {
        Log.i(TAG, "Starting Google LogIn Flow.")
        val signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient)
        startActivityForResult(signInIntent, GOOGLE_LOG_IN_RC)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        callbackManager!!.onActivityResult(requestCode, resultCode, data)

        Log.i(TAG, "Got Result code ${requestCode}.")

        Log.i(TAG, "GOOOGLE $GOOGLE_LOG_IN_RC.")

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == GOOGLE_LOG_IN_RC) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            Log.i(TAG, "With Google LogIn, is result a success? ${result.isSuccess}.")
            if (result.isSuccess) {
                // Google Sign In was successful, authenticate with Firebase
                firebaseAuthWithGoogle(result.signInAccount!!)
            } else {
                Toast.makeText(this@CreateAccountActivity, "Some error occurred.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {

        Log.i(TAG, "Authenticating user with firebase.")
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        firebaseAuth?.signInWithCredential(credential)?.addOnCompleteListener(this) { task ->

            Log.i(TAG, "Firebase Authentication, is result a success? ${task.isSuccessful}.")
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                startActivity(Intent(this@CreateAccountActivity, MainActivity::class.java))
            } else {
                // If sign in fails, display a message to the user.
                Log.e(TAG, "Authenticating with Google credentials in firebase FAILED !!")
            }
        }
    }

    ////
    private fun handleFacebookAccessToken(token: AccessToken) {
        Log.d(TAG, "handleFacebookAccessToken:" + token)

        val credential = FacebookAuthProvider.getCredential(token.token)
        firebaseAuth!!.signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithCredential:success")
                        val user = firebaseAuth!!.currentUser
                        startActivity(Intent(this@CreateAccountActivity, MainActivity::class.java))
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithCredential:failure", task.getException())
                        Toast.makeText(this@CreateAccountActivity, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                    }
                }
    }


}