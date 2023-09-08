package petros.efthymiou.groovy.playlist

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

import org.mockito.Mockito.verify
import petros.efthymiou.groovy.utils.BaseUnitTest
import petros.efthymiou.groovy.utils.captureValues
import petros.efthymiou.groovy.utils.getValueForTest

class PlaylistViewModelShould: BaseUnitTest() {

    private val repository: PlaylistRepository = mock()
    private val playlists = mock<List<Playlist>>()
    private val result = Result.success(playlists)
    private val expectedException = Exception("Something went wrong")

    @Test
    fun showLoaderWhileLoading() = runBlockingTest {
        val viewModel = mockSuccessfulCase()

        viewModel.loader.captureValues {
            viewModel.playList.getValueForTest()
            assertEquals(true, this.values[0])
        }
    }

    @Test
    fun closeLoaderAfterLoading() = runBlockingTest {
        val viewModel = mockSuccessfulCase()

        viewModel.loader.captureValues {
            viewModel.playList.getValueForTest()
            assertEquals(false, this.values.last())
        }
    }

    @Test
    fun closeLoaderAfterError() = runBlockingTest {
        val viewModel = mockErrorCase()

        viewModel.loader.captureValues {
            viewModel.playList.getValueForTest()
            assertEquals(false, this.values.last())
        }
    }

    @Test
    fun getPlaylistFromRepository() = runBlockingTest {
        val viewModel = mockSuccessfulCase()

        viewModel.playList.getValueForTest()

        verify(repository, times(1)).getPlaylists()
    }

    @Test
    fun emitErrorWhenReceiveError() = runBlockingTest {
        val viewModel = mockErrorCase()

        assertEquals(expectedException, viewModel.playList.getValueForTest()!!.exceptionOrNull())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun emitPlaylistFromRepository() = runBlockingTest {
        val viewModel = mockSuccessfulCase()

        assertEquals(result, viewModel.playList.getValueForTest())
    }

    private suspend fun mockSuccessfulCase(): PlaylistViewModel {
        whenever(repository.getPlaylists()).thenReturn(
            flow {
                emit(result)
            }
        )
        return PlaylistViewModel(repository)
    }

    private suspend fun mockErrorCase(): PlaylistViewModel {
        whenever(repository.getPlaylists()).thenReturn(
            flow {
                emit(Result.failure<List<Playlist>>(expectedException))
            }
        )
        return PlaylistViewModel(repository)
    }

}