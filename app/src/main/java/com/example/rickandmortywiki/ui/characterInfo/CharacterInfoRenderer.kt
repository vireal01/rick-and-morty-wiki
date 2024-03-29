package com.example.rickandmortywiki.ui.characterInfo

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.rickandmortywiki.data.entities.CharacterEntity
import com.example.rickandmortywiki.ui.Paddings
import com.example.rickandmortywiki.ui.components.AsyncImageWithRainbowCircle
import com.example.rickandmortywiki.ui.components.RickAndMortyTopAppBar
import com.example.rickandmortywiki.ui.components.NoContentFound
import com.example.rickandmortywiki.R

@Composable
fun CharacterInfoRenderer(
    characterId: Int?,
    viewModel: CharacterInfoViewModel = hiltViewModel(
        creationCallback = { factory: CharacterInfoViewModel.CharacterInfoViewModelFactory ->
            factory.build(characterId ?: -1)
        }),
    onBackClick: () -> Unit,
) {
    val character = viewModel.character.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.background(MaterialTheme.colorScheme.background),
        topBar = {
            RickAndMortyTopAppBar(
                text = character.value?.name ?: stringResource(id = R.string.oops),
            ) {
                onBackClick()
            }
        }
    ) { innerPadding ->
        if (character.value == null) {
            NoContentFound(
                modifier = Modifier.padding(top = innerPadding.calculateTopPadding())
            )
        } else {
            Column(
                modifier = Modifier.padding(innerPadding)
            ) {
                character.value?.let { safeValue ->
                    CharacterInfoCard(safeValue)
                }
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
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = "Gender: ${character.gender}",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
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
                    items(
                        character.appearsInEpisodes!!,
                        key = { episode -> episode.episodeId }) { episode ->
                        Text(text = episode.name.toString())
                    }
                }
            }
        }
    }
}

@Composable
fun CharacterInfoCardPreviewByLongTap(character: CharacterEntity, onClose: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(onClose) { detectTapGestures { onClose() } }
            .semantics(mergeDescendants = true) {
                contentDescription = "Close"
                onClick {
                    onClose()
                    true
                }
            }
            .onKeyEvent {
                if (it.key == Key.Escape) {
                    onClose()
                    true
                } else {
                    false
                }
            }
            .background(Color.DarkGray.copy(alpha = 0.75f))
    ) {
        CharacterInfoCard(character = character)
    }
}
