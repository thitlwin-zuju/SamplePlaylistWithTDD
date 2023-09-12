package petros.efthymiou.groovy.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_playlist.view.loader
import kotlinx.android.synthetic.main.fragment_playlist_detail.playlist_detail_root
import kotlinx.android.synthetic.main.fragment_playlist_detail.tv_playlist_detail_details
import kotlinx.android.synthetic.main.fragment_playlist_detail.tv_playlist_detail_name
import kotlinx.android.synthetic.main.fragment_playlist_detail.view.detail_loader
import petros.efthymiou.groovy.R
import javax.inject.Inject

@AndroidEntryPoint
class PlaylistDetailFragment : Fragment() {

    private val args: PlaylistDetailFragmentArgs by navArgs()

    private lateinit var playlistDetailViewModel: PlaylistDetailViewModel

    @Inject
    lateinit var playlistDetailViewModelFactory: PlaylistDetailViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_playlist_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        playlistDetailViewModel = playlistDetailViewModelFactory.create(PlaylistDetailViewModel::class.java)
        playlistDetailViewModel.getPlayListDetail(args.playlistId)

        playlistDetailViewModel.playListDetail.observe(viewLifecycleOwner) { result ->
            if (result.getOrNull() != null) {
                tv_playlist_detail_name.text = result.getOrNull()!!.name
                tv_playlist_detail_details.text = result.getOrNull()!!.details
            } else {
                Snackbar.make(playlist_detail_root, R.string.generic_error, Snackbar.LENGTH_SHORT).show()
            }
        }

        playlistDetailViewModel.loader.observe(viewLifecycleOwner) { loading ->
            when(loading) {
                true -> view.detail_loader.visibility = View.VISIBLE
                false -> view.detail_loader.visibility = View.GONE
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = PlaylistDetailFragment()
    }
}