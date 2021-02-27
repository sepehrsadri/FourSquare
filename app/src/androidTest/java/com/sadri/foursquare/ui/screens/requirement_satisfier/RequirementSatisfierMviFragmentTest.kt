package com.sadri.foursquare.ui.screens.requirement_satisfier

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiObject
import androidx.test.uiautomator.UiSelector
import com.sadri.foursquare.R
import com.sadri.foursquare.ui.screens.dashboard.DashboardActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

@RunWith(AndroidJUnit4::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@HiltAndroidTest
@LargeTest
class RequirementSatisfierMviFragmentTest {

    var hiltRule = HiltAndroidRule(this)
    var activityScenarioRule = activityScenarioRule<DashboardActivity>()

    @get:Rule
    var rulesChain = RuleChain
        .outerRule(hiltRule)
        .around(activityScenarioRule)


    @Before
    fun setup() {
    }

    @Test
    fun test1UiElementInitAsExpected() {
        onView(withId(R.id.actionBtn)).check(
            matches(
                withText(
                    R.string.permission_action
                )
            )
        )
    }

    @Test
    fun test2ClickOnAllowGpsButtonWillShowPermissionDialog() {
        onView(withId(R.id.actionBtn)).perform(
            click()
        )

        val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

        val allowButton: UiObject = device.findObject(UiSelector().text("Allow"))

        assert(
            allowButton.exists()
        )
    }

    @Test
    fun test3AcceptPermissionWillNavigateToDashboard() {
        onView(withId(R.id.actionBtn)).perform(
            click()
        )

        val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

        val allowButton: UiObject = device.findObject(
            UiSelector()
                .clickable(true)
                .checkable(false)
                .index(1)
        )

        if (allowButton.exists()) {
            allowButton.click()
        }

        onView(
            withId(
                R.id.progressBarContainer
            )
        ).check(
            matches(
                isDisplayed()
            )
        )
    }

}