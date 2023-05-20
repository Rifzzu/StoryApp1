package com.example.storyapp1.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.util.Pair
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.storyapp1.R
import com.example.storyapp1.data.remote.response.StoryItem
import com.example.storyapp1.databinding.ItemStoryBinding
import com.example.storyapp1.ui.detail.DetailActivity

class StoryAdapter(
    private val listStory: List<StoryItem>
) : RecyclerView.Adapter<StoryAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemStoryBinding.bind(itemView)
        fun bindItem(item: StoryItem) {
            with(binding) {
                Glide.with(itemView)
                    .load(item.photoUrl)
                    .into(photo)

                name.text = item.name
                description.text = item.description

                itemView.setOnClickListener {
                    DetailActivity.start(
                        itemView.context,
                        item.photoUrl as String,
                        item.id as String,
                        Pair(photo, "ivItemPhoto")
                    )

                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_story, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(listStory[position])
    }

    override fun getItemCount(): Int = listStory.size
}