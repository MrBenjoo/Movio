package com.example.movio.ui

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil3.compose.AsyncImage
import com.example.movio.di.sharedModule
import com.example.movio.model.Movie
import com.example.movio.theme.AppTheme
import kotlinx.coroutines.delay
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun DiscoverScreen() {
    KoinApplication(
        application = { modules(sharedModule) }
    ) {
        val vm = koinViewModel<DiscoveryViewModel>()
        val lazyPagingItems = vm.movies.collectAsLazyPagingItems()

        val isRefreshing by remember {
            derivedStateOf {
                lazyPagingItems.loadState.refresh is LoadState.Loading
            }
        }

        val isAppending by remember {
            derivedStateOf {
                lazyPagingItems.loadState.append is LoadState.Loading
            }
        }
        AppTheme {
            DiscoveryGridContent(
                itemCount = lazyPagingItems.itemCount,
                itemAt = { index -> lazyPagingItems[index] },
                isRefreshing = isRefreshing,
                isAppending = isAppending
            )
        }
    }

}

@Composable
private fun DiscoveryGridContent(
    itemCount: Int,
    itemAt: (Int) -> Movie?,
    isRefreshing: Boolean,
    isAppending: Boolean
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Crossfade(
            targetState = isRefreshing,
            modifier = Modifier.fillMaxSize()
        ) { refresh ->
            if (refresh) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(120.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    items(itemCount) { index ->
                        itemAt(index)?.let { movie ->
                            MovieCard(movie)
                        }
                    }

                    item {
                        if (isAppending) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun MovieCard(movie: Movie) {
    Card(modifier = Modifier.aspectRatio(2 / 3f)) {
        AsyncImage(
            model = movie.getPosterUrl(),
            contentDescription = movie.title,
            contentScale = ContentScale.Crop
        )
    }
}

@Preview
@Composable
private fun PreviewDisocveryGridContent() {
    val sampleMovies = List(20) {
        Movie(
            id = it,
            title = "Movie $it",
            overview = "Overview $it",
            posterPath = "",
            backdropPath = "",
            releaseDate = "2023-01-01",
            voteAverage = 5.0,
            voteCount = 100,
            adult = false,
            genreIds = emptyList(),
            originalLanguage = "sv",
            originalTitle = "sv",
            popularity = 1.0,
            video = true,
            poster = ""
        )
    }
    var isRefreshing by remember { mutableStateOf(true) }
    var isAppending by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        while (true) {
            isRefreshing = true
            delay(2000)
            isRefreshing = false
            delay(2000)
            isAppending = true
            delay(2000)
            isAppending = false
            delay(2000)
        }
    }

    DiscoveryGridContent(
        itemCount = sampleMovies.size,
        itemAt = { index -> sampleMovies.getOrNull(index) },
        isRefreshing = isRefreshing,
        isAppending = isAppending
    )
}