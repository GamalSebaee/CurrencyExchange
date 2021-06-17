package com.example.currencyexchange.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SpacesItemDecoration(private val spaceHorizontal: Int,
                           private val spaceVertical: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.left = spaceHorizontal
        outRect.right = spaceHorizontal
        outRect.bottom = spaceVertical
    }

}