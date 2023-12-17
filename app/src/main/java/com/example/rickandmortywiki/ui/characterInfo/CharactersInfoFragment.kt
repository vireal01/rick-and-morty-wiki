package com.example.rickandmortywiki.ui.characterInfo

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.rickandmortywiki.R
import com.example.rickandmortywiki.data.entities.EpisodeEntity
import com.example.rickandmortywiki.di.AppComponent
import com.example.rickandmortywiki.utils.KEY_SCREEN
import com.example.rickandmortywiki.utils.daggerViewModel
import com.example.rickandmortywiki.utils.getScreen

class CharactersInfoFragment : Fragment() {

    private val screen: CharacterInfoScreen get() = getScreen()
    private val component by lazy { AppComponent.init(this) }
    private val viewModel by daggerViewModel {
        component.characterInfoViewModel().build(screen.characterId)
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.character_info_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar = view.findViewById<Toolbar>(R.id.characterInfoToolbar)

        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            viewModel.character.collect { character ->
                view.findViewById<ImageView>(R.id.characterInfoImageView).load(character?.imageUrl)
                view.findViewById<TextView>(R.id.characterInfoNameTextView).text = character?.name
                view.findViewById<TextView>(R.id.characterInfoGenderTextView).text =
                    character?.gender
                view.findViewById<TextView>(R.id.characterInfoStatusTextView).text =
                    character?.status
                toolbar.title = character?.name
            }
        }

        val recyclerAdapter = CharacterInfoEpisodesListRecyclerViewAdapter(
            object : CharacterInfoEpisodesListRecyclerViewAdapter.OnItemClickListener {
                override fun onItemClick(episode: EpisodeEntity) {
                    viewModel.onViewEpisodeItemClick(episode.episodeId)
                }
            })

        view.findViewById<RecyclerView>(R.id.character_info_recycler_view).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = recyclerAdapter
        }

        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            viewModel.appearsAtEpisodes.collect { episodes ->
                val appearsInText = episodes?.episodes
                if (appearsInText != null) {
                    recyclerAdapter.setItems(appearsInText)
                }
            }
        }


        toolbar.setNavigationOnClickListener {
            viewModel.onBackPressed()
        }
    }

    companion object {
        fun newInstance(screen: CharacterInfoScreen): CharactersInfoFragment {
            return CharactersInfoFragment().apply {
                arguments = bundleOf().apply { putParcelable(KEY_SCREEN, screen) }
            }
        }
    }
}
