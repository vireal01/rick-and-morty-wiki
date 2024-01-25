package com.example.rickandmortywiki.ui.episodes

import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Surface
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.rickandmortywiki.R
import com.example.rickandmortywiki.data.entities.EpisodeEntity
import com.example.rickandmortywiki.ui.Paddings
import com.example.rickandmortywiki.ui.components.RickAndMortyTopAppBar
import com.example.rickandmortywiki.utils.prettifyLastUpdateText

@Composable
fun EpisodesListRenderer(
    onEpisodeClick: (EpisodeEntity) -> Unit = {},
    viewModel: EpisodesViewModel = hiltViewModel(),
) {
    val episodes = viewModel.episodesList.collectAsState().value
    val loadModeBtnState = viewModel.loadMoreBtnState.collectAsState().value

    Scaffold(
        modifier = Modifier.background(MaterialTheme.colorScheme.background),
        topBar = {
            RickAndMortyTopAppBar(
                text = stringResource(id = R.string.episodes_title)
            )
        },
    ) { innerPadding ->
        LazyColumn(
            contentPadding = PaddingValues(Paddings.one),
            verticalArrangement = Arrangement.spacedBy(Paddings.half),
            modifier = Modifier.padding(top = innerPadding.calculateTopPadding()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(episodes, key = { episode -> episode.episodeId }) { episode ->
                EpisodesListItem(
                    episode
                ) {
                    onEpisodeClick(episode)
                }
            }
            if (loadModeBtnState == View.VISIBLE) {
                item {
                    Button(
                        onClick = { viewModel.onLoadMoreBtnClicked() },
                    ) {
                        Text(text = stringResource(id = R.string.load_more_btn))
                    }
                }
            }
        }
    }
}

@Composable
fun EpisodesListItem(episode: EpisodeEntity, onClick: () -> Unit) {
    Surface(
        color = MaterialTheme.colorScheme.surfaceVariant,
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier.semantics {
            this.contentDescription = EpisodesListTags.episodeContainer
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() }
                .padding(Paddings.one)
                .height(64.dp - Paddings.one),
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Text(
                text = episode.name.toString(),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.semantics {
                    contentDescription = EpisodesListTags.episodeTitle
                },
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = episode.episode.toString(),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Left,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.semantics {
                        contentDescription = EpisodesListTags.episodeTag
                    },
                )
                Spacer(modifier = Modifier.width(Paddings.one))
                Text(
                    text = prettifyLastUpdateText(episode.lastUpdate),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.ExtraLight,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

object EpisodesListTags {
    const val episodeTitle = "episode_list_episode_title"
    const val episodeTag = "episode_list_episode_tag"
    const val episodeContainer = "episode_list_episode_container"
}
