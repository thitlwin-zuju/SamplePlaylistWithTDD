package petros.efthymiou.groovy.detail

import javax.inject.Inject

class PlaylistDetailMapper @Inject constructor() : Function1<PlaylistDetailRaw, PlaylistDetail> {
    override fun invoke(p1: PlaylistDetailRaw): PlaylistDetail {
        return PlaylistDetail(
            p1.id,
            p1.name,
            p1.details
        )
    }
}