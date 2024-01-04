package com.example.rickandmortywiki.ui.characterInfo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortywiki.R
import com.example.rickandmortywiki.data.entities.EpisodeEntity

class CharacterInfoEpisodesListRecyclerViewAdapter(private val itemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<CharacterInfoEpisodesListRecyclerViewAdapter.ViewHolder>() {

    private val dataSet = mutableListOf<EpisodeEntity>()

    fun setItems(newData: List<EpisodeEntity>) {
        dataSet.clear()
        dataSet.addAll(newData)
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        val characterInfoEpisodeTagTextView: TextView
        val characterInfoEpisodeNameTextView: TextView

        init {
            characterInfoEpisodeNameTextView = view.findViewById(R.id.characterInfoEpisodeName)
            characterInfoEpisodeTagTextView = view.findViewById(R.id.characterInfoEpisodeTag)
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
            .inflate(R.layout.character_info_episode_item, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.characterInfoEpisodeNameTextView.text = "${dataSet[position].name} (${dataSet[position].episode})"
        viewHolder.characterInfoEpisodeTagTextView.text = ""
    }

    interface OnItemClickListener {
        fun onItemClick(episode: EpisodeEntity)
    }

    override fun getItemCount() = dataSet.size
}
