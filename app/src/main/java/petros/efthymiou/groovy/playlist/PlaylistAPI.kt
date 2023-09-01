package petros.efthymiou.groovy.playlist

interface PlaylistAPI {
    suspend fun getPlayList(): List<Playlist>
}
