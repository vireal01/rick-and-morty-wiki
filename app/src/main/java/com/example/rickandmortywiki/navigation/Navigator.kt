package com.example.rickandmortywiki.navigation

import android.util.Log
import androidx.fragment.app.FragmentManager
import com.example.rickandmortywiki.R
import com.example.rickandmortywiki.ui.characterInfo.CharacterInfoScreen
import com.example.rickandmortywiki.ui.characterInfo.CharactersInfoFragment
import com.example.rickandmortywiki.ui.charactersList.CharactersListFragment
import com.example.rickandmortywiki.ui.charactersList.CharactersListScreen
import com.example.rickandmortywiki.ui.episodes.EpisodesFragment
import com.example.rickandmortywiki.ui.episodes.EpisodesScreen


class Navigator(private val fragmentManager: FragmentManager) {

	fun navigateTo(screen: AppBaseScreen, addToBackStack: Boolean = true) {
		val targetFragment = when (screen) {
			is EpisodesScreen -> EpisodesFragment.newInstance(screen)
			is CharactersListScreen -> CharactersListFragment.newInstance(screen)
			is CharacterInfoScreen -> CharactersInfoFragment.newInstance(screen)
			else -> throw java.lang.IllegalArgumentException("Unsupported screen")
		}
		fragmentManager
				.beginTransaction()
				.replace(R.id.fragment_container, targetFragment)
				.apply { if (addToBackStack) addToBackStack(null) }
				.commitAllowingStateLoss()
	}

	//        val characterInfoFragment = CharactersListFragment()
//        val transaction = requireActivity().supportFragmentManager.beginTransaction()
//        transaction.replace(fragmentRes, characterInfoFragment)
//        transaction.addToBackStack(null)
//        transaction.commit()

	fun closeScreen() {
		fragmentManager.popBackStack()
	}
}
