package com.josealfonsomora.coolappsfacebook.login

import android.content.Context
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.NoMatchingViewException
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.josealfonsomora.coolappsfacebook.robots.LoginRobot
import org.junit.*
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
open class LoginActivityTest {

    @Rule
    @JvmField
    var mActivityTestRule = IntentsTestRule(LoginActivity::class.java)

    @Before
    @After
    fun reset() {
        val prefs = InstrumentationRegistry.getTargetContext().getSharedPreferences("user_session", Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.clear()
        editor.commit()
    }

    @Test
    fun testLogin() {
        try {
            LoginRobot()
                    .performLogout()
                    .wait(1000)
                    .loginWithFacebook("dorothy_wiwnxaa_okelo@tfbnw.net\t", "1234qwerA")
                    .wait(5000)
                    .validateFacebookPermissions()
                    .wait(2000)
                    .isSuccess()
        } catch (e: NoMatchingViewException) {
            e.printStackTrace()
        }
    }

    @Test
    fun testLoginError() {
        try {
            LoginRobot()
                    .wait(1000)
                    .loginWithFacebook("dorothy_wiwnxaa_okelo@tfbnw.net", "invalid_password")
                    .wait(5000)
                    .isNotSuccess()
        } catch (e: NoMatchingViewException) {
            e.printStackTrace()
        }
    }
}
