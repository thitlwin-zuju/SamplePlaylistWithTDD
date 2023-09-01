package petros.efthymiou.groovy.playlist

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import petros.efthymiou.groovy.utils.BaseUnitTest

class PlaylistServiceShould: BaseUnitTest() {

    val api = mock<PlaylistAPI>()
    private val service = PlaylistService(api)
    private val playList = mock<List<Playlist>>()

    @Test
    fun fetchPlaylistsFromApi() = runBlockingTest {

        service.fetchPlaylists().first()

        verify(api, times(1)).getPlayList()
    }

    @Test
    fun convertValuesToFlowResultAndEmitThem() = runBlockingTest {
        mockSuccessCase()

        assertEquals(Result.success(playList), service.fetchPlaylists().first())
    }

    @Test
    fun emitsErrorResultWhenNetworkFails() = runBlockingTest {
        mockErrorCase()

        assertEquals("Something went wrong.", service.fetchPlaylists().first().exceptionOrNull()?.message)
    }

    private suspend fun mockSuccessCase() {
        whenever(api.getPlayList()).thenReturn(playList)
    }

    private suspend fun mockErrorCase() {
        val exception = RuntimeException("Damn backend developers")
        whenever(api.getPlayList()).thenThrow(exception)
    }
}