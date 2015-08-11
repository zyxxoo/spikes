package com.novoda.recyclerview;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class AppDrawerLikeFocusGridLayoutManager extends GridLayoutManager {

    // TODO: it's a bit weak passing this, perhaps
    private final RecyclerView recyclerView;

    public AppDrawerLikeFocusGridLayoutManager(Context context, int spanCount, RecyclerView recyclerView) {
        super(context, spanCount);
        this.recyclerView = recyclerView;
    }

    @Override
    public void addView(View child, int index) {
        super.addView(child, index);
        child.setOnKeyListener(new FindNextFocusableViewOnKeyListener(new FindNextFocusableViewOnKeyListener.Callbacks() {
            @Override
            public int getChildAdapterPosition(View view) {
                return recyclerView.getChildAdapterPosition(view);
            }

            @Override
            public RecyclerView.ViewHolder findViewHolderForAdapterPosition(int position) {
                return recyclerView.findViewHolderForAdapterPosition(position);
            }

            @Override
            public int getItemCount() {
                return AppDrawerLikeFocusGridLayoutManager.this.getItemCount();
            }

            @Override
            public void smoothScrollToPosition(int position) {
                recyclerView.smoothScrollToPosition(position);
            }
        }));
    }

    @Override
    public View onInterceptFocusSearch(View focused, int direction) {
        logOnInterceptFocusSearch((TextView) focused, direction);
        return super.onInterceptFocusSearch(focused, direction);
    }

    private void logOnInterceptFocusSearch(TextView focused, int direction) {
        Log.d("FOOO", "onInterceptFocusSearch(view ==) | position of focused view: " + focused.getText());
        switch (direction) {
            case View.FOCUS_LEFT:
                Log.d("FOOO", "INTERCEPT LEFT");
                break;
            case View.FOCUS_UP:
                Log.d("FOOO", "INTERCEPT UP");
                break;
            case View.FOCUS_RIGHT:
                Log.d("FOOO", "INTERCEPT RIGHT");
                break;
            case View.FOCUS_DOWN:
                Log.d("FOOO", "INTERCEPT DOWN");
                break;
            case View.FOCUS_FORWARD:
                Log.d("FOOO", "INTERCEPT FORWARd");
                break;
            case View.FOCUS_BACKWARD:
                Log.d("FOOO", "INTERCEPT BACKWARD");
                break;
            default:
                Log.d("FOOO", "INTERCEPT UNKNOWN: " + direction);
                break;
        }
    }

}
