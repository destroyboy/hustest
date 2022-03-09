package live.bolder.hustest;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Choreographer;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/*** hardcoded aspect ratio view for posters, to hopefully make recycler view smoother */

public class PosterLayout extends RelativeLayout {

    ImageView posterImage;
    Animation shrink_poster;
    Animation expand_poster;
    RelativeLayout info_area_1;
    RelativeLayout info_area_2;

    enum State {
        Poster,
        AnimatingToDetails,
        Details,
        AnimatingToPoster
    }

    State state;

    public PosterLayout(Context c, AttributeSet a) {
        super( c, a );

        shrink_poster = new ScaleAnimation(
                1f, 0.33f, // Start and end values for the X axis scaling
                1.0f, 0.33f, // Start and end values for the Y axis scaling
                Animation.RELATIVE_TO_SELF, 0f, // Pivot point of X scaling
                Animation.RELATIVE_TO_SELF, 0f); // Pivot point of Y scaling
        shrink_poster.setFillAfter(true); // Needed to keep the result of the animation
        shrink_poster.setDuration( 500 );
        shrink_poster.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                state = State.AnimatingToDetails;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                state = State.Details;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        expand_poster = new ScaleAnimation(
                0.33f, 1.0f, // Start and end values for the X axis scaling
                0.33f, 1.0f, // Start and end values for the Y axis scaling
                Animation.RELATIVE_TO_SELF, 0f, // Pivot point of X scaling
                Animation.RELATIVE_TO_SELF, 0f); // Pivot point of Y scaling
        expand_poster.setFillAfter(true); // Needed to keep the result of the animation
        expand_poster.setDuration( 500 );
        expand_poster.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                state = State.AnimatingToPoster;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                state = State.Poster;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        state = State.Poster;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mode = MeasureSpec.getMode( widthMeasureSpec );
        int height = ( 3 * MeasureSpec.getSize( widthMeasureSpec ) ) / 2;
        heightMeasureSpec = MeasureSpec.makeMeasureSpec( height, mode );
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    void animateToDetails() {
        switch ( state ) {
            case Poster:
                posterImage.startAnimation( shrink_poster );
                break;
            case AnimatingToDetails:
            case Details:
                break;
            case AnimatingToPoster:
                expand_poster.cancel();
                state = State.Details;
                break;
        }
    }

    void resetView() {
        shrink_poster.cancel();
        expand_poster.cancel();
        state = State.Poster;
        posterImage.setScaleX( 1.0f );
        posterImage.setScaleY( 1.0f );
    }

    void animateToPoster() {
        switch ( state ) {
            case Details:
                posterImage.startAnimation( expand_poster );
                break;
            case AnimatingToPoster:
            case Poster:
                break;
            case AnimatingToDetails:
                shrink_poster.cancel();
                state = State.Poster;
                break;
        }
    }

    void toggleView() {
        switch ( state ) {
            case Details:
            //case AnimatingToDetails:
                animateToPoster();
                break;
            case Poster:
            //case AnimatingToPoster:
                animateToDetails();
                break;
        }
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
        float w = getWidth();
        float h = getHeight();
        info_area_1.layout(
                ( int ) ( w * 0.33f + dipToPx( 18 ) ),
                ( int )dipToPx( 24 ),
                ( int ) w - ( int )dipToPx( 20 ),
                ( int )( w * 0.515f ) );

        info_area_2.layout(
                ( int )dipToPx( 18 ),
                ( int )( w * 0.51f + dipToPx( 24 ) ),
                ( int ) w - ( int )dipToPx( 20 ),
                ( int )( h - dipToPx( 24 ) ) );
    }
}
