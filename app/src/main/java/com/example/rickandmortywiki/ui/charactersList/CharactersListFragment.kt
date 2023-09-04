package com.example.rickandmortywiki.ui.charactersList

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortywiki.R
import com.example.rickandmortywiki.di.AppComponent
import com.example.rickandmortywiki.network.models.Character
import com.example.rickandmortywiki.utils.KEY_SCREEN
import com.example.rickandmortywiki.utils.daggerViewModel
import com.example.rickandmortywiki.utils.getScreen

class CharactersListFragment: Fragment() {

    private val screen: CharactersListScreen get() = getScreen()
    private val component by lazy { AppComponent.init(this) }
    private val viewModel by daggerViewModel { component.charactersListViewModel().build() }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val rootView = inflater.inflate(R.layout.characters_list_fragment, container, false)

        viewModel.showListOfCharactersOfEpisode(screen.episode)

        val recyclerAdapter = CharactersListAdapter(object : CharactersListAdapter.OnItemClickListener {
            override fun onItemClick(character: Character) {
                viewModel.onViewCharacterItemClick(character)
            }
        })

        rootView.findViewById<RecyclerView>(R.id.characters_recycler_view).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = recyclerAdapter
        }

        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            viewModel.charactersList.collect { value ->
                recyclerAdapter.setItems(value)
            }
        }


        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val toolbar = view.findViewById<Toolbar>(R.id.charactersListToolbar)
        toolbar.title = screen.episode.name

        toolbar.setNavigationOnClickListener {
            viewModel.onBackPressed()
        }

    }

    companion object {
        fun newInstance(screen: CharactersListScreen): CharactersListFragment {
            return CharactersListFragment().apply {
                arguments = bundleOf().apply { putParcelable(KEY_SCREEN, screen) }
            }
        }
    }
}