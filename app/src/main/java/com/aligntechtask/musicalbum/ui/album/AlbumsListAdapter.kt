package com.aligntechtask.musicalbum.ui.album

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.aligntechtask.musicalbum.R
import com.aligntechtask.musicalbum.data.Album
import com.aligntechtask.musicalbum.databinding.LayoutAlbumListItemBinding
import com.aligntechtask.musicalbum.util.EspressoIdlingResource


class AlbumsListAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Album>() {

        override fun areItemsTheSame(oldItem: Album, newItem: Album): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Album, newItem: Album): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding =
            DataBindingUtil.inflate<LayoutAlbumListItemBinding>(layoutInflater, R.layout.layout_album_list_item, parent, false)
val albumviewholder: AlbumViewHolder = AlbumViewHolder(binding,interaction)
        return albumviewholder
      /*  LayoutAlbumListItemBinding mbinding = DataBindingUtil.inf

        return MovieViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.layout_album_list_item,
                parent,
                false
            ),
            interaction
        )*/
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is AlbumViewHolder -> {
                holder.binding.album= differ.currentList.get(position)

                if (interaction != null) {
                    holder.bind(differ.currentList.get(position),interaction)
                }

            }
        }
    }

    override fun getItemCount(): Int {
        Log.i("adapter size",""+differ.currentList.size)
        return differ.currentList.size
    }

    fun submitList(list: List<Album>) {
        EspressoIdlingResource.increment()
        val dataCommitCallback = Runnable {
            EspressoIdlingResource.decrement()
        }
        differ.submitList(list, dataCommitCallback)
    }

    /*class MovieViewHolder
    constructor(
        itemView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: Album) = with(itemView) {
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }
            Log.i("adapter text",item.title)
            movie_title.text = item.title
            Glide.with(itemView)
                .load(item.image)
                .transform(  RoundedCorners(25))
                .into(movie_image)
            movie_copyright.text = item.description
        }
    }*/
    internal class AlbumViewHolder constructor(val itemView: LayoutAlbumListItemBinding,   interaction: Interaction?,
                                               val binding: LayoutAlbumListItemBinding = itemView
    ) :
        RecyclerView.ViewHolder(itemView.root) {

        fun bind(album: Album, clickListener:Interaction)
        {

            binding.container1.setOnClickListener {
                clickListener.onItemSelected(album)
            }
        }

    }

    interface Interaction {
        fun onItemSelected(item: Album)
    }
}
