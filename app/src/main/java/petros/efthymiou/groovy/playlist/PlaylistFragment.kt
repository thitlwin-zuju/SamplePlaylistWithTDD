package petros.efthymiou.groovy.playlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import petros.efthymiou.groovy.R

class PlaylistFragment : Fragment() {

    private val service = PlaylistService(object : PlaylistAPI {
        override suspend fun getPlayList(): List<Playlist> {
            return emptyList()
        }
    })
    private val repository = PlaylistRepository(service)
    lateinit var playlistViewModel: PlaylistViewModel
    lateinit var viewModelFactory: PlaylistViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_playlist, container, false)

        setupViewModel()

        playlistViewModel.playList.observe(this) { playLists ->
            if (playLists.getOrNull() != null)
                setupRecyclerView(view, playLists.getOrNull()!!)
            else {
                // TODO: Handle error
            }
        }
        return view
    }

    private fun setupRecyclerView(
        view: View?,
        playLists: List<Playlist>
    ) {
        with(view as RecyclerView) {
            layoutManager = LinearLayoutManager(context)
            adapter = MyPlaylistRecyclerViewAdapter(playLists)
        }
    }

    private fun setupViewModel() {
        viewModelFactory = PlaylistViewModelFactory(repository)
        playlistViewModel =
            ViewModelProvider(this, viewModelFactory).get(PlaylistViewModel::class.java)
    }

    companion object {

        @JvmStatic
        fun newInstance() = PlaylistFragment()
    }
}