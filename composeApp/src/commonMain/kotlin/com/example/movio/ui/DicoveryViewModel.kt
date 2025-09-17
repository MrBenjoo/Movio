package com.example.movio.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.movio.data.MovieRepository

class DiscoveryViewModel(movieRepository: MovieRepository) : ViewModel() {

    val movies = movieRepository
        .fetchMovies()
        .cachedIn(viewModelScope)

}