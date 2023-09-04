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
import com.example.rickandmortywiki.R
import com.example.rickandmortywiki.di.AppComponent
import com.example.rickandmortywiki.utils.KEY_SCREEN
import com.example.rickandmortywiki.utils.daggerViewModel
import com.example.rickandmortywiki.utils.getScreen

class CharactersInfoFragment: Fragment() {

    private val screen: CharacterInfoScreen get() = getScreen()
    private val component by lazy { AppComponent.init(this) }
    private val viewModel by daggerViewModel { component.characterInfoViewModel().build() }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val rootView = inflater.inflate(R.layout.character_info_fragment, container, false)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var appearsInText = ""
        screen.character.appearsInEpisodes?.forEach { appearsInText += "- ${it.name} (${it.episode}) \n"}
        view.findViewById<ImageView>(R.id.characterInfoImageView).setImageBitmap(screen.character.characterImageBitmap)
        view.findViewById<TextView>(R.id.characterInfoNameTextView).text = screen.character.name
        view.findViewById<TextView>(R.id.characterInfoGenderTextView).text = screen.character.gender
        view.findViewById<TextView>(R.id.characterInfoStatusTextView).text = screen.character.status
        view.findViewById<TextView>(R.id.characterInfoLocationTextView).text = screen.character.location?.name
        view.findViewById<TextView>(R.id.listOfEpisodesAppearsInTextView).text = appearsInText

        val toolbar = view.findViewById<Toolbar>(R.id.characterInfoToolbar)
        toolbar.title = screen.character.name

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