package petros.efthymiou.groovy.detail

import retrofit2.http.GET
import retrofit2.http.Path

interface PlaylistDetailAPI {
    @GET("playlists/{id}")
    suspend fun getPlaylistDetails(@Path("id") id: String): PlaylistDetail
}
