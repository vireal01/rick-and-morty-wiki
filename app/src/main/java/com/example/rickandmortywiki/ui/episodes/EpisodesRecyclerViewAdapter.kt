package com.example.rickandmortywiki.ui.episodes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortywiki.R
import com.example.rickandmortywiki.data.entities.EpisodeEntity

class EpisodesRecyclerViewAdapter(private val itemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<EpisodesRecyclerViewAdapter.ViewHolder>() {

    private val dataSet = mutableListOf<EpisodeEntity>()

    fun setItems(newData: List<EpisodeEntity>) {
        dataSet.clear()
        dataSet.addAll(newData)
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener  {
        val episodeNumberTextView: TextView
        val episodeNameTextView: TextView

        init {
            episodeNameTextView = view.findViewById(R.id.episodeNameTextView)
            episodeNumberTextView = view.findViewById(R.id.episodeNumberTextView)
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                itemClickListener.onItemClick(dataSet[position])
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.episode_item, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.episodeNumberTextView.text = dataSet[position].episode
        viewHolder.episodeNameTextView.text = dataSet[position].name
    }

    interface OnItemClickListener {
        fun onItemClick(episode: EpisodeEntity)
    }


    override fun getItemCount() = dataSet.size
}