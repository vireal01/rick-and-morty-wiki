package com.example.rickandmortywiki.ui.episodes

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.VISIBLE
import com.example.rickandmortywiki.R
import com.example.rickandmortywiki.data.entities.EpisodeEntity

class EpisodesRecyclerViewAdapter(private val itemClickListener: OnItemClickListener, private val loadMoreClickListener: OnLoadMoreClickListener) :
    RecyclerView.Adapter<EpisodesRecyclerViewAdapter.ViewHolder>() {

    private val dataSet = mutableListOf<DataModel>()

    fun setItems(newData: List<EpisodeEntity>) {
        dataSet.clear()
        dataSet.addAll(newData.map { episodeEntity ->  DataModel.Item(episodeEntity)})
        dataSet.add(DataModel.Button("Load more!"))
        notifyDataSetChanged()
    }

    sealed class DataModel {
        data class Item(
            val episode: EpisodeEntity
        ) : DataModel()
        data class Button(
            val buttonTitle: String
        ): DataModel()
    }

    companion object {
        private const val TYPE_ITEM = 0
        private const val TYPE_BUTTON = 1
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener  {

        init {
            view.setOnClickListener(this)
        }

        private fun bindItem(item: EpisodeEntity) {
            itemView.findViewById<TextView>(R.id.episodeNameTextView)?.text = item.name
            itemView.findViewById<TextView>(R.id.episodeNumberTextView)?.text = item.episode
        }
        private fun bindButton(buttonTitle: String) {
            val btn = itemView.findViewById<Button>(R.id.load_more_btn)
            btn.visibility = VISIBLE
            btn.text = buttonTitle
        }
        fun bind(dataModel: DataModel) {
            when (dataModel) {
                is DataModel.Button -> bindButton(dataModel.buttonTitle)
                is DataModel.Item -> bindItem(dataModel.episode)
            }
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                Log.d("123", dataSet[position].toString())
                when (dataSet[position]) {
                    is DataModel.Button -> {
                        loadMoreClickListener.onLoadMoreClick()
                        Log.d("123", "Load more click from Adaoter")
                    }
                    is DataModel.Item -> {
                        val episode = dataSet[position] as DataModel.Item
                        itemClickListener.onItemClick(episode.episode)
                    }
                }
//                itemClickListener.onItemClick(dataSet[position])
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val layout = when (viewType) {
            TYPE_ITEM -> R.layout.episode_item
            TYPE_BUTTON -> R.layout.loader
            else -> throw IllegalArgumentException("Invalid view type")
        }

        val view = LayoutInflater
            .from(viewGroup.context)
            .inflate(layout, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(dataSet[position])
    }

    interface OnItemClickListener {
        fun onItemClick(episode: EpisodeEntity)
    }

    interface OnLoadMoreClickListener {
        fun onLoadMoreClick()
    }

    override fun getItemCount() = dataSet.size

    override fun getItemViewType(position: Int): Int {
        return when (dataSet[position]) {
            is DataModel.Item -> TYPE_ITEM
            is DataModel.Button -> TYPE_BUTTON
        }
    }
}