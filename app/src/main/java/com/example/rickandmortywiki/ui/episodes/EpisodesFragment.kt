package com.example.rickandmortywiki.ui.episodes

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortywiki.R
import com.example.rickandmortywiki.data.entities.EpisodeEntity
import com.example.rickandmortywiki.di.AppComponent
import com.example.rickandmortywiki.utils.KEY_SCREEN
import com.example.rickandmortywiki.utils.daggerViewModel

class EpisodesFragment: Fragment() {

    private val component by lazy { AppComponent.init(this) }
    private val viewModel by daggerViewModel { component.episodesViewModel().build() }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        val rootView = inflater.inflate(R.layout.episodes_list_fragment, container, false)

        val recyclerAdapter = EpisodesRecyclerViewAdapter(object : EpisodesRecyclerViewAdapter.OnItemClickListener {
            override fun onItemClick(episode: EpisodeEntity) {
                viewModel.onEpisodeClick(episode)
            }
        })

        rootView.findViewById<RecyclerView>(R.id.episodes_recycler_view).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = recyclerAdapter
        }

        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            viewModel.episodesList.collect { value ->
                recyclerAdapter.setItems(value)
            }
        }

        return rootView
    }

//    private fun navigateToFragment(fragmentRes: Int, episode: Episode) {
//        val characterInfoFragment = CharactersListFragment()
//        val transaction = requireActivity().supportFragmentManager.beginTransaction()
//        transaction.replace(fragmentRes, characterInfoFragment)
//        transaction.addToBackStack(null)
//        transaction.commit()
//    }

    companion object {
        fun newInstance(screen: EpisodesScreen): EpisodesFragment {
            return EpisodesFragment().apply {
                arguments = bundleOf().apply { putParcelable(KEY_SCREEN, screen) }
            }
        }
    }
}