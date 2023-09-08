package petros.efthymiou.groovy.playlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach

class PlaylistViewModel(private val repository: PlaylistRepository): ViewModel() {

    val loader = MutableLiveData<Boolean>()

    @OptIn(ExperimentalCoroutinesApi::class)
    val playList = liveData {
        loader.postValue(true)
        emitSource(repository.getPlaylists()
            .onCompletion { loader.postValue(false) }
            .asLiveData())
    }
}