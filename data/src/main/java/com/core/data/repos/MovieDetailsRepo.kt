package com.core.data.repos

import com.core.data.base.BaseRepo
import com.core.data.model.login.MovieDetailsResponse
import com.core.data.network.ApiFactory
import com.core.data.network.NetworkBoundFileResource
import com.core.data.network.model.ResponseState
import com.core.data.strategy.DataStrategy
import com.core.network.NetworkFactoryInterface
import com.core.preference.DataStores
import com.core.utils.FileManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.MutableStateFlow
import retrofit2.Response
import javax.inject.Inject

class MovieDetailsRepo
@Inject
constructor(
    val apiFactory: ApiFactory,
    dataStore: DataStores,
    networkFactory: NetworkFactoryInterface,
    val fileManager: FileManager
) : BaseRepo(dataStore, networkFactory) {
    fun requestMoviesDetails(movieId: Int): MutableStateFlow<ResponseState<MovieDetailsResponse?>> {
        return object : NetworkBoundFileResource<MovieDetailsResponse>(
            networkFactory,
            fileName = "MoviesDetails_id_$movieId",
            fileManager = fileManager,
            strategy = DataStrategy.Strategies.DEFAULT_STRATEGY
        ) {
            override fun convert(json: String): MovieDetailsResponse? {
                return Gson().fromJson(json, object : TypeToken<MovieDetailsResponse>() {}.type)
            }

            override suspend fun createCall(): suspend () -> Response<MovieDetailsResponse> =
                {
                    apiFactory.getAccountApis().movieDetailsAsync(movieId).await()
                }
        }.asFlow()
    }
}
