package live.bolder.hustest;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/*** hardcoded aspect ratio view for posters, to hopefully make recycler view smoother */

public class PosterLayout extends RelativeLayout {

    public PosterLayout(Context c, AttributeSet a) {
        super( c, a );
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mode = MeasureSpec.getMode( widthMeasureSpec );
        int height = ( 3 * MeasureSpec.getSize( widthMeasureSpec ) ) / 2;
        heightMeasureSpec = MeasureSpec.makeMeasureSpec( mode, height );
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
