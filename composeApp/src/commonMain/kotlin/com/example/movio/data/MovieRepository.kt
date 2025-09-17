package com.example.movio.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.movio.model.Movie
import com.example.movio.remote.DiscoverMoviesResponse
import com.example.movio.remote.MovieApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MovieRepository(private val api: MovieApi) {

    private val pager = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = { DataPaginator(api) }
    )

    fun fetchMovies(): Flow<PagingData<Movie>> = pager.flow.map { pagingData ->
        pagingData.map { result ->
            result.toMovie()
        }
    }

    private fun DiscoverMoviesResponse.Result.toMovie() = Movie(
        adult = adult,
        backdropPath = backdrop_path.orEmpty(),
        genreIds = genre_ids,
        id = id,
        originalLanguage = original_language,
        originalTitle = original_title,
        overview = overview,
        popularity = popularity,
        posterPath = poster_path.orEmpty(),
        poster = poster_path.orEmpty(),
        releaseDate = release_date.orEmpty(),
        title = title,
        video = video,
        voteAverage = vote_average,
        voteCount = vote_count
    )

}