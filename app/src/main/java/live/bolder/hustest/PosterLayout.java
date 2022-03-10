package live.bolder.hustest;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Choreographer;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.widget.AppCompatTextView;

import java.util.Timer;
import java.util.TimerTask;

/*** hardcoded aspect ratio view for posters, to hopefully make recycler view smoother */

public class PosterLayout extends RelativeLayout implements Choreographer.FrameCallback {

    MoviesAdapter.MovieRow row;
    int detailsIndex = 0;
    ImageView posterImage;
    AppCompatTextView info_area_1;
    AppCompatTextView info_area_2;

    long startAnimation_time;
    long endAnimation_time;

    float getAnimationFraction() {
        long currentTime = System.nanoTime();
        return ( currentTime - startAnimation_time ) / ( float )( endAnimation_time - startAnimation_time );
    }

    /// return a number between a and b depending on fraction t
    float animateBetween( float a, float b, float t ) {
        if ( t < 0.0f )
            t = 0.0f;
        else if ( t > 1.0f )
            t = 1.0f;
        return b * t + a * ( 1.0f - t );
    }

    @Override
    public void doFrame(long frameTimeNanos) {
        switch( state ) {
            case AnimatingToDetails: {
                float animationFraction = getAnimationFraction();
                float posterScale = animateBetween( 1.0f, 0.33f, animationFraction );
                float aScale = animateBetween( 0.0f, 1.0f, animationFraction );
                posterImage.setScaleX( posterScale );
                posterImage.setScaleY( posterScale );
                info_area_1.setScaleX( aScale );
                info_area_1.setScaleY( aScale );
                info_area_2.setScaleX( aScale );
                info_area_2.setScaleY( aScale );
                if ( animationFraction >= 1.0f )
                    state = State.Details;
                else
                    Choreographer.getInstance().postFrameCallback( this );
                break;
            }

            case AnimatingToPoster: {
                float animationFraction = getAnimationFraction();
                float posterScale = animateBetween( 0.33f, 1.0f, animationFraction );
                float aScale = animateBetween( 1.0f, 0.0f, animationFraction );
                posterImage.setScaleX( posterScale );
                posterImage.setScaleY( posterScale );
                info_area_1.setScaleX( aScale );
                info_area_1.setScaleY( aScale );
                info_area_2.setScaleX( aScale );
                info_area_2.setScaleY( aScale );
                if ( animationFraction >= 1.0f )
                    state = State.Poster;
                else
                    Choreographer.getInstance().postFrameCallback( this );
                break;
            }
        }
    }

    public enum State {
        Poster,
        AnimatingToDetails,
        Details,
        AnimatingToPoster
    }

    State state;

    public PosterLayout(Context c, AttributeSet a) {
        super( c, a );
        state = State.Poster;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mode = MeasureSpec.getMode( widthMeasureSpec );
        int height = ( 3 * MeasureSpec.getSize( widthMeasureSpec ) ) / 2;
        heightMeasureSpec = MeasureSpec.makeMeasureSpec( height, mode );
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    void resetView( MoviesAdapter.MovieRow row ) {
        this.row = row;
        detailsIndex = 0; // show the first detail when in details view
        switch( row.state ) {
            case Poster:
                posterImage.setScaleX( 1.0f );
                posterImage.setScaleY( 1.0f );
                info_area_1.setScaleX( 0.0f );
                info_area_1.setScaleY( 0.0f );
                info_area_2.setScaleX( 0.0f );
                info_area_2.setScaleY( 0.0f );
                this.state = State.Poster;
                break;
            case Details:
                posterImage.setScaleX( 0.33f );
                posterImage.setScaleY( 0.33f );
                info_area_1.setScaleX( 1.0f );
                info_area_1.setScaleY( 1.0f );
                info_area_2.setScaleX( 1.0f );
                info_area_2.setScaleY( 1.0f );
                this.state = State.Details;
                break;
        }
    }

    void doDetailsAnimation() {
        if ( row == null || row.detailList == null || row.detailList.size()==0 )
            return;

        if ( --detailsIndex < 0 )
            detailsIndex = row.detailList.size() - 1;

        if ( info_area_1 == null || info_area_2 == null )
            return;

        MoviesAdapter.MovieRow.Detail detail = row.detailList.get( detailsIndex );

        info_area_1.setText( detail.key );
        info_area_2.setText( detail.value );
    }

    // periodically update the details text
    Timer detailsAnimationTimer;

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        if ( detailsAnimationTimer != null )
            detailsAnimationTimer.cancel();

        detailsAnimationTimer = new Timer();
        detailsAnimationTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                ( ( MainActivity )getContext() ).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        doDetailsAnimation();
                    }
                });
            }
        },0,1000);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        detailsAnimationTimer.cancel();
    }

    // return the state that we shall transition to....
    State toggleView() {
        switch( state ) {
            case Poster:
                startAnimation_time = System.nanoTime();
                endAnimation_time = startAnimation_time + 1000*1000*250;
                state = State.AnimatingToDetails;
                Choreographer.getInstance().postFrameCallback( this );
                return State.Details;
            case Details:
                startAnimation_time = System.nanoTime();
                endAnimation_time = startAnimation_time + 1000*1000*250;
                state = State.AnimatingToPoster;
                Choreographer.getInstance().postFrameCallback( this );
                return State.Poster;
        }
        return state;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        posterImage = findViewById( R.id.poster_image );
        info_area_1 = findViewById( R.id.info_area_1 );
        info_area_2 = findViewById( R.id.info_area_2 );
    }

    float dipToPx( float dip ) {
        Resources r = getResources();
        return TypedValue.applyDimension( TypedValue.COMPLEX_UNIT_DIP,
                dip,
                r.getDisplayMetrics()
        );
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        posterImage.setPivotX( 0 );
        posterImage.setPivotY( 0 );

        info_area_1.setPivotX( info_area_1.getWidth() );
        info_area_1.setPivotY( 0 );

        info_area_2.setPivotX( info_area_2.getWidth() );
        info_area_2.setPivotY( info_area_2.getHeight() );
    }

}
