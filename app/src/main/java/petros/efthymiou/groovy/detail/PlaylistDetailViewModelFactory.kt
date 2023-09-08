package petros.efthymiou.groovy.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class PlaylistDetailViewModelFactory @Inject constructor(private val playlistService: PlaylistDetailService) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PlaylistDetailViewModel(playlistService) as T
    }
}