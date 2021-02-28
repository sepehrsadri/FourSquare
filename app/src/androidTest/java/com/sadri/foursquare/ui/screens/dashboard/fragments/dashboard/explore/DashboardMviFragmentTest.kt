package com.sadri.foursquare.ui.screens.dashboard.fragments.dashboard.explore

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.GrantPermissionRule
import com.sadri.foursquare.R
import com.sadri.foursquare.data.repositories.explore.ExploreDataSingleSourceOfTruth
import com.sadri.foursquare.data.repositories.explore.ExploreResult
import com.sadri.foursquare.data.utils.Result
import com.sadri.foursquare.ui.screens.dashboard.DashboardActivity
import com.sadri.foursquare.utils.TEST_TIMEOUT_MS
import com.sadri.foursquare.utils.idling_resource.EspressoIdlingResourceRule
import com.sadri.foursquare.utils.idling_resource.waitUntilViewIsDisplayed
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@HiltAndroidTest
@LargeTest
class DashboardMviFragmentTest {

    var espressoIdlingResourceRule = EspressoIdlingResourceRule()
    var hiltRule = HiltAndroidRule(this)
    var activityScenarioRule = activityScenarioRule<DashboardActivity>()

    var accessFineLocationPermissionRule =
        GrantPermissionRule.grant(android.Manifest.permission.ACCESS_FINE_LOCATION)
    var accessCoarseLocationPermissionRule =
        GrantPermissionRule.grant(android.Manifest.permission.ACCESS_COARSE_LOCATION)

    @get:Rule
    var rulesChain = RuleChain
        .outerRule(espressoIdlingResourceRule)
        .around(accessFineLocationPermissionRule)
        .around(accessCoarseLocationPermissionRule)
        .around(hiltRule)
        .around(activityScenarioRule)

    @Inject
    lateinit var exploreDataSingleSourceOfTruth: ExploreDataSingleSourceOfTruth

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test(timeout = TEST_TIMEOUT_MS)
    fun test1FirstListItemIsDisplayedAsExpected() {
        waitUntilViewIsDisplayed(withId(R.id.rcvVenues))

        onView(withId(R.id.rcvVenues))
            .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(0, ViewActions.scrollTo()))

        val fetchedItem = exploreDataSingleSourceOfTruth.getFetchedList()
            .first() as? Result.Success<ExploreResult> ?: throw RuntimeException("test failed")

        onView(
            withText(
                fetchedItem.data.result.first().formattedName
            )
        ).check(matches(isDisplayed()))
    }

    @Test(timeout = TEST_TIMEOUT_MS)
    fun test2ClickOnFirstListItemNavigateToDetailScreen() {
        waitUntilViewIsDisplayed(withId(R.id.rcvVenues))

        onView(withId(R.id.rcvVenues))
            .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(0, ViewActions.scrollTo()))

        val fetchedItem = exploreDataSingleSourceOfTruth.getFetchedList()
            .first() as? Result.Success<ExploreResult> ?: throw RuntimeException("test failed")

        val itemText = fetchedItem.data.result.first().formattedName

        onView(
            withText(
                itemText
            )
        ).perform(click())

        onView(
            withId(R.id.detailContainer)
        ).check(matches(isDisplayed()))

        onView(
            withId(
                R.id.nameTv
            )
        ).check(matches(withText(itemText)))
    }

}