package com.example.rickandmortywiki.utils

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.fragment.app.Fragment
import androidx.fragment.app.createViewModelLazy
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

const val KEY_SCREEN = "screen"

@Suppress("UseCheckOrError")
fun <T : Parcelable> Fragment.getScreen(): T = arguments?.getParcelable(KEY_SCREEN)
	?: throw IllegalStateException("Screen arg wasn't passed")

inline fun <reified T : ViewModel> Fragment.daggerViewModel(
		crossinline viewModelInstanceCreator: () -> T
): Lazy<T> {
	return createViewModelLazy(
			viewModelClass = T::class,
			storeProducer = { this.viewModelStore },
			factoryProducer = {
				object : ViewModelProvider.Factory {
					override fun <T : ViewModel> create(modelClass: Class<T>): T {
						return viewModelInstanceCreator() as T
					}
				}
			}
	)
}
