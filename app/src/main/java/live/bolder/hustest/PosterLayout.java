package live.bolder.hustest;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Choreographer;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/*** hardcoded aspect ratio view for posters, to hopefully make recycler view smoother */

public class PosterLayout extends RelativeLayout implements Choreographer.FrameCallback {

    ImageView posterImage;
    View info_area_1;
    View info_area_2;

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

    enum State {
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

    void resetView( State state ) {
        switch( state ) {
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

    boolean childSizeSet = false;

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        float w = getWidth();
        float h = getHeight();

        float a1_l = ( w * 0.33f + dipToPx( 18 ) );
        float a1_t = dipToPx( 24 );
        float a1_r = w - dipToPx( 20 );
        float a1_b = w * 0.515f;

        float a2_l = dipToPx( 18 );
        float a2_t = w * 0.51f + dipToPx( 24 );
        float a2_r = w - dipToPx( 20 );
        float a2_b = h - dipToPx( 24 );

        if ( !childSizeSet ) {
            android.view.ViewGroup.LayoutParams a1_params = info_area_1.getLayoutParams();
            a1_params.width = ( int )( a1_r - a1_l );
            a1_params.height = ( int ) ( a1_b - a1_t );
            info_area_1.setLayoutParams(a1_params);

            android.view.ViewGroup.LayoutParams a2_params = info_area_2.getLayoutParams();
            a2_params.width = ( int )( a2_r - a2_l );
            a2_params.height = ( int ) ( a2_b - a2_t );
            info_area_2.setLayoutParams(a2_params);
            childSizeSet = true;
        }

        info_area_1.layout( ( int )a1_l, ( int )a1_t, ( int )a1_r, ( int )a1_b );
        info_area_2.layout( ( int )a2_l, ( int )a2_t, ( int )a2_r, ( int )a2_b );

        posterImage.setPivotX( 0 );
        posterImage.setPivotY( 0 );

        info_area_1.setPivotX( info_area_1.getWidth() );
        info_area_1.setPivotY( 0 );

        info_area_2.setPivotX( info_area_2.getWidth() );
        info_area_2.setPivotY( info_area_2.getHeight() );
    }

}
