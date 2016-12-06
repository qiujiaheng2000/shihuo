
package com.shihuo.shihuo.Views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

import java.lang.ref.WeakReference;

/**
 * Created by lishuai on 16/11/30.
 */

public class MyHorizontalScrollView extends HorizontalScrollView {
    private static final int MAX_X_OVERSCROLL_DISTANCE = 200;

    private WeakReference<Context> wReference;

    private int mMaxXOverscrollDistance;

    private GestureDetector mGestureDetector;

    public MyHorizontalScrollView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        wReference = new WeakReference<Context>(context);
        initBounceDistance();
    }

    public MyHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        wReference = new WeakReference<Context>(context);
        initBounceDistance();
    }

    public MyHorizontalScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
        wReference = new WeakReference<Context>(context);
        initBounceDistance();
    }

    private void initBounceDistance() {
        try {
            final DisplayMetrics metrics = wReference.get().getResources().getDisplayMetrics();
            mMaxXOverscrollDistance = (int) (metrics.density * MAX_X_OVERSCROLL_DISTANCE);
            mGestureDetector = new GestureDetector(
                    (GestureDetector.OnGestureListener) new YScrollDetector());
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }

    @SuppressLint("NewApi")
    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY,
                                   int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY,
                                   boolean isTouchEvent) {
        // 这块是关键性代码
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY,
                mMaxXOverscrollDistance, maxOverScrollY, isTouchEvent);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // TODO Auto-generated method stub
        // return super.onInterceptTouchEvent(ev);
        return super.onInterceptTouchEvent(ev) && mGestureDetector.onTouchEvent(ev);
    }

    // Return false if we're scrolling in the x direction
    class YScrollDetector extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            // TODO Auto-generated method stub
            return super.onScroll(e1, e2, distanceX, distanceY);
        }

    }

}
