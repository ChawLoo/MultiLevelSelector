package cn.chawloo.multilevelselector

import cn.chawloo.multilevelselector.widget.MultiLevelSelector

data class TestEntity(
    var categoryId: Long = 0,
    var parentId: Long = 0,
    var categoryName: String = "",
) : MultiLevelSelector.IMultiLevelEntity {
    override var id: Long = categoryId
    override var showTxt: String = categoryName
    override var lastId: Long = parentId

    override var isNew: Boolean = true
}
