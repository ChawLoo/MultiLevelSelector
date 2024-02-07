package cn.chawloo.multilevelselector

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import cn.chawloo.multilevelselector.widget.MultiLevelSelector
import cn.chawloo.multilevelselector.widget.TYPE_CHILDREN_NEXT_MODE
import cn.chawloo.multilevelselector.widget.TYPE_ONLY_ONE_LIST_MODE

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    lateinit var multiLevelSelector: MultiLevelSelector<Area>
    override fun onStart() {
        super.onStart()
        multiLevelSelector = findViewById(R.id.multiLevel)
        findViewById<Button>(R.id.btn_change).setOnClickListener {
            type = if (type == TYPE_ONLY_ONE_LIST_MODE) {
                TYPE_CHILDREN_NEXT_MODE
            } else {
                TYPE_ONLY_ONE_LIST_MODE
            }
            changeMode()
        }
        changeMode()
    }

    private var type = TYPE_ONLY_ONE_LIST_MODE

    private fun changeMode() {
        multiLevelSelector.type = type
        val data = TempData.getData(type)
        if (type == TYPE_ONLY_ONE_LIST_MODE) {
            multiLevelSelector.checkRepeat = {
                false
            }
            multiLevelSelector.setData(data) {
                findViewById<TextView>(R.id.tvResult).text = joinToString("->") { it.name }
            }
        } else {
            multiLevelSelector.setData(data) {
                findViewById<TextView>(R.id.tvResult).text = joinToString("->") { it.name }
            }
            multiLevelSelector.checkRepeat = {
                false
            }
        }
    }
}