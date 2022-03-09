package live.bolder.hustest;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Objects;

public class MovieViewModel extends ViewModel implements MovieCache.GetMovieUpdate {

    ArrayList<MutableLiveData<Movie>> movies;

    public MovieViewModel() {
        movies = new ArrayList<>();
        for ( int i=0; i<10; i++ )
            movies.add( new MutableLiveData<>() );
    }

    void addObserver( MainActivity activity, Observer<Movie> observer ) {
        for ( MutableLiveData<Movie> movie: movies )
            movie.observe( activity, observer );
    }

    class Movie {

        MovieResults.Result info;
        MovieDetails details;
        int index;
        PosterLayout.State state;

        Movie(MovieResults.Result info, MovieDetails details, int index, PosterLayout.State state ) {
            this.info = info;
            this.details = details;
            this.index = index;
            this.state = state;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Movie)) return false;
            Movie movie = (Movie) o;
            return index == movie.index && Objects.equals(info, movie.info) && Objects.equals(details, movie.details);
        }

        @Override
        public int hashCode() {
            return Objects.hash(info, details, index);
        }
    }

    @Override
    public void onMovieUpdated( MovieCache.MovieItem item ) {
        MutableLiveData<Movie> live_version = movies.get( item.index );
        PosterLayout.State state = live_version.getValue() == null ? PosterLayout.State.Poster : live_version.getValue().state;
        Movie new_version = new Movie( item.info, item.details, item.index, state );

        // equals doesn't care about Poster/Detail state so we don't trigger the observe...
        if ( !Objects.equals(live_version.getValue(), new_version ) )
            live_version.setValue( new_version );
    }
}
