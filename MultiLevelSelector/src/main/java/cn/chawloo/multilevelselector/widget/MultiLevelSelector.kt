package cn.chawloo.multilevelselector.widget

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import cn.chawloo.multilevelselector.R
import cn.chawloo.multilevelselector.databinding.ItemMultiLevelOptionBinding
import cn.chawloo.multilevelselector.databinding.ItemMultiLevelSelectorHeadBinding
import cn.chawloo.multilevelselector.databinding.MultiLevelSelectorBinding
import com.drake.brv.annotaion.AnimationType
import com.drake.brv.utils.divider
import com.drake.brv.utils.dividerSpace
import com.drake.brv.utils.linear
import com.drake.brv.utils.models
import com.drake.brv.utils.setup

const val TYPE_ONLY_ONE_LIST_MODE = 0//同一级列表
const val TYPE_CHILDREN_NEXT_MODE = 1//包含下一级列表模式

class MultiLevelSelector<T : MultiLevelSelector.IMultiLevelEntity> @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : LinearLayout(context, attrs, defStyle) {
    private var vb: MultiLevelSelectorBinding = MultiLevelSelectorBinding.bind(inflate(context, R.layout.multi_level_selector, this))
    var type: Int = TYPE_ONLY_ONE_LIST_MODE

    private var breadCrumbsColor: Int = Color.parseColor("#2c80c5")
    private var breadCrumbsTextSize: Int = 24
    private val footer = object : IMultiLevelEntity {
        override val id: Long
            get() = 0
        override val lastId: Long
            get() = -1
        override val showTxt: String
            get() = "请选择"
        override val isNew: Boolean
            get() = false
        override val next: List<IMultiLevelEntity>
            get() = emptyList()

    }
    private var selectCategoryArr = ArrayList<T>()
    private var onConfirm: List<T>.() -> Unit = {}

    private var sourceData: List<IMultiLevelEntity> = ArrayList()

    fun setData(data: List<T>, onConfirm: List<T>.() -> Unit) {
        vb.rvOptions.models = null
        selectCategoryArr.clear()
        sourceData = data
        val list = if (type == TYPE_ONLY_ONE_LIST_MODE) {
            getNext()
        } else {
            sourceData
        }
        list.takeIf { it.isNotEmpty() }?.run {
            vb.rvOptions.models = list
        }
        this.onConfirm = onConfirm
    }

    private fun getNext(item: IMultiLevelEntity? = null): List<IMultiLevelEntity> {
        return if (type == TYPE_CHILDREN_NEXT_MODE) {
            item?.next ?: sourceData
        } else {
            sourceData.filter { it.lastId == (item?.id ?: -1L) }
        }
    }

    var checkRepeat: (IMultiLevelEntity) -> Boolean = {
        false
    }

    init {
        setWillNotDraw(false)
        obtainAttributes(context, attrs)
        initRecyclerView()
    }

    private fun obtainAttributes(context: Context, attrs: AttributeSet?) {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.MultiLevelSelector)
        ta.run {
            breadCrumbsColor = ta.getColor(R.styleable.MultiLevelSelector_bread_crumbs_color, ContextCompat.getColor(context, R.color.theme_color))
            breadCrumbsTextSize = ta.getColor(R.styleable.MultiLevelSelector_bread_crumbs_color, 24)
        }
        ta.recycle()
    }

    private fun initRecyclerView() {
        vb.rvMenu.linear(orientation = RecyclerView.HORIZONTAL)
            .dividerSpace(20)
            .setup {
                addType<IMultiLevelEntity>(R.layout.item_multi_level_selector_head)
                setAnimation(AnimationType.ALPHA)
                onBind {
                    with(getBinding<ItemMultiLevelSelectorHeadBinding>()) {
                        tvTitle.text = getModel<IMultiLevelEntity>().showTxt
                        tvTitle.isEnabled = !isFooter(modelPosition)
                    }
                }
                onClick(R.id.tv_title) {
                    val next = if (modelPosition > 0) {
                        getNext(getModel<IMultiLevelEntity>(modelPosition - 1))
                    } else {
                        getNext()
                    }
                    next.takeIf { it.isNotEmpty() }?.run {
                        vb.rvOptions.models = this
                    }
                    selectCategoryArr = ArrayList(selectCategoryArr.subList(0, modelPosition))
                    vb.rvMenu.models = selectCategoryArr
                    clearFooter()
                    addFooter(footer)
                }
            }.addFooter(footer)
        vb.rvOptions.linear()
            .divider {
                setDivider(1)
                setColor(Color.parseColor("#EEEEEE"))
            }
            .setup {
                setAnimation(AnimationType.SLIDE_RIGHT)
                addType<IMultiLevelEntity>(R.layout.item_multi_level_option)
                onBind {
                    with(getBinding<ItemMultiLevelOptionBinding>()) {
                        with(getModel<IMultiLevelEntity>()) {
                            tvTitle.text = showTxt
                            ivNew.visibility = if (isNew) VISIBLE else GONE
                        }
                    }
                }
                onClick(R.id.item_view) {
                    val selectItem = getModel<T>()
                    val list = if (type == TYPE_ONLY_ONE_LIST_MODE) {
                        getNext(selectItem)
                    } else {
                        selectItem.next
                    }
                    val hasNext = !list.isNullOrEmpty()
                    if (!checkRepeat(selectItem)) {
                        selectCategoryArr.add(selectItem)
                        vb.rvMenu.models = selectCategoryArr
                        if (hasNext) {
                            vb.rvOptions.models = list
                            vb.rvMenu.smoothScrollToPosition(itemCount - 1)
                        } else {
                            vb.rvOptions.models = null
                            onConfirm(selectCategoryArr)
                        }
                    }
                }
            }
    }

    interface IMultiLevelEntity {
        val id: Long
        val lastId: Long
        val showTxt: String
        val isNew: Boolean
        val next: List<IMultiLevelEntity>?
    }
}