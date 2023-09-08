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
    private val mapper: PlaylistMapper = mock()
    private val playlist = mock<List<Playlist>>()
    private val playlistRaw = mock<List<PlaylistRaw>>()
    private val exception = RuntimeException("Something went wrong")

    @Test
    fun getPlaylistsFromService() = runBlockingTest {
        val repository = mockSuccessfulCase()

        repository.getPlaylists()

        verify(service, times(1)).fetchPlaylists()
    }

    @Test
    fun propagateError() = runBlockingTest {
        val repository = mockErrorCase()

        assertEquals(exception, repository.getPlaylists().first().exceptionOrNull())
    }

    @Test
    fun emitPlaylistsFromService() = runBlockingTest {
        val repository = mockSuccessfulCase()

        assertEquals(playlist, repository.getPlaylists().first().getOrNull())
    }

    @Test
    fun delegateBusinessLogicToMapper() = runBlockingTest {
        val repository = mockSuccessfulCase()

        repository.getPlaylists().first()

        verify(mapper, times(1)).invoke(playlistRaw)
    }

    private suspend fun mockSuccessfulCase(): PlaylistRepository {
        whenever(service.fetchPlaylists()).thenReturn(
            flow {
                emit(Result.success(playlistRaw))
            }
        )

        whenever(mapper.invoke(playlistRaw)).thenReturn(playlist)

        return PlaylistRepository(service, mapper)
    }

    private suspend fun mockErrorCase(): PlaylistRepository {
        whenever(service.fetchPlaylists()).thenReturn(
            flow {
                emit(Result.failure<List<PlaylistRaw>>(exception))
            }
        )
        return PlaylistRepository(service, mapper)
    }

}