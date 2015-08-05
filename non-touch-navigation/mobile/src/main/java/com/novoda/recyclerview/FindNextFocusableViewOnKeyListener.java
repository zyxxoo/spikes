package com.novoda.recyclerview;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;

class FindNextFocusableViewOnKeyListener implements View.OnKeyListener {

    private final RecyclerViewDataSet recyclerViewDataSet;

    FindNextFocusableViewOnKeyListener(RecyclerViewDataSet recyclerViewDataSet) {
        this.recyclerViewDataSet = recyclerViewDataSet;
    }

    @Override
    public boolean onKey(View view, int keyCode, KeyEvent event) {
        if (keyDownLeft(keyCode, event)) {
            return handleNextFocus(view, View.FOCUS_LEFT);
        } else if (keyDownRight(keyCode, event)) {
            return handleNextFocus(view, View.FOCUS_RIGHT);
        }

        return false;
    }

    private boolean keyDownLeft(int keyCode, KeyEvent event) {
        return event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DPAD_LEFT;
    }

    private boolean keyDownRight(int keyCode, KeyEvent event) {
        return event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DPAD_RIGHT;
    }

    private boolean handleNextFocus(View view, int focusRight) {
        View nextFocusableView = nextFocusableView(focusRight, view);
        if (nextFocusableView == null) {
            return false;
        }
        nextFocusableView.requestFocus();
        return true;
    }

    private View nextFocusableView(int direction, View currentFocusedView) {
        int positionCurrentFocusedView = recyclerViewDataSet.getPosition(currentFocusedView);

        switch (direction) {
            case View.FOCUS_LEFT:
                return nextFocusableViewOnLeft(positionCurrentFocusedView);

            case View.FOCUS_RIGHT:
                return nextFocusableViewOnRight(positionCurrentFocusedView);

            default:
                return null;
        }
    }

    private View nextFocusableViewOnLeft(int positionCurrentFocusedView) {
        return (positionCurrentFocusedView == 0) ? null : recyclerViewDataSet.getChildAt(positionCurrentFocusedView - 1);
    }

    private View nextFocusableViewOnRight(int positionCurrentFocusedView) {
        return (positionCurrentFocusedView == recyclerViewDataSet.getItemCount() - 1) ? null : recyclerViewDataSet.getChildAt(positionCurrentFocusedView + 1);
    }

    interface RecyclerViewDataSet {
        /**
         * Get adapter position of item view, or {@link RecyclerView.NO_POSITION} if not found.
         */
        int getPosition(View view);

        /**
         * View at position
         */
        @Nullable
        View getChildAt(int position);

        /**
         * Number of items in bound adapter.
         *
         * @return
         */
        int getItemCount();
    }

}
