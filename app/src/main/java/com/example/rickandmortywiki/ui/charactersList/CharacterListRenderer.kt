package com.example.rickandmortywiki.ui.charactersList

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.rickandmortywiki.data.entities.CharacterEntity
import com.example.rickandmortywiki.ui.Paddings
import com.example.rickandmortywiki.ui.components.AsyncImageWithRainbowCircle
import com.example.rickandmortywiki.ui.components.RickAndMortyTopAppBar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CharactersListRenderer(viewModel: CharactersListViewModel) {
    val episodeWithCharacters =
        viewModel.episodeWithCharacters.collectAsState().value?.characters
    if (episodeWithCharacters != null) {
        Scaffold(
            modifier = Modifier.background(MaterialTheme.colorScheme.background),
            topBar = {
                RickAndMortyTopAppBar(
                    text = viewModel.episodeWithCharacters.collectAsState().value?.episode?.name.toString(),
                ) {
                    viewModel.onBackPressed()
                }
            },
        ) { innerPadding ->
            LazyColumn(
                contentPadding = PaddingValues(Paddings.one),
                verticalArrangement = Arrangement.spacedBy(Paddings.half),
                modifier = Modifier.padding(top = innerPadding.calculateTopPadding())
            ) {
                items(episodeWithCharacters) { item ->
                    CharactersListItem(
                        item
                    ) { viewModel.onViewCharacterItemClick(item.characterId) }
                }
            }
        }
    }
}

@Composable
fun CharactersListItem(character: CharacterEntity, onClick: () -> Unit) {
    Surface(
        color = MaterialTheme.colorScheme.surfaceVariant,
        shape = MaterialTheme.shapes.medium,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onClick()
                }
                .padding(vertical = Paddings.half, horizontal = Paddings.one)
            // Paddings may be only /4 or /8. Side paddings 16.dp (from Material design)
        ) {
            // image size max 64x64, 56x56
            AsyncImageWithRainbowCircle(
                model = character.imageUrl,
                size = 64.dp,
                strokeWidth = 10f
            )
            Column(
                modifier = Modifier
                    .padding(horizontal = Paddings.one)
                    .height(64.dp),
                verticalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    text = character.name ?: "Unknown",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.titleMedium
                )
                // By long tap make preview dialog
                // Body1 and Body2 diff 4.sp
                Text(
                    text = "Status: ${character.status}",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}
