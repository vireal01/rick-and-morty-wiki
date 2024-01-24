//package com.example.rickandmortywiki.ui.charactersList
//
//import android.annotation.SuppressLint
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.compose.ui.platform.ComposeView
//import androidx.compose.ui.platform.ViewCompositionStrategy
//import androidx.core.os.bundleOf
//import androidx.fragment.app.Fragment
//import com.example.rickandmortywiki.di.AppComponent
//import com.example.rickandmortywiki.ui.theme.RickAndMortyTheme
//import com.example.rickandmortywiki.utils.KEY_SCREEN
//import com.example.rickandmortywiki.utils.daggerViewModel
//import com.example.rickandmortywiki.utils.getScreen
//
//class CharactersListFragment : Fragment() {
//
//    private val screen: CharactersListScreen get() = getScreen()
//    private val component by lazy { AppComponent.init(this) }
//    private val viewModel by daggerViewModel {
//        component.charactersListViewModel().build(screen.episodeId)
//    }
//
//    @SuppressLint("MissingInflatedId")
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//
//        super.onCreateView(inflater, container, savedInstanceState)
//
//        return ComposeView(requireContext()).apply {
//            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
//            setContent {
//                RickAndMortyTheme {
//                    CharactersListRenderer(viewModel)
//                }
//            }
//        }
//    }
//
//    companion object {
//        fun newInstance(screen: CharactersListScreen): CharactersListFragment {
//            return CharactersListFragment().apply {
//                arguments = bundleOf().apply { putParcelable(KEY_SCREEN, screen) }
//            }
//        }
//    }
//}
