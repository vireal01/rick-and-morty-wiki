package com.example.rickandmortywiki.navigation

import androidx.lifecycle.ViewModel

class Router : ViewModel() {

	private var navigator: Navigator? = null

	fun attachNavigator(navigator: Navigator) {
		this.navigator = navigator
	}

	fun detachNavigator() {
		navigator = null
	}

	fun navigateTo(screen: AppBaseScreen) {
		navigator?.navigateTo(screen)
	}

	fun closeScreen() {
		navigator?.closeScreen()
	}
}
