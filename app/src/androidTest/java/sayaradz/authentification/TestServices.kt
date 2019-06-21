package sayaradz.authentification

//import android.support.test.runner.AndroidJUnit4
import androidx.test.runner.AndroidJUnit4
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GetTokenResult
import junit.framework.TestCase
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import sayaradz.api.ServiceProvider

@RunWith(AndroidJUnit4::class)
class TestServices : TestCase() {
@Test
public fun testResponse ()
{

    lateinit var idToken : String   // the access token

    var firebaseAuth = FirebaseAuth.getInstance()
    val user = firebaseAuth?.currentUser
    user?.getIdToken(true)
            ?.addOnCompleteListener(object : OnCompleteListener<GetTokenResult> {
                override  fun onComplete(task: Task<GetTokenResult>) {

                    //if (task.isSuccessful()) {

                        idToken = task.getResult()!!.getToken()!!  // Having the token
                        val retrofit = Retrofit.Builder()
                                .baseUrl("https://sayaradz-ee-backend.herokuapp.com/api/")
                                .addConverterFactory(GsonConverterFactory.create())
                                .build()
                        val jsonPlaceHolderApi = retrofit.create(ServiceProvider::class.java)
                    var response = jsonPlaceHolderApi.getMarques(idToken).execute()
                       var marques=response.body()

                    assertEquals(15,marques!!.size)
                    assertEquals("200",response.code().toString())

                }
            })






}





}