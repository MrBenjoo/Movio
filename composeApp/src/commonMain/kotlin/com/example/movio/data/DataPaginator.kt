package com.example.movio.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.movio.debug
import com.example.movio.remote.DiscoverMoviesResponse
import com.example.movio.remote.MovieApi
import io.ktor.client.call.body
import io.ktor.http.isSuccess

class DataPaginator(private val api: MovieApi): PagingSource<Int, DiscoverMoviesResponse.Result>() {

    private val tag = "DataPaginator"

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DiscoverMoviesResponse.Result> {
        return try {
            val nextPageNumber = params.key ?: 1
            debug(tag, "Load next item for page: $nextPageNumber")
            val response = api.fetchDiscovery(nextPageNumber)
            if (response.status.isSuccess()) {
                val result = response.body<DiscoverMoviesResponse>()
                val data = result.results
                val nextKey = if (data.isEmpty()) {
                    null
                } else {
                    (result.page + 1).takeIf { result.page < result.total_pages }
                }
                debug(tag, "Fetched ${data.size} movies for page ${result.page}, nextKey: $nextKey")
                LoadResult.Page(
                    data = data,
                    prevKey = null,
                    nextKey = nextKey
                )
            } else {
                debug(tag, "HTTP error: ${response.status}")
                LoadResult.Error(
                    Exception("HTTP ${response.status.value}: ${response.status.description}")
                )
            }
        } catch (e: Exception) {
            debug(tag, "Exception during load: ${e.message}")
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, DiscoverMoviesResponse.Result>): Int? {
        return state.anchorPosition?.let { anchor ->
            state.closestPageToPosition(anchor)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchor)?.nextKey?.minus(1)
        }
    }
}