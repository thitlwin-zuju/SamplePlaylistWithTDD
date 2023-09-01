package petros.efthymiou.groovy.playlist

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.times
import petros.efthymiou.groovy.utils.BaseUnitTest

class PlaylistRepositoryShould: BaseUnitTest() {

    private val service: PlaylistService = mock()
    private val repository = PlaylistRepository(service)
    private val playlist = mock<List<Playlist>>()
    private val exception = RuntimeException("Something went wrong")

    @Test
    fun getPlaylistsFromService() = runBlockingTest {
        repository.getPlaylists()

        verify(service, times(1)).fetchPlaylists()
    }

    @Test
    fun propagateError() = runBlockingTest {
        mockErrorCase()

        assertEquals(exception, repository.getPlaylists().first().exceptionOrNull())
    }

    private suspend fun mockErrorCase() {
        whenever(repository.getPlaylists()).thenReturn(
            flow {
                emit(Result.failure<List<Playlist>>(exception))
            }
        )
    }

    @Test
    fun emitPlaylistsFromService() = runBlockingTest {
        mockSuccessfulCase()

        assertEquals(playlist, repository.getPlaylists().first().getOrNull())
    }

    private suspend fun mockSuccessfulCase() {
        whenever(repository.getPlaylists()).thenReturn(
            flow {
                emit(Result.success(playlist))
            }
        )
    }
}