package com.example.rickandmortywiki.ui.charactersList.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.rickandmortywiki.R
import com.example.rickandmortywiki.data.entities.CharacterEntity
import com.example.rickandmortywiki.ui.components.CommonListItemWrapper

@Composable
fun CharactersListItem(character: CharacterEntity, onClick: () -> Unit) {
    CommonListItemWrapper {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onClick()
                }
                .padding(5.dp)
        ) {
            AsyncImage(
                model = character.imageUrl,
                contentDescription = "${character.name}",
                modifier = Modifier.clip(CircleShape),
                placeholder = painterResource(id = R.drawable.placeholder_character_image)
            )
            Column(
                modifier = Modifier.padding(10.dp)
            ) {
                Text(
                    character.name ?: "Unknown", style = MaterialTheme.typography.h4.copy(
                        fontWeight = FontWeight.ExtraLight
                    )
                )
                Text("Status: ${character.status}", style = MaterialTheme.typography.subtitle1)
            }
        }
    }
}
