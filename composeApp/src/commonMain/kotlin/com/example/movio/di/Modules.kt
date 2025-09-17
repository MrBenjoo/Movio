package com.example.movio.di

import com.example.movio.data.MovieRepository
import com.example.movio.remote.MovieApi
import com.example.movio.ui.DiscoveryViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val provideApi = module {
    single {
        MovieApi()
    }
}

val provideRepository = module {
    single {
        MovieRepository(get())
    }
}

val provideDiscoveryViewModel = module {
    viewModelOf(::DiscoveryViewModel)
}

val sharedModule = listOf(
    provideApi,
    provideRepository,
    provideDiscoveryViewModel
)