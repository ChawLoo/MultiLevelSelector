package cn.chawloo.multilevelselector.example

import cn.chawloo.multilevelselector.widget.TYPE_ONLY_ONE_LIST_MODE
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object TempData {
    fun getData(mode: Int): List<Area> {
        if (mode == TYPE_ONLY_ONE_LIST_MODE) {
            val dataList = ArrayList<Area>()
            for (i in 1..3) {
                dataList.add(Area(code = i.toLong(), parentId = -1, name = "$i").apply {
                    isNew = i % 3 == 0
                })
            }
            for (i in 1..3) {
                for (p in 10..20) {
                    dataList.add(Area(code = p.toLong(), parentId = i.toLong(), name = "${i}-${p}").apply {
                        isNew = p % 3 == 0
                    })
                }
            }
            for (f in 1..3) {
                for (i in 10..20) {
                    for (p in 100..110) {
                        dataList.add(Area(code = p.toLong(), parentId = i.toLong(), name = "${f}-${i}-${p}").apply {
                            isNew = p % 3 == 0
                        })
                    }
                }
            }
            for (f in 1..3) {
                for (s in 10..20) {
                    for (i in 100..110) {
                        for (p in 1000..1010) {
                            dataList.add(Area(code = p.toLong(), parentId = i.toLong(), name = "${f}-${s}-${i}-${p}").apply {
                                isNew = p % 3 == 0
                            })
                        }
                    }
                }
            }
            return dataList
        } else {
            val data = area.fromJson<List<Area>>()
            setRandomItemIsNew(data)
            return data
        }
    }

    private fun setRandomItemIsNew(data: List<Area>) {
        data.forEachIndexed { index, item ->
            for (i in 0..3) {
                //循环三遍，为了多设置几个随机的isNew
                val random1 = (0..data.size).random()
                if (index == random1) {
                    item.isNew = true
                }
            }
            item.children?.takeIf { it.isNotEmpty() }?.run {
                //递归
                setRandomItemIsNew(this)
            }
        }
    }
    @OptIn(ExperimentalSerializationApi::class)
    val json = Json {
        encodeDefaults = true//编码默认值
        ignoreUnknownKeys = true//忽略未知键
        coerceInputValues = true//强制输入值，如果json属性与对象格式不符，则使用对象默认值
        explicitNulls = true //序列化时是否忽略null
        isLenient = true //宽松解析，json格式异常也可解析，如：{name:"小红",age:"18"} + Person(val name:String,val age:Int) ->Person("小红",18)
    }

    inline fun <reified T> T.toJson(): String = json.encodeToString(this)

    inline fun <reified T> String.fromJson(): T = json.decodeFromString(this)

    private val area: String =
}