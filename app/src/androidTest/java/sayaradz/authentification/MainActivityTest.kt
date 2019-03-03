package sayaradz.authentification

import android.support.design.widget.BottomNavigationView
import android.support.test.rule.ActivityTestRule
import android.view.View
import org.junit.*

class MainActivityTest {

        //@get:Rule var activityTestRule = ActivityTestRule(MainActivity::class.java)

        @Rule
        @JvmField var mActivityTestRule = ActivityTestRule(MainActivity::class.java)
        var mActivity : MainActivity? = null

        @Before
        fun setUp() {
            mActivity = mActivityTestRule.activity

        }
        @Test
        fun testBottom() {

            var bottom = mActivity!!.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
            Assert.assertNotNull(bottom)

        }


        @After
        fun tearDown() {
            mActivity = null
        }
    }
