package petros.efthymiou.groovy.playlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_playlist.view.playlists_list
import kotlinx.android.synthetic.main.fragment_playlist.view.loader
import petros.efthymiou.groovy.R
import javax.inject.Inject

@AndroidEntryPoint
class PlaylistFragment : Fragment() {

    private lateinit var playlistViewModel: PlaylistViewModel

    @Inject
    lateinit var viewModelFactory: PlaylistViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_playlist, container, false)

        setupViewModel()

        playlistViewModel.playList.observe(viewLifecycleOwner) { playLists ->
            if (playLists.getOrNull() != null)
                setupRecyclerView(view.playlists_list, playLists.getOrNull()!!)
//            else {
//                view.progressBar.visibility = View.GONE
//            }
        }

        playlistViewModel.loader.observe(viewLifecycleOwner) { loading ->
            when(loading) {
                true -> view.loader.visibility = View.VISIBLE
                false -> view.loader.visibility = View.GONE
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
            adapter = MyPlaylistRecyclerViewAdapter(playLists){
                val action = PlaylistFragmentDirections.actionPlaylistFragmentToPlaylistDetailFragment(it)
                findNavController().navigate(action)
            }
        }
    }

    private fun setupViewModel() {
        playlistViewModel =
            ViewModelProvider(this, viewModelFactory).get(PlaylistViewModel::class.java)
    }

    companion object {

        @JvmStatic
        fun newInstance() = PlaylistFragment()
    }
}