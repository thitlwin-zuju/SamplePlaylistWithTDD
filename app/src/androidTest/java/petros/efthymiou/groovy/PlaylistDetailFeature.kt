package petros.efthymiou.groovy

import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertNotDisplayed
import org.hamcrest.core.AllOf
import org.junit.Rule
import org.junit.Test
import petros.efthymiou.groovy.playlist.idlingResource

class PlaylistDetailFeature: BaseUITest() {

    val mainActivityRule = ActivityScenarioRule(MainActivity::class.java)
        @Rule get

    @Test
    fun displaysLoaderWhileFetchingThePlaylistDetails() {
        IdlingRegistry.getInstance().unregister(idlingResource)

        Thread.sleep(1001)

        navigateToPlaylistDetails(0)

        assertDisplayed(R.id.detail_loader)
    }

    @Test
    fun hideLoader() {
        navigateToPlaylistDetails(0)

        assertNotDisplayed(R.id.detail_loader)
    }

    @Test
    fun displayPlayListNameAndDetail() {
        navigateToPlaylistDetails(0)

        assertDisplayed(R.id.tv_playlist_detail_details)
        assertDisplayed("Rock your senses with this timeless signature vibe list. \n\n • Poison \n • You shook me all night \n • Zombie \n • Rock'n Me \n • Thunderstruck \n • I Hate Myself for Loving you \n • Crazy \n • Knockin' on Heavens Door")
    }

    @Test
    fun displaysErrorMessage() {
        navigateToPlaylistDetails(1)

        assertDisplayed(R.string.generic_error)
    }

    private fun navigateToPlaylistDetails(row: Int) {
        Espresso.onView(
            AllOf.allOf(
                ViewMatchers.withId(R.id.imv_playlist),
                ViewMatchers.isDescendantOfA(
                    nthChildOf(
                        ViewMatchers.withId(R.id.playlists_list),
                        row
                    )
                )
            )
        ).perform(ViewActions.click())
    }
}