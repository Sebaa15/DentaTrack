package org.ucb.dentatrack.Movies.presentation.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import org.ucb.dentatrack.Movies.presentation.viewmodel.CatalogEvent
import org.ucb.dentatrack.Movies.presentation.viewmodel.CatalogState
import coil3.compose.AsyncImage

@Composable
fun CatalogContent(
    state: CatalogState,
    onEvent: (CatalogEvent) -> Unit
) {

    when {

        state.isLoading -> {

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        state.error != null -> {

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = state.error
                )

                Button(
                    onClick = {
                        onEvent(CatalogEvent.Retry)
                    }
                ) {
                    Text("Reintentar")
                }
            }
        }

        else -> {

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(8.dp),
                horizontalArrangement =
                    Arrangement.spacedBy(8.dp),
                verticalArrangement =
                    Arrangement.spacedBy(10.dp)
            ) {

                items(state.movies) { movie ->

                    Column(
                        modifier =
                            Modifier.fillMaxWidth(),
                        horizontalAlignment =
                            Alignment.CenterHorizontally
                    ) {

                        AsyncImage(
                            model =
                                if (movie.posterPath.isNotBlank()) {
                                    "https://image.tmdb.org/t/p/w500${movie.posterPath}"
                                } else {
                                    null
                                },
                            contentDescription =
                                movie.title,
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .aspectRatio(2f / 3f),
                            contentScale =
                                ContentScale.Crop
                        )

                        Text(
                            text = movie.title,
                            modifier =
                                Modifier.padding(top = 4.dp),
                            textAlign =
                                TextAlign.Center,
                            maxLines = 2,
                            overflow =
                                TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }
}