package cn.chawloo.multilevelselector.widget

import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.addDividerLine() {
    this.addItemDecoration(RecyclerDivider().apply {
        color = Color.parseColor("#f2f2f7")
        size = 1
    })
}