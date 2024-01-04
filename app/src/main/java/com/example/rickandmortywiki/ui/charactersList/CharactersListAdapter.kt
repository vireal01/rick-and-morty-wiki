package com.example.rickandmortywiki.ui.charactersList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.rickandmortywiki.R
import com.example.rickandmortywiki.data.entities.CharacterEntity

class CharactersListAdapter(private val itemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<CharactersListAdapter.ViewHolder>() {

    private val dataSet = mutableListOf<CharacterEntity>()

    fun setItems(newData: List<CharacterEntity>) {
        dataSet.clear()
        dataSet.addAll(newData)
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        val characterNameTextView: TextView
        val characterStatusTextView: TextView
        val characterImageView: ImageView

        init {
            characterNameTextView = view.findViewById(R.id.characterNameTextView)
            characterStatusTextView = view.findViewById(R.id.characterStatusTextView)
            characterImageView = view.findViewById(R.id.characterImageView)
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
            .inflate(R.layout.character_item, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.characterNameTextView.text = dataSet[position].name
        viewHolder.characterStatusTextView.text = dataSet[position].status
        viewHolder.characterImageView.load(dataSet[position].imageUrl)
    }

    interface OnItemClickListener {
        fun onItemClick(character: CharacterEntity)
    }

    override fun getItemCount() = dataSet.size
}
