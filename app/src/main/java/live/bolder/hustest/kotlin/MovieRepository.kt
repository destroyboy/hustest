package live.bolder.hustest.kotlin

import live.bolder.hustest.MovieDetails
import live.bolder.hustest.MovieResults
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MovieRepository {

    companion object {
        private const val BASE_URL = "https://api.themoviedb.org"
        private const val PAGE = 1
        private const val API_KEY = "b8e7895cef48bee2b231b6ba1e9b0425"
        private const val LANGUAGE = "en-US"
        private const val CATEGORY = "popular"

        private val retrofitService: MovieInterface = Retrofit.Builder()
            .baseUrl( BASE_URL )
            .addConverterFactory( GsonConverterFactory.create( ) )
            .build( ).create( MovieInterface::class.java )
    }

    suspend fun getMovies(): Response<MovieResults> {
        val result = retrofitService.getMovies( CATEGORY, API_KEY, LANGUAGE, PAGE )
        val movies = result.body()!!.results
        // we only care about top ten so... remove
        // pretty dumb way to do it I know!
        while ( movies.size > 10 )
            movies.removeLast()
        return result
    }

    suspend fun getMovieDetails( id : Int ): Response<MovieDetails> {
        return retrofitService.getMovieDetails( id, API_KEY, LANGUAGE )
    }
}