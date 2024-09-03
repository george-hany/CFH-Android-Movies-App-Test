package com.core.data.network.interfaces

import com.core.data.model.login.MovieDetailsResponse
import com.core.data.model.login.MoviesResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AccountApis {
    @POST("movie/popular?api_key=777660159186d81259c9dcfa910ad0f1&page=")
    fun loginAsync(): Deferred<Response<MoviesResponse>>

    @GET("movie/now_playing?language=en-US&page=1")
    fun nowPlayingListAsync(): Deferred<Response<MoviesResponse>>

    @GET("movie/{id}?language=en-US")
    fun movieDetailsAsync(@Path("id") movieId: Int): Deferred<Response<MovieDetailsResponse>>
}
