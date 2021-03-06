package live.bolder.hustest

import androidx.appcompat.app.AppCompatActivity
import live.bolder.hustest.DiskCache.DiskCacheLoaded
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import live.bolder.hustest.kotlin.MovieRepository
import live.bolder.hustest.kotlin.MainViewModel
import java.util.ArrayList

class MainActivity : AppCompatActivity(), DiskCacheLoaded, LifecycleOwner {

    private lateinit var diskCache: DiskCache
    private lateinit var movieCache: MovieCache
    private lateinit var moviesAdapter: MoviesAdapter

    lateinit var movieViewModel: MovieViewModel

    override fun onDiskCacheCreated(diskCache: DiskCache) {
        movieCache = MovieCache(diskCache, movieViewModel)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        movieViewModel = ViewModelProvider(this)[ MovieViewModel::class.java ]
        val movieObserver = Observer { movie: MovieViewModel.Movie ->
            moviesAdapter.updateMovie(
                movie.info,
                movie.details,
                movie.index,
                movie.state
            )
        }
        movieViewModel.addObserver(this, movieObserver)
        diskCache = DiskCache(this, this)
        val moviesList: RecyclerView = findViewById(R.id.films)
        //moviesList.setHasFixedSize( true )
        moviesList.layoutManager = LinearLayoutManager(this)
        moviesAdapter = MoviesAdapter(this)
        moviesList.adapter = moviesAdapter

        /// new kotlin code, to demonstrate use of coroutines
        val kMovieObserver = Observer { movies: ArrayList<MainViewModel.Movie> ->
            moviesAdapter.updateFromKotlin( movies )
        }
        val mainViewModel = MainViewModel( MovieRepository() )
        mainViewModel.addObserver( this, kMovieObserver )
        mainViewModel.refreshPopularMovies()
    }
}