package petros.efthymiou.groovy.detail

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import petros.efthymiou.groovy.utils.BaseUnitTest

class PlaylistDetailServiceShould: BaseUnitTest() {
    private val api = mock<PlaylistDetailAPI>()
    private val service = PlaylistDetailService(api)
    private val playlistDetail = mock<PlaylistDetail>()

    @Test
    fun fetchPlaylistDetailFromApi() = runBlockingTest {
        service.fetchPlaylistDetail("1").single()

        verify(api, times(1)).getPlaylistDetails("1")
    }

    @Test
    fun convertValuesToFlowResultAndEmitThem() = runBlockingTest {
        mockSuccessCase()

        assertEquals(Result.success(playlistDetail), service.fetchPlaylistDetail("1").single())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun emitsErrorResultWhenNetworkFails() = runBlockingTest {
        whenever(api.getPlaylistDetails("1")).thenThrow(RuntimeException("Damn backend developers"))

        assertEquals("Something went wrong.", service.fetchPlaylistDetail("1").single().exceptionOrNull()?.message)
    }

    private suspend fun mockSuccessCase() {
        whenever(api.getPlaylistDetails("1")).thenReturn(playlistDetail)
    }
}