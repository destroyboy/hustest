package live.bolder.hustest

import androidx.appcompat.app.AppCompatActivity
import live.bolder.hustest.DiskCache.DiskCacheLoaded
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import live.bolder.hustest.MovieCache.MovieItem
import androidx.recyclerview.widget.LinearLayoutManager

class MainActivity : AppCompatActivity(), DiskCacheLoaded, LifecycleOwner {

    private var diskCache: DiskCache? = null
    private var movieCache: MovieCache? = null
    private var moviesAdapter: MoviesAdapter? = null
    private var movieViewModel: MovieViewModel? = null

    override fun onDiskCacheCreated(diskCache: DiskCache) {
        movieCache = MovieCache(diskCache, movieViewModel)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        movieViewModel = ViewModelProvider(this)[ MovieViewModel::class.java ]
        val movieObserver = Observer { movie: MovieViewModel.Movie ->
            moviesAdapter!!.updateMovie(
                MovieItem(
                    movie.index,
                    movie.info,
                    movie.details
                )
            )
        }
        movieViewModel!!.addObserver(this, movieObserver)
        diskCache = DiskCache(this, this)
        val moviesList: RecyclerView = findViewById(R.id.films)
        moviesList.layoutManager = LinearLayoutManager(this)
        moviesAdapter = MoviesAdapter(this)
        moviesList.adapter = moviesAdapter
    }
}