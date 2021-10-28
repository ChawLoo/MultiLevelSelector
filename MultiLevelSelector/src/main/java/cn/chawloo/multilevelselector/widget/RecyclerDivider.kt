package cn.chawloo.multilevelselector.widget

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import androidx.core.view.children
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * RecyclerView 分割线
 * @author Create by 鲁超 on 2020/11/20 0020 18:24
 */
class RecyclerDivider : RecyclerView.ItemDecoration {
    private var paint: Paint
    private var orientation: Int = LinearLayoutManager.VERTICAL
    var color: Int = 0
        set(value) {
            paint.color = value
            field = value
        }
    var size: Int = 0

    constructor() : this(LinearLayoutManager.VERTICAL) {
        size = 1
        color = Color.parseColor("#cccccc")
    }

    constructor(orientation: Int) {
        this.orientation = orientation
        paint = Paint()
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        if (orientation == LinearLayoutManager.VERTICAL) {
            drawHorizontal(c, parent)
        } else {
            drawVertical(c, parent)
        }
    }

    private fun drawVertical(c: Canvas, parent: RecyclerView) {
        val top = parent.paddingTop.toFloat()
        val bottom = parent.height - parent.paddingBottom.toFloat()
        for (child in parent.children) {
            val params = child.layoutParams as RecyclerView.LayoutParams
            val left = child.right + params.rightMargin.toFloat()
            val right = left + size
            c.drawRect(left, top, right, bottom, paint)
        }
    }

    private fun drawHorizontal(c: Canvas, parent: RecyclerView) {
        val left = parent.paddingLeft.toFloat()
        val right = parent.width - parent.paddingRight.toFloat()
        for (child in parent.children) {
            val params = child.layoutParams as RecyclerView.LayoutParams
            val top = child.bottom + params.bottomMargin.toFloat()
            val bottom = top + size
            c.drawRect(left, top, right, bottom, paint)
        }
    }
}