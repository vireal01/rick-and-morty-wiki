package com.example.rickandmortywiki.ui.characterInfo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.rickandmortywiki.data.entities.CharacterEntity
import com.example.rickandmortywiki.ui.Paddings
import com.example.rickandmortywiki.ui.components.AsyncImageWithRainbowCircle
import com.example.rickandmortywiki.ui.components.RickAndMortyTopAppBar

@Composable
fun CharacterInfoRenderer(viewModel: CharacterInfoViewModel) {
    val character = viewModel.character.collectAsState().value

    Scaffold(
        modifier = Modifier.background(MaterialTheme.colorScheme.background),
        topBar = {
            RickAndMortyTopAppBar(
                text = character?.name.toString(),
            ) {
                viewModel.onBackPressed()
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            if (character != null) {
                CharacterInfoCard(character)
            }
        }
    }
}

@Suppress("LongMethod")
@Composable
fun CharacterInfoCard(character: CharacterEntity) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        modifier = Modifier
            .padding(Paddings.one)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(Paddings.one)
        ) {
            Row {
                AsyncImageWithRainbowCircle(
                    model = character.imageUrl,
                    size = 128.dp,
                    strokeWidth = 12f
                )
                Spacer(modifier = Modifier.size(Paddings.oneAndHalf))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(128.dp)
                        .padding(vertical = Paddings.half, horizontal = Paddings.quarter),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = character.name.toString(),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                        )
                    Text(
                        text = "Status: ${character.status}",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    Text(
                        text = "Gender: ${character.gender}",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
            Spacer(modifier = Modifier.size(Paddings.one))
            Text(
                text =
                """
                  Meet the ${character.name}, enigmatic traveler from the far reaches of the multiverse. Armed with a quirky charm and unpredictable gadgets, they navigate chaos with a blend of wit and cosmic curiosity. A true anomaly in the cosmic tapestry of Rick and Morty's universe.
                """.trimIndent(),
                style = MaterialTheme.typography.bodyLarge
            )
            if (character.appearsInEpisodes != null) {
                LazyColumn {
                    items(character.appearsInEpisodes!!) { episode ->
                        Text(text = episode.name.toString())
                    }
                }
            }
        }
    }
}
