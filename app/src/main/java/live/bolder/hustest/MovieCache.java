package live.bolder.hustest;

import android.util.Log;

import java.io.Serializable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieCache {

    static final String BASE_URL = "https://api.themoviedb.org";
    static final int PAGE = 1;
    static final String API_KEY = "b8e7895cef48bee2b231b6ba1e9b0425";
    static final String LANGUAGE = "en-US";
    static final String CATEGORY = "popular";

    static class MovieItem {
        MovieItem( int index, MovieResults.Result info, MovieDetails details ) {
            this.index = index;
            this.info = info;
            this.details = details;
        }
        int index;
        MovieResults.Result info;
        MovieDetails details;
    }

    interface GetMovieUpdate {
        void onMovieUpdated( MovieItem item );
    }

    MovieItem[] movieItems = new MovieItem[ 10 ];
    DiskCache diskCache;
    GetMovieUpdate observer;
    MovieInterface movieInterface;

    interface MovieResultsCallback {
        void onResult( MovieResults results );
    }

    interface MovieDetailsCallback {
        void onResult( MovieDetails results );
    }

    void getMovies( MovieResultsCallback callback ) {
        Call<MovieResults> call = movieInterface.getMovies( CATEGORY, API_KEY, LANGUAGE, PAGE );
        call.enqueue(new Callback<MovieResults>() {
            @Override
            public void onResponse(Call<MovieResults> call, Response<MovieResults> response) {
                /// FIXME: yucky but removes extra movies, so we can compare...
                while ( response.body().results.size() > 10 )
                    response.body().results.remove( response.body().results.size() - 1 );
                callback.onResult( response.body() );
            }

            @Override
            public void onFailure(Call<MovieResults> call, Throwable t) {
                callback.onResult( null );
            }
        });
    }

    void getMovieDetails( int id, MovieDetailsCallback callback ) {
        Call<MovieDetails> detailsCall = movieInterface.getMovieDetails( id, API_KEY, LANGUAGE );
        detailsCall.enqueue(new Callback<MovieDetails>() {
            @Override
            public void onResponse(Call<MovieDetails> call, Response<MovieDetails> response) {
                callback.onResult( response.body() );
            }

            @Override
            public void onFailure(Call<MovieDetails> call, Throwable t) {
                callback.onResult( null );
            }
        });
    }

    MovieCache( DiskCache diskCache, GetMovieUpdate observer ) {
        this.diskCache = diskCache;
        this.observer = observer;
        movieInterface = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL).build().create(MovieInterface.class);

        for ( int i=0; i < movieItems.length; i++ )
            movieItems[ i ] = new MovieItem( i, null, null );

        diskCache.getSerializable("top_ten_movies", new DiskCache.SerializableCallback() {
            @Override
            public void onResult(String key, Serializable value) {
                if ( value != null ) {
                    MovieResults moviesFromDisk = ( MovieResults )value;
                    for ( int i=0; i<moviesFromDisk.results.size(); i++ ) {
                        MovieItem movie  = movieItems[ i ];
                        movie.info = moviesFromDisk.results.get( i );
                        observer.onMovieUpdated( movie );
                        diskCache.getSerializable("movie_details_" + movie.info.id, new DiskCache.SerializableCallback() {
                            @Override
                            public void onResult(String key, Serializable value) {
                                if ( value != null ) {
                                    movie.details = ( MovieDetails) value;
                                    observer.onMovieUpdated( movie );
                                }
                            }
                        });
                    }
                }

                getMovies(new MovieResultsCallback() {
                    @Override
                    public void onResult( MovieResults results ) {
                        if ( results != null && value != null && value.equals( results ) ) {
                            Log.d( "TMDB", "results match!" );
                        }
                        else if ( results != null ) {
                            for ( int i=0; i<results.results.size(); i++ ) {
                                MovieItem movie = movieItems[ i ];
                                movie.info = results.results.get( i );
                                if ( movie.details != null && movie.details.id != movie.info.id )
                                    movie.details = null;
                                observer.onMovieUpdated( movie );
                            }
                            diskCache.putSerializable("top_ten_movies", results, null );
                        }

                        for ( int i=0; i<movieItems.length; i++ ) {
                            MovieItem movie = movieItems[ i ];
                            getMovieDetails( movie.info.id, new MovieDetailsCallback() {
                                @Override
                                public void onResult( MovieDetails results ) {
                                    if ( results != null && !results.equals( movie.details ) ) {
                                        movie.details = results;
                                        observer.onMovieUpdated( movie );
                                        diskCache.putSerializable("movie_details_" + movie.info.id, results, null );
                                    }

                                }
                            });
                        }

                    }
                });
            }
        });
    }
}
