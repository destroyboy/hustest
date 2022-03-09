package live.bolder.hustest;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

    static class MovieRow {
        MovieResults.Result info;
        MovieDetails details;

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
            notifyItemChanged( index );
        }
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
        holder.posterLayout.resetView( holder.row.state );
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

        ViewHolder(View itemView) {
            super(itemView);
            posterView = itemView.findViewById( R.id.poster_image );
            posterLayout = itemView.findViewById( R.id.poster_layout );
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