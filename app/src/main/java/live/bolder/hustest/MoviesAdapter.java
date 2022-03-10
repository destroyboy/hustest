package live.bolder.hustest;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import live.bolder.hustest.kotlin.MainViewModel;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

    static class MovieRow {
        MovieResults.Result info;
        MovieDetails details;

        class Detail {
            String key;
            String value;
            Detail( String key, String value ) {
                this.key = key;
                this.value = value;
            }
        }

        ArrayList<Detail> detailList;

        /// whether we display poster or details
        PosterLayout.State state = PosterLayout.State.Poster;

        MovieRow( MovieResults.Result info, PosterLayout.State state ) {
            this.info = info;
            this.state = state;
        }
        int getId() {
            return info.id;
        }

        String getPosterPath() {
            return "https://image.tmdb.org/t/p/original"+ info.posterPath;
        }

        void generateDetailsList() {

            detailList = new ArrayList<>();

            if ( details == null )
                return;
            try {
                JSONObject jsonObject = new JSONObject(new Gson().toJson( details ));
                Iterator<String> keys = jsonObject.keys();

                while(keys.hasNext()) {
                    String key = keys.next();
                    detailList.add( new Detail( key, jsonObject.get( key ).toString( ) ) );
                }
            }
            catch ( JSONException e ) {
                e.printStackTrace();
            }
        }
    }

    private List<MovieRow> mData;
    private LayoutInflater mInflater;
    private RecyclerView.LayoutManager layoutManager;

    RecyclerView.SmoothScroller smoothScroller;

    MoviesAdapter(Context context ) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = new ArrayList<>();
        setHasStableIds(true);

        smoothScroller = new LinearSmoothScroller( context ) {
            @Override protected int getVerticalSnapPreference() {
                return LinearSmoothScroller.SNAP_TO_START;
            }

            @Override
            protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                final float MILLISECONDS_PER_INCH = 50;
                return MILLISECONDS_PER_INCH / displayMetrics.densityDpi;
            }
        };
    }

    void updateMovie( MovieResults.Result info, MovieDetails details, int index, PosterLayout.State state ) {
        if ( mData.size() < 10 ) {
            mData.add( new MovieRow( info, state ) );
            notifyItemInserted( index );
        }
        else {
            MovieRow row = mData.get( index );
            row.info = info;
            row.details = details;
            row.state = state;
            row.generateDetailsList();
            notifyItemChanged( index );
        }
    }

    void updateFromKotlin( ArrayList<MainViewModel.Movie> movies ) {
        Log.d( "TMDB", "updating from kotlin" );
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        layoutManager = recyclerView.getLayoutManager();
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType ) {
        View view = mInflater.inflate( R.layout.film_row, parent, false );
        view.findViewById(R.id.poster_image).setClipToOutline( true );
        return new ViewHolder( view );
    }

    @Override
    public void onBindViewHolder( ViewHolder holder, int position ) {
        holder.row = mData.get( position );
        holder.posterLayout.resetView( holder.row );
        Picasso.get().load( holder.row.getPosterPath()).resize( 400, 600 ).placeholder( R.drawable.imagemissing ).into( holder.posterView );
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public long getItemId( int position ) {
        return mData.get( position ).getId();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView posterView;
        PosterLayout posterLayout;
        MovieRow row;
        TextView text1;

        ViewHolder(View itemView) {
            super(itemView);
            posterView = itemView.findViewById( R.id.poster_image );
            posterLayout = itemView.findViewById( R.id.poster_layout );
            text1 = itemView.findViewById( R.id.info_area_1 );
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            smoothScroller.setTargetPosition( getAdapterPosition() );
            layoutManager.startSmoothScroll( smoothScroller );
            row.state = posterLayout.toggleView();
            try {
                ( ( MainActivity )view.getContext( ) ).getMovieViewModel().movies.get( getAdapterPosition() ).getValue().state = row.state;
            }
            catch ( NullPointerException ignore ) {

            }
            ///if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }
}