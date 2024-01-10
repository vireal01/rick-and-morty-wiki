package com.example.rickandmortywiki.ui.characterInfo

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.rickandmortywiki.di.AppComponent
import com.example.rickandmortywiki.ui.theme.RickAndMortyTheme
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
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                RickAndMortyTheme {
                    CharacterInfoRenderer(viewModel)
                }
            }
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
