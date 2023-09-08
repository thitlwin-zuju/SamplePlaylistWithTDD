package petros.efthymiou.groovy.detail

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import petros.efthymiou.groovy.playlist.PlaylistAPI
import javax.inject.Inject

class PlaylistDetailService @Inject constructor(private val api: PlaylistDetailAPI) {

    suspend fun fetchPlaylistDetail(playListId: String): Flow<Result<PlaylistDetail>> {
        return flow {
            emit(Result.success(api.getPlaylistDetails(playListId)))
        }.catch {
            emit(Result.failure(RuntimeException("Something went wrong.")))
        }
    }
}