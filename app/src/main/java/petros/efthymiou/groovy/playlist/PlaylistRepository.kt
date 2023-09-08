package petros.efthymiou.groovy.playlist

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PlaylistRepository @Inject constructor(private val service: PlaylistService, private val mapper: PlaylistMapper) {

    suspend fun getPlaylists(): Flow<Result<List<Playlist>>> =
        service.fetchPlaylists().map { result ->
            when (result.isSuccess) {
                true -> Result.success(mapper(result.getOrNull()!!))
                false -> Result.failure(result.exceptionOrNull()!!)
            }
        }
}