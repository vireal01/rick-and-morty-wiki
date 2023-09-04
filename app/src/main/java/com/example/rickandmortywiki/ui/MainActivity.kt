package com.example.rickandmortywiki.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.fragment.app.add
import com.example.rickandmortywiki.R
import com.example.rickandmortywiki.navigation.Navigator
import com.example.rickandmortywiki.navigation.Router
import com.example.rickandmortywiki.ui.episodes.EpisodesFragment

class MainActivity: AppCompatActivity() {
    private val router by viewModels<Router>()
    private val navigator by lazy { Navigator(supportFragmentManager) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<EpisodesFragment>(R.id.fragment_container)
            }
        }

    }

    override fun onResume() {
        super.onResume()
        router.attachNavigator(navigator)
    }

    override fun onPause() {
        router.detachNavigator()
        super.onPause()
    }
}