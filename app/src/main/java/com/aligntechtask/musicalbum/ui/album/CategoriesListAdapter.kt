package com.aligntechtask.musicalbum.ui.album

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.aligntechtask.musicalbum.R

import com.aligntechtask.musicalbum.data.source.Category
import com.aligntechtask.musicalbum.databinding.LayoutCategoryListItemBinding
import com.aligntechtask.musicalbum.util.EspressoIdlingResource

class CategoriesListAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Category>() {

        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding =
            DataBindingUtil.inflate<LayoutCategoryListItemBinding>(layoutInflater, R.layout.layout_category_list_item, parent, false)
        val albumviewholder: CategoriesListAdapter.CategoryViewHolder =
            CategoriesListAdapter.CategoryViewHolder(binding, interaction)
        return albumviewholder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CategoryViewHolder -> {
                holder.binding.category = differ.currentList.get(position)
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

    fun submitList(list: List<Category>) {
        EspressoIdlingResource.increment()
        val dataCommitCallback = Runnable {
            EspressoIdlingResource.decrement()
        }
        differ.submitList(list, dataCommitCallback)
    }

    class CategoryViewHolder
    constructor(
        itemView: LayoutCategoryListItemBinding,
        private val interaction: Interaction?,
        val binding: LayoutCategoryListItemBinding = itemView
    ) : RecyclerView.ViewHolder(itemView.root) {

        fun bind(category: Category, clickListener: Interaction?)
        {

            binding.container1.setOnClickListener {
                if (clickListener != null) {
                    clickListener.onItemSelected(category)
                }
            }
        }

            
        }



    interface Interaction {
        fun onItemSelected(item: Category)
    }
}
