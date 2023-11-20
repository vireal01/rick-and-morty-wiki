package com.example.rickandmortywiki.ui.episodes

import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortywiki.R
import com.example.rickandmortywiki.data.entities.EpisodeEntity

class EpisodesRecyclerViewAdapter(
    private val itemClickListener: OnItemClickListener,
    private val loadMoreClickListener: OnLoadMoreClickListener,
) :
    RecyclerView.Adapter<EpisodesRecyclerViewAdapter.ViewHolder>() {

    private val dataSet = mutableListOf<DataModel>()
    private var loadMoreBtnState: Int = VISIBLE

    fun setItems(newData: List<EpisodeEntity>) {
        dataSet.clear()
        dataSet.addAll(newData.map { episodeEntity -> DataModel.Item(episodeEntity) })
        dataSet.add(DataModel.Button("Load more!"))
        notifyDataSetChanged()
    }

    fun getLoadMoreBtnState(value: Int?) {
        loadMoreBtnState = value ?: VISIBLE
    }

    sealed class DataModel {
        data class Item(
            val episode: EpisodeEntity
        ) : DataModel()

        data class Button(
            val buttonTitle: String
        ) : DataModel()
    }

    companion object {
        private const val TYPE_ITEM = 0
        private const val TYPE_BUTTON = 1
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

        private fun bindItem(item: EpisodeEntity) {
            itemView.findViewById<TextView>(R.id.episodeNameTextView)?.text = item.name
            itemView.findViewById<TextView>(R.id.episodeNumberTextView)?.text = item.episode
            itemView.setOnClickListener(this)
        }

        private fun bindButton(buttonTitle: String) {
            val loadMoreBtn = itemView.findViewById<Button>(R.id.load_more_btn)
            loadMoreBtn.text = buttonTitle
            loadMoreBtn.visibility = loadMoreBtnState
            loadMoreBtn.setOnClickListener(this)
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
                when (dataSet[position]) {
                    is DataModel.Button -> {
                        loadMoreClickListener.onLoadMoreClick()
                    }

                    is DataModel.Item -> {
                        val episode = dataSet[position] as DataModel.Item
                        itemClickListener.onItemClick(episode.episode)
                    }
                }
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