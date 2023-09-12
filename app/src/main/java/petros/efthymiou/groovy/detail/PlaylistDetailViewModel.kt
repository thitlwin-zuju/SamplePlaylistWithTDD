package petros.efthymiou.groovy.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch


class PlaylistDetailViewModel(private val playlistService: PlaylistDetailService): ViewModel() {
    private var _playListDetail = MutableLiveData<Result<PlaylistDetail>>()
    val playListDetail = _playListDetail

    val loader = MutableLiveData<Boolean>()

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getPlayListDetail(playlistId: String) {
        viewModelScope.launch {
            loader.postValue(true)
            playlistService.fetchPlaylistDetail(playlistId)
                .onCompletion { loader.postValue(false) }
                .collectLatest {
                _playListDetail.value = it
            }
        }
    }
}
