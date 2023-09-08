package petros.efthymiou.groovy.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class PlaylistDetailViewModel(private val playlistService: PlaylistDetailService): ViewModel() {
    private var _playListDetail = MutableLiveData<Result<PlaylistDetail>>()
    val playListDetail = _playListDetail

    fun getPlayListDetail(playlistId: String) {
        viewModelScope.launch {
            playlistService.fetchPlaylistDetail(playlistId).collectLatest {
                _playListDetail.value = it
            }
        }
    }

}
