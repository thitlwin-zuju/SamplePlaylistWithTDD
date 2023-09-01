package petros.efthymiou.groovy.playlist

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import petros.efthymiou.groovy.R

import petros.efthymiou.groovy.databinding.ListItemBinding

class MyPlaylistRecyclerViewAdapter(
    private val values: List<Playlist>
) : RecyclerView.Adapter<MyPlaylistRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            ListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.tvName.text = item.name
        holder.tvCategory.text = item.category
        holder.imvPlaylist.setImageResource(R.mipmap.playlist)
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val tvName: TextView = binding.tvPlaylistName
        val tvCategory: TextView = binding.tvPlaylistCategory
        val imvPlaylist: ImageView = binding.imvPlaylist
    }

}