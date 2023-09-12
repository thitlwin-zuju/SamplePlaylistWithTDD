package petros.efthymiou.groovy.detail

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import petros.efthymiou.groovy.utils.BaseUnitTest
import petros.efthymiou.groovy.utils.captureValues
import petros.efthymiou.groovy.utils.getValueForTest

class PlaylistDetailViewModelShould : BaseUnitTest() {

    private val playlistService = mock<PlaylistDetailService>()
    private val playlistDetail = mock<PlaylistDetail>()
    private val result = Result.success(playlistDetail)
    private val expectedException = Exception("Something went wrong")
    private val error = Result.failure<PlaylistDetail>(expectedException)
    private val playListId = "1"

    @Test
    fun getPlaylistDetailFromService() = runBlockingTest {
        val viewModel = mockSuccessfulCase()

        viewModel.getPlayListDetail(playListId)

        viewModel.playListDetail.getValueForTest()

        verify(playlistService, times(1)).fetchPlaylistDetail(playListId)
    }

    @Test
    fun emitPlaylistDetailFromService() = runBlockingTest {
        val viewModel = mockSuccessfulCase()

        viewModel.getPlayListDetail(playListId)

        assertEquals(result, viewModel.playListDetail.getValueForTest())
    }

    @Test
    fun emitErrorWhenReceiveError() = runBlockingTest {
        val viewModel = mockErrorCase()

        assertEquals(error, viewModel.playListDetail.getValueForTest())
    }

    @Test
    fun showLoaderWhileLoading() = runBlockingTest {
        val viewModel = mockSuccessfulCase()

        viewModel.loader.captureValues {
            viewModel.getPlayListDetail(playListId)
            viewModel.playListDetail.getValueForTest()
            assertEquals(true, values[0])
        }
    }

    @Test
    fun closeLoaderAfterLoading() = runBlockingTest {
        val viewModel = mockSuccessfulCase()

        viewModel.loader.captureValues {
            viewModel.getPlayListDetail(playListId)

            viewModel.playListDetail.getValueForTest()
            assertEquals(false, this.values.last())
        }
    }

    @Test
    fun closeLoaderAfterError() = runBlockingTest {
        val viewModel = mockErrorCase()

        viewModel.loader.captureValues {
            viewModel.getPlayListDetail(playListId)

            viewModel.playListDetail.getValueForTest()
            assertEquals(false, this.values.last())
        }
    }

    private suspend fun mockErrorCase(): PlaylistDetailViewModel {
        whenever(playlistService.fetchPlaylistDetail(playListId)).thenReturn(flow {
            emit(Result.failure<PlaylistDetail>(expectedException))
        })

        val viewModel = PlaylistDetailViewModel(playlistService)
        viewModel.getPlayListDetail(playListId)
        return viewModel
    }

    private suspend fun mockSuccessfulCase(): PlaylistDetailViewModel {
        whenever(playlistService.fetchPlaylistDetail(playListId)).thenReturn(flow {
            emit(result)
        })

        val viewModel = PlaylistDetailViewModel(playlistService)
        return viewModel
    }

}