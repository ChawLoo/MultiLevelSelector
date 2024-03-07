package cn.chawloo.multilevelselector.example

import cn.chawloo.multilevelselector.widget.IMultiLevelEntity
import kotlinx.serialization.Serializable

@Serializable
data class Area(
    var code: Long,
    var name: String,
    var parentId: Long = 0,
    var children: List<Area>? = null
) : IMultiLevelEntity {
    override var id: Long = code
    override var lastId: Long = parentId
    override var showTxt: String = name
    override var isNew: Boolean = false
    override val next: List<Area>? = children
}
