package cn.edu.jlu.animation;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by paworks on 18-2-7.
 */

public class AutoMarqueeTextview extends android.support.v7.widget.AppCompatTextView {

    public AutoMarqueeTextview(Context context) {
        super(context);
        setFocusable(true);
    }

    public AutoMarqueeTextview(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setFocusable(true);
    }

    public AutoMarqueeTextview(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFocusable(true);
    }

    @Override
    public boolean isFocused() {
        return true;
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction,
                                  Rect previouslyFocusedRect) {
    }
}
