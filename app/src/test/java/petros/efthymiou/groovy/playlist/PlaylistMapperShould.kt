package petros.efthymiou.groovy.playlist

import junit.framework.TestCase.assertEquals
import org.junit.Test
import petros.efthymiou.groovy.R

class PlaylistMapperShould {

    private val playListRaw = PlaylistRaw("1", "name", "category")
    private val mapper = PlaylistMapper()
    private val  mappedPlayList = mapper.invoke(listOf(playListRaw))
    val playList = mappedPlayList[0]

    @Test
    fun keepSameId() {
        assertEquals(playListRaw.id, playList.id)
    }

    @Test
    fun keepSameName() {
        assertEquals(playListRaw.name, playList.name)
    }

    @Test
    fun keepSameCategory() {
        assertEquals(playListRaw.category, playList.category)
    }

    @Test
    fun mapDefaultImageWhenNotRock() {
        assertEquals(R.mipmap.playlist, playList.image)
    }

    @Test
    fun mapRockImageWhenRock() {
        val rockPlayListRaw = PlaylistRaw("1", "name", "rock")
        val rockPlayList = mapper.invoke(listOf(rockPlayListRaw))[0]
        assertEquals(R.mipmap.rock, rockPlayList.image)
    }

}