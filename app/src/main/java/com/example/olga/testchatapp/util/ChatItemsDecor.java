package com.example.olga.testchatapp.util;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import static com.example.olga.testchatapp.util.MessageAdapter.TYPE_ME;
import static com.example.olga.testchatapp.util.MessageAdapter.TYPE_OTHERS;

/**
 * Created by olga on 29.12.16.
 */

public class ChatItemsDecor extends RecyclerView.ItemDecoration {

    private int spin;
    private int bottom;

    public ChatItemsDecor(int spin, int bottom) {
        this.spin = spin;
        this.bottom = bottom;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = parent.getChildAdapterPosition(view);
        int viewType = parent.getAdapter().getItemViewType(position);

        switch (viewType) {
            case TYPE_ME:
                outRect.left = spin;
                break;
            case TYPE_OTHERS:
                outRect.right = spin;
                break;
        }

        outRect.bottom = bottom;
    }


}
