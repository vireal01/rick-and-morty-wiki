//package com.example.rickandmortywiki.di
//
//import android.app.Application
//import androidx.fragment.app.Fragment
//import androidx.lifecycle.ViewModelProvider
//import com.example.rickandmortywiki.ui.MainActivity
//import com.example.rickandmortywiki.navigation.Router
//import com.example.rickandmortywiki.ui.characterInfo.CharacterInfoViewModel
//import com.example.rickandmortywiki.ui.charactersList.CharactersListViewModel
//import com.example.rickandmortywiki.ui.episodes.EpisodesViewModel
//import dagger.BindsInstance
//import dagger.Component
//import javax.inject.Singleton
//
//@Singleton
//@Component(modules = [AppModule::class])
//interface AppComponent {
//
//	@Component.Builder
//	interface Builder {
//
//		@BindsInstance
//		fun app(app: Application): Builder
//
////		@BindsInstance
////		fun router(router: Router): Builder
//
//		fun build(): AppComponent
//	}
//
//	fun episodesViewModel(): EpisodesViewModel.Factory
//	fun charactersListViewModel(): CharactersListViewModel.Factory
//	fun characterInfoViewModel(): CharacterInfoViewModel.Factory
//
//	// Add here dependencies for Android Worker
//
//	companion object {
//
//		fun init(fragment: Fragment): AppComponent {
//			// Name matches the interface name
//			return DaggerAppComponent.builder()
//					.app(fragment.requireActivity().application)
//					.router(ViewModelProvider(fragment.requireActivity())[Router::class.java])
//					.build()
//		}
//	}
//}
