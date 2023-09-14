package com.example.coroutinetest

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.coroutinetest.ui.MainActivity
import org.hamcrest.Matcher
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class EspressoTest {
    @get:Rule
    public val activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @Test
    fun `Espresso_기본테스트`() {
        onView(withId(R.id.btLoadInfo))
            .perform(click())

        onView(withId(R.id.btRandomTest))
            .perform(click())

//        onView(withId(R.id.tvInfo))
//            .check(ViewAssertions.matches(isDisplayed()))

        onView(isRoot()).perform(waitFor(3000))

        onView(withId(R.id.tvInfo))
            .check(ViewAssertions.matches(isDisplayed()))
    }

    private fun waitFor(delay: Long): ViewAction? {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> = isRoot()
            override fun getDescription(): String = "wait for $delay milliseconds"
            override fun perform(uiController: UiController, v: View?) {
                uiController.loopMainThreadForAtLeast(delay)
            }
        }
    }
}
