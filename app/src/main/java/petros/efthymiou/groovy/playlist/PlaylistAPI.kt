package petros.efthymiou.groovy.playlist

import petros.efthymiou.groovy.detail.PlaylistDetail
import petros.efthymiou.groovy.detail.PlaylistDetailRaw
import retrofit2.http.GET
import retrofit2.http.Path

interface PlaylistAPI {
    @GET("playlists")
    suspend fun getPlayList(): List<PlaylistRaw>
}
