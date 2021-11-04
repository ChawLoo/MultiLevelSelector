package cn.chawloo.multilevelselector

import cn.chawloo.multilevelselector.widget.MultiLevelSelector

data class Area(
    var code: Long,
    var name: String,
    var parentId: Long = 0,
    var children: List<Area>? = null
) : MultiLevelSelector.IMultiLevelEntity<Area> {
    override var id: Long = code
    override var lastId: Long = parentId
    override var showTxt: String = name
    override var isNew: Boolean = false
    override val next: List<Area>? = children
}
