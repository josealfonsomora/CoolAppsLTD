package com.josealfonsomora.coolappsfacebook.robots

import android.support.test.InstrumentationRegistry
import android.support.test.InstrumentationRegistry.getInstrumentation
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.espresso.web.sugar.Web
import android.support.test.espresso.web.webdriver.DriverAtoms
import android.support.test.espresso.web.webdriver.DriverAtoms.findElement
import android.support.test.espresso.web.webdriver.Locator
import android.support.test.uiautomator.UiDevice
import android.support.test.uiautomator.UiObjectNotFoundException
import android.support.test.uiautomator.UiSelector


class LoginRobot {

    fun wait(millis: Long): LoginRobot {
        try {
            Thread.sleep(millis)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        return this
    }

    fun loginWithFacebook(email: String, password: String): LoginRobot {
        try {
            Web.onWebView().withElement(DriverAtoms.findElement(Locator.ID, "m_login_email")).perform(DriverAtoms.webKeys(email))
            Web.onWebView().withElement(DriverAtoms.findElement(Locator.ID, "m_login_password")).perform(DriverAtoms.webKeys(password))
            Web.onWebView().withElement(DriverAtoms.findElement(Locator.ID, "u_0_5")).perform(DriverAtoms.webClick())

            wait(10000)

            Web.onWebView().withElement(DriverAtoms.findElement(Locator.ID, "u_0_3")).perform(DriverAtoms.webClick())

        } catch (e: Exception) {
            // Chrome Tabs
            e.printStackTrace()
            val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
            val selector = UiSelector()

            val chromeButton = device.findObject(selector.className("android.widget.Button"))
            try {
                chromeButton.click()
            } catch (ex: UiObjectNotFoundException) {
                ex.printStackTrace()
            }

            val continueButton = device.findObject(selector.className("android.widget.Button").index(0))
            try {
                continueButton.click()
            } catch (ex: UiObjectNotFoundException) {
                ex.printStackTrace()
            }

            val emailText = device.findObject(selector.resourceId("m_login_email"))
            try {
                emailText.text = email
            } catch (ex: UiObjectNotFoundException) {
                ex.printStackTrace()
            }

            val loginButton = device.findObject(selector.className("android.widget.Button"))
            try {
                loginButton.click()
            } catch (ex: UiObjectNotFoundException) {
                ex.printStackTrace()
            }

            val passwordText = device.findObject(selector.resourceId("m_login_password"))
            try {
                passwordText.text = password
            } catch (ex: UiObjectNotFoundException) {
                ex.printStackTrace()
            }

            try {
                loginButton.click()
            } catch (ex: UiObjectNotFoundException) {
                ex.printStackTrace()
            }

            val confirmButton = device.findObject(selector.resourceId("u_0_3"))
            try {
                confirmButton.click()
            } catch (ex: UiObjectNotFoundException) {
                ex.printStackTrace()
            }
        }
        return this
    }

    fun isSuccess(): LoginRobot {
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText("Log out")).perform(click());
        return this
    }

    fun isNotSuccess(): LoginRobot {
        Web.onWebView().withElement(findElement(Locator.ID, "u_0_5"))

        return this;
    }

    fun validateFacebookPermissions(): LoginRobot {
        try {
            Web.onWebView().withElement(DriverAtoms.findElement(Locator.ID, "u_0_9")).perform(DriverAtoms.webClick())
        } catch (exception: Exception) {
            // No confirmation needed. Continue
        }

        return this
    }

    fun performLogout(): LoginRobot {
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText("Log out")).perform(click());
        return this
    }
}
