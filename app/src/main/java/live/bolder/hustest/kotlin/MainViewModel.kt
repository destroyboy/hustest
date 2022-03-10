package live.bolder.hustest.kotlin

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import live.bolder.hustest.MovieDetails
import live.bolder.hustest.MovieResults
import live.bolder.hustest.PosterLayout
import java.util.*
import kotlin.collections.ArrayList

class MainViewModel constructor(private val mainRepository: MovieRepository) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.d("TMDB", "Exception handled: ${throwable.localizedMessage}")
    }

    class Movie(
        var info: MovieResults.Result,
        var details: MovieDetails?,
        var index: Int,
        var state: PosterLayout.State
    ) {
        override fun equals(o: Any?): Boolean {
            if (this === o) return true
            if (o !is Movie) return false
            val movie = o
            return index == movie.index && info == movie.info && details == movie.details
        }

        override fun hashCode(): Int {
            return Objects.hash(info, details, index)
        }
    }

    var movies: MutableLiveData<ArrayList<Movie>> =  MutableLiveData<ArrayList<Movie>>()

    fun refreshPopularMovies() {
        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val moviesNew: ArrayList<Movie> = ArrayList()
            val movieResults = mainRepository.getMovies().body()!!.results
            for ( i in 0..movieResults.size ) {
                val m = Movie( movieResults[ i ], null, i, PosterLayout.State.Poster )
                moviesNew.add( m )
                val details = mainRepository.getMovieDetails( m.info.id )
                m.details = details.body()
            }

            withContext(Dispatchers.Main) {
                movies.value = moviesNew
            }
        }
    }
}