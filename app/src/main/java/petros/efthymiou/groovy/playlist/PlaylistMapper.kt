package petros.efthymiou.groovy.playlist

import petros.efthymiou.groovy.R
import javax.inject.Inject

class PlaylistMapper @Inject constructor() : Function1<List<PlaylistRaw>, List<Playlist>> {
    override fun invoke(p1: List<PlaylistRaw>): List<Playlist> {
        return p1.map { playlistRaw ->
            val imageId = when(playlistRaw.category) {
                "rock" -> R.mipmap.rock
                else -> R.mipmap.playlist
            }
            Playlist(
                playlistRaw.id,
                playlistRaw.name,
                playlistRaw.category,
                imageId
            )
        }
    }

}
