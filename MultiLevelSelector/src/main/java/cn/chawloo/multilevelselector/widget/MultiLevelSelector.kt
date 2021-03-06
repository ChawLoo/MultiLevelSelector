package cn.chawloo.multilevelselector.widget

import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import cn.chawloo.multilevelselector.R
import cn.chawloo.multilevelselector.databinding.MultiLevelSelectorBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

const val TYPE_ONLY_ONE_LIST_MODE = 0//同一级列表
const val TYPE_CHILDREN_NEXT_MODE = 1//包含下一级列表模式

class MultiLevelSelector<T : MultiLevelSelector.IMultiLevelEntity<T>> @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyle: Int = 0
) : LinearLayout(context, attrs, defStyle) {
    private var vb: MultiLevelSelectorBinding = MultiLevelSelectorBinding.bind(inflate(context, R.layout.multi_level_selector, this))
    var type: Int = TYPE_ONLY_ONE_LIST_MODE

    private var breadCrumbsColor: Int = Color.parseColor("#2c80c5")
    private var breadCrumbsTextSize: Int = sp2px(12F)
    private var mMenuAdapter: BaseAdapter<T> = object : BaseAdapter<T>(R.layout.item_multi_level_selector_head) {
        override fun convert(holder: BaseViewHolder, item: T) {
            holder.setText(R.id.tv_title, item.showTxt + " >")
        }
    }.apply {
        setOnItemClickListener { _, _, position ->
            for (i in data.size downTo position + 1) {
                removeAt(i - 1)
                selectCategoryArr.removeAt(i - 1)
                val next = if (position > 0) {
                    val item = getItem(position - 1)
                    getNext(item)
                } else {
                    getNext()
                }
                next?.takeIf { it.isNotEmpty() }?.run {
                    changeOptions(this)
                }
            }
        }
        setAnimationWithDefault(BaseQuickAdapter.AnimationType.AlphaIn)
    }
    private var mOptionsAdapter: BaseAdapter<T> = object : BaseAdapter<T>(R.layout.item_multi_level_option) {
        override fun convert(holder: BaseViewHolder, item: T) {
            holder.setText(R.id.item_tv, item.showTxt)
                .setGone(R.id.iv_new, !item.isNew)
        }
    }.apply {
        setOnItemClickListener { _, _, position ->
            if (System.currentTimeMillis() - clickTime > 300) {
                val selectItem = getItem(position)
                val list = if (type == TYPE_ONLY_ONE_LIST_MODE) {
                    getNext(selectItem)
                } else {
                    selectItem.next
                }
                val hasNext = !list.isNullOrEmpty()
                if (!checkRepeat(selectItem)) {
                    selectCategoryArr.add(selectItem)
                    mMenuAdapter.addData(selectItem)
                    if (hasNext) {
                        changeOptions(list)
                        val footerViewPosition = mMenuAdapter.footerViewPosition
                        vb.rvMenu.smoothScrollToPosition(footerViewPosition)
                    } else {
                        this.setNewInstance(null)
                        onConfirm?.invoke(selectCategoryArr)
                    }
                }
                clickTime = System.currentTimeMillis()
            }
        }
        setAnimationWithDefault(BaseQuickAdapter.AnimationType.SlideInRight)
        setEmptyView(R.layout.custom_empty_view)
    }
    private var selectCategoryArr = ArrayList<T>()
    private var onConfirm: ((ArrayList<T>) -> Unit)? = null

    private var sourceData: List<T> = ArrayList()

    fun setData(data: List<T>, onConfirm: (ArrayList<T>) -> Unit) {
        mMenuAdapter.setList(null)
        selectCategoryArr.clear()
        sourceData = data
        val list = if (type == TYPE_ONLY_ONE_LIST_MODE) {
            getNext()
        } else {
            sourceData
        }
        list?.takeIf { it.isNotEmpty() }?.run {
            mOptionsAdapter.setList(list)
        }
        this.onConfirm = onConfirm
    }

    private fun getNext(item: T? = null): List<T>? {
        return if (item != null) {
            if (type == TYPE_CHILDREN_NEXT_MODE) {
                item.next
            } else {
                sourceData.filter { it.lastId == item.id }
            }
        } else {
            if (type == TYPE_CHILDREN_NEXT_MODE) {
                sourceData
            } else {
                sourceData.filter { it.lastId == -1L }
            }
        }

    }

    var checkRepeat: (T) -> Boolean = {
        false
    }

    init {
        setWillNotDraw(false)
        obtainAttributes(context, attrs)
    }

    private fun obtainAttributes(context: Context, attrs: AttributeSet?) {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.MultiLevelSelector)
        ta.run {
            breadCrumbsColor = ta.getColor(R.styleable.MultiLevelSelector_bread_crumbs_color, ContextCompat.getColor(context, R.color.theme_color))
            breadCrumbsTextSize = ta.getColor(R.styleable.MultiLevelSelector_bread_crumbs_color, sp2px(12F))
        }
        ta.recycle()
        initRecyclerView()
        addFooterView()
    }

    private fun addFooterView() {
        val footer = LayoutInflater.from(context).inflate(R.layout.item_multi_level_selector_head, vb.rvMenu, false)
        val titleTv = footer.findViewById<TextView>(R.id.tv_title)
        titleTv.text = "请选择 >"
        titleTv.setTextColor(Color.parseColor("#999999"))
        mMenuAdapter.addFooterView(footer, -1, HORIZONTAL)
    }

    private var clickTime: Long = 0

    private fun initRecyclerView() {
        vb.rvMenu.apply {
            addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                    super.getItemOffsets(outRect, view, parent, state)
                    outRect.left = dp2px(10F)
                }
            })
            adapter = mMenuAdapter
        }
        vb.rvOptions.addDividerLine()
        vb.rvOptions.adapter = mOptionsAdapter
    }

    private fun changeOptions(list: List<T>?) {
        mOptionsAdapter.setList(list)
    }

    private fun dp2px(dp: Float): Int {
        val scale: Float = context.resources.displayMetrics.density
        return (dp * scale + 0.5f).toInt()
    }

    private fun sp2px(sp: Float): Int {
        val scale: Float = context.resources.displayMetrics.scaledDensity
        return (sp * scale + 0.5f).toInt()
    }

    interface IMultiLevelEntity<T> {
        val id: Long
        val lastId: Long
        val showTxt: String
        val isNew: Boolean
        val next: List<T>?
    }
}