package com.ataulm.basic.recyclerview;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

public class FixedFocusGridLayoutManager extends GridLayoutManager {

    public FixedFocusGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    @Override
    public void addView(View child, int index) {
        super.addView(child, index);
        child.setOnKeyListener(new FindNextFocusableViewOnKeyListener(new FindNextFocusableViewOnKeyListener.RecyclerViewDataSet() {
            @Override
            public int getPosition(View view) {
                return FixedFocusGridLayoutManager.this.getPosition(view);
            }

            @Override
            public View getChildAt(int position) {
                return FixedFocusGridLayoutManager.this.getChildAt(position);
            }

            @Override
            public int getItemCount() {
                return FixedFocusGridLayoutManager.this.getItemCount();
            }
        }));
    }

    @Override
    public void removeView(View child) {
        super.removeView(child);
        removeOnKeyListenerFrom(child);
    }

    private void removeOnKeyListenerFrom(View child) {
        child.setOnKeyListener(null);
    }

    @Override
    public void removeViewAt(int index) {
        super.removeViewAt(index);
        View child = getChildAt(index);
        removeOnKeyListenerFrom(child);
    }

}
