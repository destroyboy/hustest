package live.bolder.hustest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

    static class MovieRow {
        MovieResults.Result info;
        MovieDetails details;

        MovieRow( MovieResults.Result info ) {
            this.info = info;
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

    MoviesAdapter(Context context ) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = new ArrayList<>();
        setHasStableIds(true);
    }

    void updateMovie( MovieCache.MovieItem item ) {
        if ( mData.size() < 10 ) {
            mData.add( new MovieRow( item.info ) );
            notifyItemInserted( item.index );
        }
        else {
            MovieRow row = mData.get( item.index );
            row.info = item.info;
            row.details = item.details;
            notifyItemChanged( item.index );
        }
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
        MovieRow row = mData.get( position );
        Picasso.get().load( row.getPosterPath()).into(holder.posterView );
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

        ViewHolder(View itemView) {
            super(itemView);
            posterView = itemView.findViewById( R.id.poster_image );
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            ///if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }
}