package com.example.nbabrowser.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.nbabrowser.model.Player
import com.example.nbabrowser.network.PAGE_SIZE

/**
 * Wraps pagination logic
 */
class PlayerPagingSource(
    private val repository: NBARepository
): PagingSource<Int, Player>() {
    override fun getRefreshKey(state: PagingState<Int, Player>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Player> {
        return try {
            val currentPage = params.key ?: 0
            val cursor = currentPage * PAGE_SIZE

            val response = repository.getPlayers(cursor)

            LoadResult.Page(
                data = response.data,
                prevKey = if (currentPage == 0) { null } else { currentPage - 1 },
                nextKey = if (response.data.isEmpty()) { null} else { currentPage + 1 }
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
