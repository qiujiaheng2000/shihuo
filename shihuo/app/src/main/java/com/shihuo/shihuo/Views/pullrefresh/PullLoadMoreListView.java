
package com.shihuo.shihuo.Views.pullrefresh;


import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Scroller;

import com.shihuo.shihuo.R;
import com.shihuo.shihuo.Views.EmptyView;

/**
 * 自定义ListView控件，实现下拉和更多处理
 * <HR>
 * 作者: 孙博
 * <p/>
 * 时间: 2013年11月7日 下午2:43:42
 */
public class PullLoadMoreListView extends ListView implements OnScrollListener {
    /**
     * FooterView可以显示
     */
    public static final int FOOTER_SHOW = 0x01;

    /**
     * FooterView可以隐藏
     */
    public static final int FOOTER_HIDE = 0x02;

    /**
     * FooterView保持显示，但不可点击
     */
    public static final int FOOTER_RETAIN = 0x03;

    /**
     * FooterView保持显示，可点击
     */
    public static final int FOOTER_WAIT = 0x04;

    /**
     * FooterView保持显示，可点击
     */
    public static final int FOOTER_REFRESH = 0x05;

    public final static int STATE_NORMAL = 0;

    public final static int STATE_READY = 1;

    public final static int STATE_LOADING = 2;

    public final static int STATE_NOTDATA = 3;

    public final static int STATE_REFRESH = 4;

    public final static int STATE_REFRESHING = 2;

    // private final static int PULL_LOAD_MORE_DELTA = 50; // when pull up >=
    // 50px
    // at bottom, trigger
    // load more.
    private final static float OFFSET_RADIO = 1.8f; // support iOS like pull

    private float mLastY = -1; // save event y

    private Scroller mScroller; // used for scroll back

    private OnScrollListener mScrollListener; // user's scroll listener

    // the interface to trigger refresh and load more.
    private OnXListViewListener mListViewListener;

    private boolean mEnablePullRefresh = true;
    private boolean mPullRefreshing = false; // is refreashing.
    // private final static int SCROLLBACK_FOOTER = 1;
    // -- footer view
    private XListViewFooter mFooterView;
    private RelativeLayout mFooterViewContent;
    // feature.
    private int mPullLoadState;
    private boolean mPullLoading;
    private boolean mIsFooterReady = false;
    // for mScroller, scroll back from header or footer.
    private int mScrollBack;

    /**
     * @param context
     */
    public PullLoadMoreListView(Context context) {
        super(context);
        this.initWithContext(context);
    }

