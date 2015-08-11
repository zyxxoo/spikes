package com.novoda.recyclerview;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

class FindNextFocusableViewOnKeyListener implements View.OnKeyListener {

    private final Callbacks callbacks;

    FindNextFocusableViewOnKeyListener(Callbacks callbacks) {
        this.callbacks = callbacks;
    }

    @Override
    public boolean onKey(View view, int keyCode, KeyEvent event) {
        if (keyDownLeft(keyCode, event)) {
            Log.d("FOOO", "onKeyDown LEFT on view at position: " + ((TextView) view).getText());
            return handleNextFocus(view, View.FOCUS_LEFT);
        } else if (keyDownRight(keyCode, event)) {
            Log.d("FOOO", "onKeyDown RIGHT on view at position: " + ((TextView) view).getText());
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

    /**
     * @return whether the focus was handled or not
     */
    private boolean handleNextFocus(View view, int focusDirection) {
        View nextFocusableView = nextFocusableView(focusDirection, view);
        if (nextFocusableView == null) {
            Log.d("FOOO", "handleNextFocus() dunno what to focus on next, returning null");
            return false;
        }
        nextFocusableView.requestFocus();
        return true;
    }

    private View nextFocusableView(int direction, View currentFocusedView) {
        int positionCurrentFocusedView = callbacks.getChildAdapterPosition(currentFocusedView);

        switch (direction) {
            case View.FOCUS_LEFT:
                return nextFocusableView(positionCurrentFocusedView - 1);

            case View.FOCUS_RIGHT:
                return nextFocusableView(positionCurrentFocusedView + 1);

            default:
                return null;
        }
    }

    private View nextFocusableView(int targetPosition) {
        if (targetPosition < 0 || targetPosition > callbacks.getItemCount() - 1) {
            return null;
        }
        callbacks.smoothScrollToPosition(targetPosition);
        RecyclerView.ViewHolder viewHolder = callbacks.findViewHolderForAdapterPosition(targetPosition);

        if (viewHolder == null) {
            return null;
        } else {
            return viewHolder.itemView;
        }
    }

    interface Callbacks {

        /**
         * Get adapter position of item view, or {@link RecyclerView.NO_POSITION} if not found.
         */
        int getChildAdapterPosition(View view);

        /**
         * ViewHolder at position
         */
        @Nullable
        RecyclerView.ViewHolder findViewHolderForAdapterPosition(int position);

        /**
         * Number of items in bound adapter.
         *
         * @return
         */
        int getItemCount();

        void smoothScrollToPosition(int position);
    }

}
