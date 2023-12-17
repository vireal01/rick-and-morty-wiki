package com.example.rickandmortywiki.ui.episodes

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortywiki.R
import com.example.rickandmortywiki.data.entities.EpisodeEntity
import java.text.SimpleDateFormat
import java.util.Locale

class EpisodesRecyclerViewAdapter(
    private val itemClick: (EpisodeEntity) -> Unit,
    private val buttonClick: () -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var dataSet = mutableListOf<RecyclerViewItemDataModel>()
    var loadMoreBtnState: Int = VISIBLE

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(newData: List<RecyclerViewItemDataModel>) {
        dataSet.clear()
        dataSet.addAll(newData)
        dataSet.add(RecyclerViewItemDataModel.Button("Load more!"))
        notifyDataSetChanged()
    }

    fun getLoadMoreBtnState(value: Int) {
        loadMoreBtnState = value
    }

    companion object {
        private const val TYPE_ITEM = 0
        private const val TYPE_BUTTON = 1
    }

    inner class ButtonItem(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        init {
            itemView.setOnClickListener(this)
        }

        fun bind(item: RecyclerViewItemDataModel.Button) {
            val loadMoreBtn = itemView.findViewById<Button>(R.id.load_more_btn)
            loadMoreBtn.text = item.buttonTitle
            loadMoreBtn.visibility = loadMoreBtnState
            loadMoreBtn.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                buttonClick()
            }
        }
    }

    inner class EpisodeItem(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        fun bind(item: RecyclerViewItemDataModel.Item) {
            itemView.findViewById<TextView>(R.id.episodeNameTextView)?.text = item.episode.name
            itemView.findViewById<TextView>(R.id.episodeNumberTextView)?.text = item.episode.episode
            itemView.findViewById<TextView>(R.id.lastEpisodeUpdate)?.text =
                prettifyLastUpdateText(item.episode.lastUpdate)
            itemView.setOnClickListener(this)
        }

        private fun prettifyLastUpdateText(timestamp: Long?): String {
            val simpleDateFormat = SimpleDateFormat("dd MMM, HH:mm:ss", Locale.ENGLISH)
            return "upd: " + simpleDateFormat.format(timestamp)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val element = dataSet[position] as RecyclerViewItemDataModel.Item
                itemClick(element.episode)
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        return when (viewType) {
            TYPE_ITEM -> EpisodeItem(inflater.inflate(R.layout.episode_item, viewGroup, false))
            TYPE_BUTTON -> ButtonItem(inflater.inflate(R.layout.loader, viewGroup, false))
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (val item = dataSet[position]) {
            is RecyclerViewItemDataModel.Item -> (holder as EpisodeItem).bind(item)
            is RecyclerViewItemDataModel.Button -> (holder as ButtonItem).bind(item)
        }
    }

    override fun getItemCount() = dataSet.size

    override fun getItemViewType(position: Int): Int {
        return when (dataSet[position]) {
            is RecyclerViewItemDataModel.Item -> TYPE_ITEM
            is RecyclerViewItemDataModel.Button -> TYPE_BUTTON
        }
    }
}