    public PullLoadMoreListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.initWithContext(context);
    }

    public PullLoadMoreListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.initWithContext(context);
    }

    private void initWithContext(Context context) {
        mScroller = new Scroller(context, new DecelerateInterpolator());
        // XListView need the scroll event, and it will dispatch the event to
        // user's listener (as a proxy).
        super.setOnScrollListener(this);

        // init footer view
        mFooterView = new XListViewFooter(context);
        mFooterViewContent = (RelativeLayout)mFooterView
                .findViewById(R.id.xlistview_footer_content);
    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        if (adapter != null)
            // make sure XListViewFooter is the last footer view, and only add
            // once.
            if (mIsFooterReady) {
                mIsFooterReady = false;
                this.addFooterView(mFooterView);
            }
        super.setAdapter(adapter);
    }

    public void setFooterReady(boolean mIsFooterReady) {
        this.mIsFooterReady = mIsFooterReady;
    }

    /**
     * enable or disable pull up load more feature.
     */
    public void setPullLoadEnable(int enableState) {
        mPullLoadState = enableState;
        switch (mPullLoadState) {
            case FOOTER_SHOW:
                mFooterViewContent.setVisibility(View.VISIBLE);
                mPullLoading = false;
                mFooterView.setState(XListViewFooter.STATE_NORMAL);
                // both "pull up" and "click" will invoke load more.
                mFooterView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startLoadMore();
                    }
                });
                break;
            case FOOTER_HIDE:
                mFooterViewContent.setVisibility(View.GONE);
                mFooterView.setOnClickListener(null);
                break;
            case FOOTER_RETAIN:
                mFooterViewContent.setVisibility(View.VISIBLE);
                mFooterView.setState(XListViewFooter.STATE_NOTDATA);
                mFooterView.setOnClickListener(null);
                break;
            case FOOTER_WAIT:
                mFooterViewContent.setVisibility(View.VISIBLE);
                mPullLoading = false;
                mFooterView.setState(XListViewFooter.STATE_WAIT);
                // both "pull up" and "click" will invoke load more.
                mFooterView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startLoadMore();
                    }
                });
                break;
            case FOOTER_REFRESH:
                mFooterViewContent.setVisibility(View.VISIBLE);
                mPullLoading = false;
                mFooterView.setState(XListViewFooter.STATE_REFRESH);
                // both "pull up" and "click" will invoke load more.
                mFooterView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startLoadMore();
                    }
                });
                break;
        }
    }

    public int getPullLoadState() {
        return mPullLoadState;
    }


    public View getFooterView() {
        return mFooterView;
    }

    /**
     * stop refresh, reset header view.
     */
    public void stopRefresh() {
        mPullRefreshing = false;
        if ((getAdapter() == null || getAdapter().getCount() < 2) && getEmptyView() != null
                && getEmptyView() instanceof EmptyView) {
            ((EmptyView)getEmptyView()).onEmpty();
        }
    }

    /**
     * stop load more, reset footer view.
     */
    public void stopLoadMore() {
        if (mPullLoading) {
            mPullLoading = false;
            mFooterView.setState(PullLoadMoreListView.STATE_NORMAL);
        }
    }

    /**
     * set last refresh time
     *
     * @param time
     */
    public void setRefreshTime(String time) {
    }

    private void invokeOnScrolling() {
        if (mScrollListener instanceof OnXScrollListener) {
            OnXScrollListener l = (OnXScrollListener)mScrollListener;
            l.onXScrolling(this);
        }
    }

    private void startLoadMore() {
        if (mListViewListener != null && !mPullLoading) {
            mFooterView.setState(PullLoadMoreListView.STATE_LOADING);
            mListViewListener.onLoadMore();
            mPullLoading = true;
        }
    }

    public void setFooterLoading() {
        mPullLoading = true;
        mFooterViewContent.setVisibility(View.VISIBLE);
        mFooterView.setState(PullLoadMoreListView.STATE_LOADING);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mLastY == -1)
            mLastY = ev.getRawY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastY = ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                final float deltaY = ev.getRawY() - mLastY;
                mLastY = ev.getRawY();
                break;
            default:
                mLastY = -1; // reset
                break;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
    }

    @Override
    public void setOnScrollListener(OnScrollListener l) {
        mScrollListener = l;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        switch (scrollState) {
            case OnScrollListener.SCROLL_STATE_IDLE:
                if ((view.getLastVisiblePosition() == (view.getCount() - 1))
                        && (mPullLoadState == FOOTER_SHOW || mPullLoadState == FOOTER_WAIT || mPullLoadState == FOOTER_REFRESH))
                    this.startLoadMore();
                break;
        }
        if (mScrollListener != null)
            mScrollListener.onScrollStateChanged(view, scrollState);
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                         int totalItemCount) {
        // send to user's listener
        // mTotalItemCount = totalItemCount;
        if (mScrollListener != null)
            mScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
    }

    public void setXListViewListener(OnXListViewListener l) {
        mListViewListener = l;
    }

    /**
     * you can listen ListView.OnScrollListener or this one. it will invoke
     * onXScrolling when header/footer scroll back.
     */
    public interface OnXScrollListener extends OnScrollListener {
        void onXScrolling(View view);
    }

    /**
     * implements this interface to get refresh/load more event.
     */
    public interface OnXListViewListener {
        void onLoadMore();
    }

}
