package com.example.rickandmortywiki.ui.charactersList

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
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
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.rickandmortywiki.data.entities.CharacterEntity
import com.example.rickandmortywiki.ui.Paddings
import com.example.rickandmortywiki.ui.characterInfo.CharacterInfoCardPreviewByLongTap
import com.example.rickandmortywiki.ui.components.AsyncImageWithRainbowCircle
import com.example.rickandmortywiki.ui.components.NoContentFound
import com.example.rickandmortywiki.ui.components.RickAndMortyTopAppBar
import com.example.rickandmortywiki.R

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CharactersListRenderer(
    onBackClick: () -> Unit,
    onCharacterClick: (CharacterEntity) -> Unit,
    episodeId: Int?,
    viewModel: CharactersListViewModel = hiltViewModel(
        creationCallback = { factory: CharactersListViewModel.CharactersListViewModelFactory ->
            factory.build(episodeId ?: -1)
        }),
) {
    val episodeWithCharacters =
        viewModel.episodeWithCharacters.collectAsState().value?.characters
    Scaffold(
        modifier = Modifier.background(MaterialTheme.colorScheme.background),
        topBar = {
            RickAndMortyTopAppBar(
                text = viewModel.episodeWithCharacters.collectAsState().value?.episode?.name
                    ?: stringResource(
                        id = R.string.oops
                    )
            ) {
                onBackClick()
            }
        },
    ) { innerPadding ->
        if (episodeWithCharacters != null) {
            LazyColumn(
                contentPadding = PaddingValues(Paddings.one),
                verticalArrangement = Arrangement.spacedBy(Paddings.half),
                modifier = Modifier.padding(top = innerPadding.calculateTopPadding())
            ) {
                items(episodeWithCharacters, key = { character -> character.characterId }) { item ->
                    CharactersListItem(
                        character = item,
                        viewModel = viewModel
                    ) { onCharacterClick(item) }
                }
            }
        } else {
            NoContentFound(
                modifier = Modifier.padding(top = innerPadding.calculateTopPadding())
            )
        }
    }
    viewModel.activeItem.collectAsStateWithLifecycle().value?.let { safeActiveItem ->
        CharacterInfoCardPreviewByLongTap(
            character = safeActiveItem,
            onClose = { viewModel.activeItem.value = null }
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CharactersListItem(
    character: CharacterEntity,
    viewModel: CharactersListViewModel,
    onClick: () -> Unit
) {

    val haptics = LocalHapticFeedback.current
    Surface(
        color = MaterialTheme.colorScheme.surfaceVariant,
        shape = MaterialTheme.shapes.medium,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .combinedClickable(
                    onClick = { onClick() },
                    onLongClick = {
                        haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                        viewModel.activeItem.value = character
                    },
                )
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
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                // By long tap make preview dialog
                // Body1 and Body2 diff 4.sp
                Text(
                    text = "Status: ${character.status}",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
