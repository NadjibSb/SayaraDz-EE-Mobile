package sayaradz.authentification

//import android.support.test.rule.ActivityTestRule
import android.view.View
import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class CreateAccountActivityTest {

    //@get:Rule var activityTestRule = ActivityTestRule(MainActivity::class.java)

 //   @Rule @JvmField var mActivityTestRule = ActivityTestRule(CreateAccountActivity::class.java)
    var mActivity : CreateAccountActivity? = null

    @Before
    fun setUp() {
   //     mActivity = mActivityTestRule.activity

    }
   @Test
   fun testLaunch() {

       var btnGoogle = mActivity!!.findViewById<View>(R.id.google_sign_in_button)
       var btnFb = mActivity!!.findViewById<View>(R.id.facebook_sign_in_button)
       assertNotNull(btnGoogle)
       assertNotNull(btnFb)

   }


    @After
    fun tearDown() {
     mActivity = null
    }
}

